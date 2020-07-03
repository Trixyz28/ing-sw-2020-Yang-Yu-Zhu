package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;
/**
 * This is the worker for the God Poseidon, decorated under WorkerDecorator abstract class.
 * <p></p>
 * If Poseidon's unmoved Worker is on the ground level, it may build up to three times.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Poseidon extends WorkerDecorator {

    private final List<UndecoratedWorker> totalWorkers;
    /**
     * Creates a worker with Poseidon's God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     * @param totalWorkerList Variable that presents all the workers in the game.
     */
    public Poseidon(UndecoratedWorker worker, List<UndecoratedWorker> totalWorkerList){
        super(worker);
        totalWorkers = totalWorkerList;
    }

    int buildCounter = 0;

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * At the end of the turn, the player can choose to build a block up to 3 times with the worker bestowed with Poseidon's God Powers,
     * if it is unmoved and on ground level.
     * In other cases it operates normally.
     */
    @Override
    public boolean canBuildBlock(Tile t) {
        if(poseidonCheck()) {
            return super.canBuildBlock(t);
        }else {
            return getUnmovedWorker().canBuildBlock(t);
        }
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Poseidon's God Powers can choose to build a dome 3 times if unmoved and on ground level
     * at the end of the turn.
     * If not it builds like a normal worker.
     */
    @Override
    public boolean canBuildDome(Tile t) {
        if (poseidonCheck()) {
            return super.canBuildDome(t);
        }else {
            return getUnmovedWorker().canBuildDome(t);
        }
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Poseidon's God Powers can build a block 3 times if unmoved and on ground floor
     * at the end of the turn.
     */
    @Override
    public void buildBlock(Tile t) {

        if(poseidonCheck()){
            super.buildBlock(t);
        }else{
            getUnmovedWorker().buildBlock(t);
              /* Check solo per il move worker (per God Power) */
        }

        buildCounter++;
        powerCheck();

    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Poseidon's God Powers can build a dome 3 times if unmoved and on ground floor
     * at the end of the turn.
     */
    @Override
    public void buildDome(Tile t) {

        if(poseidonCheck()){
            super.buildDome(t);
        }else{
            getUnmovedWorker().buildDome(t);
        }

        buildCounter++;
        powerCheck();


    }
    /**
     * {@inheritDoc}
     * <p></p>
     *Additionally:
     * <p></p>
     * If the player wants to build with the worker that didn't move and on ground floor he can only build on the adjacent tiles.
     */
    @Override
    public List<Tile> getAdjacentTiles() {
        if(getState() == 3){
            return getUnmovedWorker().getAdjacentTiles();
        }

        return super.getAdjacentTiles();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState() {
        super.nextState();
        buildCounter = 0;
    }

    /**
     * Checks which workers didn't move during the turn.
     * @return The worker who didn't move, otherwise <code>Null</code>.
     */
    private UndecoratedWorker getUnmovedWorker(){
        for (UndecoratedWorker w : totalWorkers){
            if(w.getBelongToPlayer() == getBelongToPlayer() && !w.equals(this)){
                return w;
            }
        }
        return null;
    }

    /**
     * Checks if the Poseidon's worker is using his God Power or not.
     * @return A boolean: <code>true</code> if the worker is the chosen one, otherwise <code>false</code>.
     */
    private boolean poseidonCheck(){

        return (getState() == 0 || buildCounter == 0);
    }

    /**
     * Checks if the worker can active Poseidon's power.
     */
    private void powerCheck(){  /* getState() == 0  -> unmoved worker */
        if(getState() != 0 && buildCounter < 4) {
            /* Worker moved -> check unmoved worker */
            UndecoratedWorker unmovedWorker = getUnmovedWorker();
            if (unmovedWorker != null && unmovedWorker.getPosition().getBlockLevel() == 0) {
                /* only if the unmoved worker is on level 0 */
                for (Tile t : unmovedWorker.getAdjacentTiles()) {
                    if (unmovedWorker.canBuildBlock(t) || unmovedWorker.canBuildDome(t)) {
                        setGodPower(true);
                        setState(3);
                        break;
                    }
                }
            }
        }
    }




}

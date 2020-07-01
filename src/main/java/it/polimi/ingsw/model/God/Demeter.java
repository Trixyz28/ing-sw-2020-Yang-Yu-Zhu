package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
/**
 * This is the worker for the God Demeter, decorated under WorkerDecorator abstract class.
 * <p></p>
 * Demeter's Worker may build one additional time, but not on the same space.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Demeter extends WorkerDecorator {

    private Tile originalBuild = new Tile();
    private int buildCounter = 0;

    /**
     * Creates a worker with Demeter's God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Demeter (UndecoratedWorker worker) {
        super(worker);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Demeter' God Powers can choose to build a block again after a "build" operation in the same turn but can't build
     * on the original <code>Tile</code>.
     */
    @Override
    public boolean canBuildBlock(Tile t) {
        if (buildCounter == 0) {
            return super.canBuildBlock(t);
        }
        else{
            return canBuildBlockDemeter(t);
        }
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Demeter' God Powers can build a block again after a "build" operation in the same turn but can't build
     * on the original <code>Tile</code>.
     */
    @Override
    public void buildBlock(Tile t) {
        super.buildBlock(t);
        if(buildCounter == 0) {
            buildCounter = 1;
            originalBuild = t;
            if(canBuild()) {
                setGodPower(true);
            }
        }
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Demeter' God Powers can also choose to build a dome after a "build" operation in the same turn but can't build
     * on the original <code>Tile</code>.
     */
    @Override
    public boolean canBuildDome(Tile t) {
        if( buildCounter == 0){
            return super.canBuildDome(t);
        }
        else{
            return canBuildDomeDemeter(t);
        }
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Demeter' God Powers can build a dome again after a "build" operation in the same turn but can't build
     * on the original <code>Tile</code>.
     */
    @Override
    public void buildDome(Tile t) {
        super.buildDome(t);
        if (buildCounter == 0) {
            buildCounter = 1;
            if(canBuild()) {
                setGodPower(true);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState() {
        super.nextState();
        buildCounter = 0;
        originalBuild = null;
    }

    /**
     * Special method to let the worker of Demeter to build a block again.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     * @return A boolean: <code>true</code> if the build action is available, otherwise <code>false</code>.
     */
    private boolean canBuildBlockDemeter(Tile t){
        if(t == originalBuild){
            return false;
        } else{
            return super.canBuildBlock(t);

        }
    }
    /**
     * Special method to let the worker of Demeter to build a dome again.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     * @return A boolean: <code>true</code> if the build action is available, otherwise <code>false</code>.
     */
    private boolean canBuildDomeDemeter(Tile t){
        if(t == originalBuild){
            return false;
        } else{
            return super.canBuildDome(t);

        }
    }

    /**
     * Checks if Demeter's worker can build a block or a dome again.
     * @return A boolean: <code>true</code> if the "build" operation is available, otherwise <code>false</code>.
     */
    private boolean canBuild(){
        for(Tile t : getAdjacentTiles()){
            if(canBuildBlock(t) || canBuildDome(t)){
                return true;
            }
        }
        return false;
    }



}

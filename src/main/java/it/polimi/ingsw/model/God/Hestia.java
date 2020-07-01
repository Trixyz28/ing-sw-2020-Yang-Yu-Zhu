package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;
/**
 * This is the worker for the God Hestia, decorated under WorkerDecorator abstract class.
 * <p></p>
 * Hestia's Worker may build one additional time, but this cannot be on a perimeter space.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Hestia extends WorkerDecorator {

    /**
     * Creates a worker with Hestia's God Power with the specified attributes.
     * @param worker Variable that implements the interface of the decorator pattern.
     */
    public Hestia (UndecoratedWorker worker){
        super(worker);
    }

    private int buildCounter = 0;

    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Hestia' God Powers can choose to build a block again after a "build" operation in the same turn but can't build
     * on a perimeter <code>Tile</code>.
     */
    @Override
    public boolean canBuildBlock(Tile t) {
        if (buildCounter == 0) {
            return super.canBuildBlock(t);
        }
        else{
            return canBuildBlockHestia(t);
        }
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Hestia' God Powers can build a block again after a "build" operation in the same turn but can't build
     * on a perimeter <code>Tile</code>.
     */
    @Override
    public void buildBlock(Tile t) {
        super.buildBlock(t);
        if(buildCounter == 0) {
            buildCounter = 1;
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
     * the worker bestowed with Hestia' God Powers can choose to build a dome again after a "build" operation in the same turn but can't build
     * on a perimeter <code>Tile</code>.
     */
    @Override
    public boolean canBuildDome(Tile t) {
        if( buildCounter == 0){
            return super.canBuildDome(t);
        }
        else{
            return canBuildDomeHestia(t);
        }
    }
    /**
     * {@inheritDoc}
     * <p></p>
     * Additionally:
     * <p>
     * the worker bestowed with Hestia's God Powers can build a dome again after a "build" operation in the same turn but can't build
     * on a perimeter <code>Tile</code>.
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
    }
    /**
     * Special method to let the worker of Hestia to build a block again.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     * @return A boolean: <code>true</code> if the "build" operation is available, otherwise <code>false</code>.
     */
    private boolean canBuildBlockHestia(Tile t){
        if(t.perimeterTile()){
            return false;
        }
        else{
            return super.canBuildBlock(t);

        }
    }
    /**
     * Special method to let the worker of Hestia to build a dome again.
     * @param t Variable that indicates the <code>Tile</code> at issue.
     * @return A boolean: <code>true</code> if the "build" operation is available, otherwise <code>false</code>.
     */
    private boolean canBuildDomeHestia(Tile t){
        if(t.perimeterTile()){
            return false;
        }
        else{
            return super.canBuildDome(t);

        }
    }
    /**
     * Check if Hestia's worker can build a block or a dome again.
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

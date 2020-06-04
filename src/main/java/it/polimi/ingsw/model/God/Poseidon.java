package it.polimi.ingsw.model.God;

import it.polimi.ingsw.model.Tile;

import java.util.List;

public class Poseidon extends WorkerDecorator{

    private List<UndecoratedWorker> totalWorkers;

    public Poseidon(UndecoratedWorker worker, List<UndecoratedWorker> totalWorkerList){
        super(worker);
        totalWorkers = totalWorkerList;
    }


    int buildCounter = 0;
    boolean isUnmoved = true;

    @Override
    public void move(Tile t) {
        buildCounter = 0;
        isUnmoved = false;
        super.move(t);
    }

    @Override
    public boolean canBuildBlock(Tile t) {
        if(poseidonCheck()) {
            return super.canBuildBlock(t);
        }else {
            return getUnmovedWorker().canBuildBlock(t);
        }
    }

    @Override
    public boolean canBuildDome(Tile t) {
        if (poseidonCheck()) {
            return super.canBuildDome(t);
        }else {
            return getUnmovedWorker().canBuildDome(t);
        }
    }

    @Override
    public void buildBlock(Tile t) {
        buildCounter++;
        if(poseidonCheck()){
            super.buildBlock(t);
        }else{
            getUnmovedWorker().buildBlock(t);
            powerCheck();  /* Check solo per il move worker (per God Power) */
        }

    }

    @Override
    public void buildDome(Tile t) {

        buildCounter++;
        if(poseidonCheck()){
            super.buildDome(t);
        }else{
            getUnmovedWorker().buildDome(t);
            powerCheck();
        }


    }

    @Override
    public int useGodPower(boolean use) {
        if(!use){
            setGodPower(false);
            isUnmoved = true;
        }
        return 2;  /* BUILD */
    }

    private UndecoratedWorker getUnmovedWorker(){
        for (UndecoratedWorker w : totalWorkers){
            if(w.getBelongToPlayer() == getBelongToPlayer() && w.getPosition()!=getPosition()){
                return w;
            }
        }
        return null;
    }

    private boolean poseidonCheck(){

        return (isUnmoved || buildCounter == 0);
    }

    private void powerCheck(){
        if(!isUnmoved && buildCounter <= 3) {  /* Worker mosso -> controllare unmoved worker */
            UndecoratedWorker unmovedWorker = getUnmovedWorker();
            if (unmovedWorker != null && unmovedWorker.getPosition().getBlockLevel() == 0) {
                for (Tile t : unmovedWorker.getPosition().getAdjacentTiles()) {
                    if (unmovedWorker.canBuildBlock(t) || unmovedWorker.canBuildDome(t)) {
                        setGodPower(true);  /* solo se il worker canBuild in level 0 */
                        break;
                    }
                }
            }
        }else if(!isUnmoved && buildCounter == 4){
            buildCounter = 0;
            isUnmoved = true;
        }
    }


}

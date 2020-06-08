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

        if(poseidonCheck()){
            super.buildBlock(t);
        }else{
            getUnmovedWorker().buildBlock(t);
              /* Check solo per il move worker (per God Power) */
        }
        powerCheck();
        buildCounter++;

    }

    @Override
    public void buildDome(Tile t) {

        if(poseidonCheck()){
            super.buildDome(t);
        }else{
            getUnmovedWorker().buildDome(t);
        }
        powerCheck();
        buildCounter++;


    }


    @Override
    public void nextState() {
        super.nextState();
        buildCounter = 0;
    }

    private UndecoratedWorker getUnmovedWorker(){
        for (UndecoratedWorker w : totalWorkers){
            if(w.getBelongToPlayer() == getBelongToPlayer() && !w.equals(this)){
                return w;
            }
        }
        return null;
    }

    private boolean poseidonCheck(){

        return (getState() == 0 || buildCounter == 0);
    }

    private void powerCheck(){  /* getState() == 0  -> unmoved worker */
        System.out.println("in check");
        if(getState() != 0 && buildCounter < 3) {  /* Worker mosso -> controllare unmoved worker */
            System.out.println("in if");
            UndecoratedWorker unmovedWorker = getUnmovedWorker();
            if (unmovedWorker != null && unmovedWorker.getPosition().getBlockLevel() == 0) {
                for (Tile t : unmovedWorker.getPosition().getAdjacentTiles()) {
                    if (unmovedWorker.canBuildBlock(t) || unmovedWorker.canBuildDome(t)) {
                        setGodPower(true);  /* solo se il worker canBuild in level 0 */
                        //System.out.println("on");
                        setState(3);
                        break;
                    }
                }
            }
        }
    }


}

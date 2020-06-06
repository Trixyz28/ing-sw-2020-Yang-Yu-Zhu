package it.polimi.ingsw.model.God;

//public class with conditions of worker like Athenas with attribute booleans/set/get to check
//ATTENZIONE: potrebbe essere messo in Match come regola generale, pensare alla scalabilità in caso di aggiunta di divinità.
// in tal caso aggiungere metodo per creazione oggetto di classe Conditions

import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class Conditions {

 //if true in currentTurn worker cant move up
    private boolean athenaRule = false;  /* true -> canMoveUp = false */

    private boolean limusRule = false;

    private boolean heraRule = false;

    private int heraPlayerID;

    private List<UndecoratedWorker> limusWorkers = new ArrayList<>();


    protected boolean canMoveUp() {
        return !athenaRule;
    }

    protected void setAthenaRule(boolean i) {
        this.athenaRule = i;
    }

    protected void addLimusWorker(UndecoratedWorker worker){  /* salvare i worker per controllo */
        limusWorkers.add(worker);
        limusRule = true;
    }

    protected boolean checkBuildCondition(Tile t){
        if(limusRule) {
            for (UndecoratedWorker limus : limusWorkers) {  /* non si può buildare su tile adiacenti a limus */
                if (limus.getPosition().isAdjacentTo(t)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void setHeraPlayerID(int playerID){
        heraPlayerID = playerID;
        heraRule = true;
    }

    protected boolean checkWinCondition(Tile t){
        if(heraRule && t.perimeterTile()){
            return false;
        }
        return true;
    }

    public void update(int losePlayerID){
        if(limusWorkers.get(0).getBelongToPlayer() == losePlayerID){
            limusRule = false;
            limusWorkers.clear();
        }else if(heraPlayerID == losePlayerID){
            heraRule = false;
        }
    }



}
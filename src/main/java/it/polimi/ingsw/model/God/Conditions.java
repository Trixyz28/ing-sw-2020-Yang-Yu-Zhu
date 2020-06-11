package it.polimi.ingsw.model.God;

//public class with conditions of worker like Athenas with attribute booleans/set/get to check
//ATTENZIONE: potrebbe essere messo in Match come regola generale, pensare alla scalabilità in caso di aggiunta di divinità.
// in tal caso aggiungere metodo per creazione oggetto di classe Conditions

import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class Conditions {

 //if true in currentTurn worker cant move up
    private boolean athenaRule = false;

    private boolean limusRule = false;

    private boolean heraRule = false;

    private int heraPlayerID;

    private List<UndecoratedWorker> limusWorkers = new ArrayList<>();



    protected boolean checkMoveCondition(Tile position, Tile dest){
        if(athenaRule){
            return position.getBlockLevel() >= dest.getBlockLevel();
        }
        return true;
    }

    protected void setAthenaRule(boolean i) {
        this.athenaRule = i;
    }

    /* salvare i worker per controllo Limus */
    protected void addLimusWorker(UndecoratedWorker worker){
        limusWorkers.add(worker);
        limusRule = true;
    }

    protected boolean checkBuildCondition(Tile t){
        if(limusRule) {
            /* non si può buildare su tile adiacenti a limus */
            for (UndecoratedWorker limus : limusWorkers) {
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
        if(limusRule && limusWorkers.get(0).getBelongToPlayer() == losePlayerID){
            limusRule = false;
            limusWorkers.clear();
        }else if(heraRule && heraPlayerID == losePlayerID){
            heraRule = false;
        }
    }



}
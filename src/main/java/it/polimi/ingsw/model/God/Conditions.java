package it.polimi.ingsw.model.God;

//public class with conditions of worker like Athenas with attribute booleans/set/get to check
//ATTENZIONE: potrebbe essere messo in Match come regola generale, pensare alla scalabilità in caso di aggiunta di divinità.
// in tal caso aggiungere metodo per creazione oggetto di classe Conditions

import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Conditions that serves the purpose of checking particulars conditions made by specific Gods
 * @author GC44
 * @version 1.0
 * @since 1.0
 */

public class Conditions {

    //if true in currentTurn worker cant move up
    private boolean athenaRule = false;

    private boolean limusRule = false;

    private boolean heraRule = false;

    private int heraPlayerID;

    private final List<UndecoratedWorker> limusWorkers = new ArrayList<>();


    /**
     * Checks if the moving condition of the Athena God Rue is satisfied or not.
     *
     * @param position Variable that indicates the initial <code>Tile</code>.
     * @param dest     Variable that indicates the destination <code>Tile</code>.
     * @return A boolean: <code>true</code> if the Athena God Rule is satisfied, otherwise <code>false</code>.
     */
    protected boolean checkMoveCondition(Tile position, Tile dest) {
        if (athenaRule) {
            return position.getBlockLevel() >= dest.getBlockLevel();
        }
        return true;
    }

    /**
     * Sets the Athena God Rule depending on game actions.
     *
     * @param i Variable that indicates if Athena God Rule should be activated or not.
     */
    protected void setAthenaRule(boolean i) {
        this.athenaRule = i;
    }

    /**
     * Saves the workers for the Limus God Rule control check.
     *
     * @param worker Variable which represents the workers whose player has Limus as God Power.
     */
    /* salvare i worker per controllo Limus */
    protected void addLimusWorker(UndecoratedWorker worker) {
        limusWorkers.add(worker);
        limusRule = true;
    }

    /**
     * Checks if the moving condition of the Limus God Rule is satisfied or not.
     *
     * @param t Variable that indicates the <code>Tile</code> at issue.
     * @return A boolean: <code>true</code> if the Limus God Rule is satisfied, otherwise <code>false</code>.
     */
    protected boolean checkBuildCondition(Tile t) {
        if (limusRule) {
            /* non si può buildare su tile adiacenti a limus */
            for (UndecoratedWorker limus : limusWorkers) {
                if (limus.getPosition().isAdjacentTo(t)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sets the ID of the player under Hera God Rule in the associated attribute.
     *
     * @param playerID Variable that indicates the player which possess Hera God workers
     */
    protected void setHeraPlayerID(int playerID) {
        heraPlayerID = playerID;
        heraRule = true;
    }

    /**
     * Checks if the winning condition of the Hera God Rule is satisfied or not.
     *
     * @param t Variable that indicates the <code>Tile</code> at issue.
     * @return A boolean: <code>true</code> if the Hera God Rule is satisfied,
     * otherwise <code>false</code>.
     */
    protected boolean checkWinCondition(Tile t) {
        if (heraRule && t.perimeterTile()) {
            return false;
        }
        return true;
    }

    /**
     * Check if the losing conditions of both Limus God Rule and Hera God Rule are satisfied or not.
     *
     * @param losePlayerID Variable that indicates the player at issue under the God's Rules.
     */
    public void update(int losePlayerID) {
        if (limusRule && limusWorkers.get(0).getBelongToPlayer() == losePlayerID) {
            limusRule = false;
            limusWorkers.clear();
        } else if (heraRule && heraPlayerID == losePlayerID) {
            heraRule = false;
        }
    }


}
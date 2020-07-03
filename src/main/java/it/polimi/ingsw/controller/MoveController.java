package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Tile;
/**
 * Controller that handles the <code>move</code> action of the worker.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */

public class MoveController {

    private final Model model;

    private Tile position;
    private UndecoratedWorker worker;

    /**
     *Creates a <code>MoveController</code> with the specified attributes.
     * @param model  Variable that represents the match. Also represents the Model in the MVC pattern.
     */
    public MoveController(Model model) {
        this.model = model;
    }

    /**
     *Moves the <code>worker</codeZ>in the <code>Tile</code> chosen by the player.
     * @param move Variable that could encapsulate a <code>move</code> action.
     * @return A boolean that indicates if the action was successful or not in the selected <code>Tile</code>.
     */
    public boolean moveWorker(Operation move){

        worker = model.getCurrentTurn().getChosenWorker();
        position = model.commandToTile(move.getRow(), move.getColumn());

        if(checkPosition()) {
            /* move andato a buon fine */
            worker.move(position);
            return true;
        }
        /* da ripetere move */
        return false;
    }

    /**
     *Checks the validity of the <code>move</code> action performed.
     * @return A boolean: <code>True</code> if the <code>worker</code> can move to the <code>Tile</code>
     *         otherwise <code>False</code>.
     */
    private boolean checkPosition(){  /* controllo posizione */
        return worker.canMove(position);
    }


}

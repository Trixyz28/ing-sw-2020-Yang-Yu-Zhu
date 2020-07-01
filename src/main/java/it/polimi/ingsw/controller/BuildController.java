package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.UndecoratedWorker;

/**
 * Controller that handles the <code>build</code> operation.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class BuildController {

    private final Model model;
    private UndecoratedWorker worker;

    /**
     * Creates a <code>BuildController</code> with the specified attributes.
     * @param model Variable that represents the match. Also represents the Model in the MVC pattern.
     */
    public BuildController(Model model) {
        this.model = model;
    }

    /**
     * Checks which <code>Tile</code> is the <code>build</code> action used upon and tries to proceeds with the action.
     * @param operation Variable that could encapsulate a <code>build</code> action.
     * @return A boolean that indicates if the action was successful or not in the selected <code>Tile</code>.
     */
    public boolean build(Operation operation){
        worker = model.getCurrentTurn().getChosenWorker();
        Tile position = model.commandToTile(operation.getRow(), operation.getColumn());

        return checkPosition(position);
    }

    /**
     *Checks if the worker can <code>build</code> and returns a boolean depending on the success or not of the action.
     * @param position Variable that indicates the position on the board of the selected <code>Tile</code>.
     * @return A boolean: <code>True</code> if the block or dome is built, otherwise <code>False</code>.
     */
    /* check buildBlock/Dome if true -> Build */
    private boolean checkPosition(Tile position){
        /* canBuild */
        if(worker.canBuildBlock(position)){
            worker.buildBlock(position);
            return true;
        }
        if(worker.canBuildDome(position)){
            worker.buildDome(position);
            return true;
        }
        return false;
    }

}

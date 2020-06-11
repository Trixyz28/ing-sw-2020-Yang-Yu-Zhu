package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Tile;

public class MoveController {

    private Model model;

    private Tile position;
    private UndecoratedWorker worker;


    public MoveController(Model model) {
        this.model = model;
    }

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

    private boolean checkPosition(){  /* controllo posizione */
        return worker.canMove(position);
    }


}

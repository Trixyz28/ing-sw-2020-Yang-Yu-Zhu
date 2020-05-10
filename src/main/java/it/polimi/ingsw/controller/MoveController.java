package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.view.View;

public class MoveController {

    private Model model;
    private View view;

    private Tile position;
    private Worker worker;


    public MoveController(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public boolean moveWorker(Operation move){

        worker = model.getCurrentTurn().getChosenWorker();
        position = model.commandToTile(move.getRow(), move.getColumn());
        if(checkPosition(position)) {
           worker.move(position);
            return true;  /* move andato a buon fine */
        }else{
            return false;  /* da ripetere move */
        }
    }

    private boolean checkPosition(Tile position){  /* controllo posizione */
        return worker.getCurrentPosition().availableToMove(position);  /* worker.canMove; da usare */
    }

}

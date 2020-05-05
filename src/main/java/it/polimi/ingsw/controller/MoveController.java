package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.view.View;

public class MoveController extends Controller{

    private Tile position;
    private Worker worker;


    public MoveController(Model model, View view) {
        super(model, view);
    }

    public void moveWorker(Operation move){

        worker = model.getCurrentTurn().getChosenWorker();
        position = model.commandToTile(move.getRow(), move.getColumn());
        if(checkPosition(position)) {
           worker.move(position);

        }else{
           //mostrare view messaggio di posizione errata e ripetere mossa

        }
    }

    private boolean checkPosition(Tile position){  /* controllo posizione */
        //worker.canMove; da usare
        return worker.getCurrentPosition().availableToMove(position);
    }

    @Override
    public void update(Object arg) {

    }
}

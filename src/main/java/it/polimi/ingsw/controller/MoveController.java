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

    public boolean moveWorker(Operation move, boolean canMoveUp){  /* canMoveUp : false non si può salire di livello */

        worker = model.getCurrentTurn().getChosenWorker();
        position = model.commandToTile(move.getRow(), move.getColumn());
        if(!canMoveUp){
            /*
            if(worker.getCurrentPosition().getBlockLevel() < position.getBlockLevel()){  /* il worker non può salire di livello!
                return false;
            }
            */
        }
        if(checkPosition(position)) {
            worker.move(position);
            model.showBoard(); /* mostrare mappa dopo move */
            return true;  /* move andato a buon fine */
        }else{
            return false;  /* da ripetere move */
        }
    }

    private boolean checkPosition(Tile position){  /* controllo posizione */
        //return worker.getCurrentPosition(worker).availableToMove(position);  /* worker.canMove; da usare */
        return worker.canMove(model.getCurrentTurn().getInitialTile()).contains(position);
    }

}

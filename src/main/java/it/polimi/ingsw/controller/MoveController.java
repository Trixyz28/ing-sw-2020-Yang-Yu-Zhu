package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.God.Apollo;
import it.polimi.ingsw.model.God.Minotaur;
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

    public boolean moveWorker(Operation move){  /* canMoveUp : false non si può salire di livello */

        worker = model.getCurrentTurn().getChosenWorker();
        position = model.commandToTile(move.getRow(), move.getColumn());

        //System.out.println("fine checkCanMoveUp");
        if(checkPosition(position)) {
            //System.out.println("check = true");
            worker.move(position);
            //model.showBoard(); /* mostrare mappa dopo move */
            return true;  /* move andato a buon fine */
        }else{
            //System.out.println("check = false");
            return false;  /* da ripetere move */
        }
    }

    private boolean checkPosition(Tile position){  /* controllo posizione */
        //return worker.getCurrentPosition(worker).availableToMove(position);  /* worker.canMove; da usare */
        //System.out.println("Worker tipo " + worker.toString());
        return worker.canMove().contains(position);
    }

    /*
    protected boolean moveApollo(Operation operation, boolean canMoveUp){  /* cambiare solo posizione della tile occupata e continuare move
        position = model.commandToTile(operation.getRow(), operation.getColumn());
        worker = model.getCurrentTurn().getChosenWorker();
        if(position.isOccupiedByWorker() && checkPosition(position,canMoveUp)){
            /* se occupato cambiare prima la posizione del worker
            for (UndecoratedWorker w : model.getTotalWorkers()){
                if(w.getPosition() == position){  /* trovare il worker nella tile destination
                    if(!(w instanceof Apollo)){  /* cambiare posizione se non è proprio worker
                        w.setPosition(worker.getPosition());
                        worker.move(position);  /* move normale
                        model.showBoard();
                        return true;
                    }
                    break;
                }
            }
            return false;  /* moveApollo fallita
        } else {  /* move normale
            System.out.println("Apollo move normale");
            return moveWorker(operation, canMoveUp);
        }
    }

    /*
    protected boolean moveMinotaur(Operation operation, boolean canMoveUp){
        position = model.commandToTile(operation.getRow(), operation.getColumn());
        worker = model.getCurrentTurn().getChosenWorker();

        if(position.isOccupiedByWorker() && checkPosition(position,canMoveUp)){ // fare spinta solo se possibile
            /* se occupato cambiare prima la posizione del worker */
    /*
            for (UndecoratedWorker w : model.getTotalWorkers()){
                if(w.getPosition() == position){  // trovare il worker nella tile destination
                    if(!(w instanceof Minotaur)){  // cambiare posizione se non è proprio worker
                        int forcedRow = position.getRow()+(position.getRow()-worker.getPosition().getRow());
                        int forcedColumn = position.getColumn()+(position.getColumn()-worker.getPosition().getColumn());
                        w.setPosition(model.commandToTile(forcedRow, forcedColumn));
                        worker.move(position);  // move normale
                        model.showBoard();
                        return true;
                    }
                    break;
                }
            }
            return false;  /* moveMinotuaur fallita */
    /*
        } else {
            return moveWorker(operation, canMoveUp);
        }
    }
    */
}

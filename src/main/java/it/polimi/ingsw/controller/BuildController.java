package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.view.View;

public class BuildController extends Controller {

    private Tile position;
    private Worker worker;

    public BuildController(Model model, View view) {
        super(model, view);
    }

    public void build(Operation operation){
        worker = model.getCurrentTurn().getChosenWorker();
        position = model.commandToTile(operation.getRow(), operation.getColumn());
        if(checkPosition(position)){
            worker.build(position);
        }else{
            //messaggio view errato comando
        }
    }

    private boolean checkPosition(Tile position){
        //worker.canBuild;  da usare
        return worker.getCurrentPosition().availableToBuild(position);
    }
}

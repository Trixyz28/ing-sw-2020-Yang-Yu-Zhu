package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.view.View;

public class BuildController {

    private Model model;
    private View view;

    private Tile position;
    private Worker worker;

    public BuildController(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public boolean build(Operation operation){
        worker = model.getCurrentTurn().getChosenWorker();
        position = model.commandToTile(operation.getRow(), operation.getColumn());
        if(checkPosition(position)){
            worker.build(position);
            return true;  /* build andato a buon fine */
        }else{
            return false;  /* da ripetere build */
        }
    }

    private boolean checkPosition(Tile position){
        return worker.getCurrentPosition().availableToBuild(position);  /* worker.canBuild;  da usare */
    }
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.UndecoratedWorker;

public class BuildController {

    private Model model;
    private Tile position;
    private UndecoratedWorker worker;

    public BuildController(Model model) {
        this.model = model;
    }


    public boolean build(Operation operation){
        worker = model.getCurrentTurn().getChosenWorker();
        position = model.commandToTile(operation.getRow(), operation.getColumn());

        return checkPosition(position);
    }

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

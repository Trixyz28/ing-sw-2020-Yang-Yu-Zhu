package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.Hephaestus;
import it.polimi.ingsw.model.God.UndecoratedWorker;

public class BuildController {

    private Model model;
    private Tile position;
    private UndecoratedWorker worker;
    private boolean isAtlas;
    private Operation operation;

    protected boolean isAtlas(){
        return isAtlas;
    }

    protected Operation getOperation(){
        return operation;
    }

    public BuildController(Model model) {
        this.model = model;
    }


    public boolean checkBlockDome(String command){  /* Atlas build */
        if(command.equals("DOME")){
            worker.buildDome(position);
            return true;
        }else if(command.equals("BLOCK")){
            worker.buildBlock(position);
            return true;
        }
        return false;
    }


    public boolean build(Operation operation){
        isAtlas = false;
        this.operation = operation;
        worker = model.getCurrentTurn().getChosenWorker();
        position = model.commandToTile(operation.getRow(), operation.getColumn());
        if(checkPosition(position)){  /* build nella check */
            model.showBoard();  /* mostrare mappa dopo build */
            return true;  /* build andato a buon fine */
        }else{
            return false;  /* da ripetere build */
        }
    }

    private boolean checkPosition(Tile position){  /* check buildBlock/Dome if true -> Build */
        //return worker.getCurrentPosition(worker).availableToBuild(position);  /* worker.canBuild;  da usare */
        if(worker.canBuildDome(position) && worker.canBuildBlock(position)){  /* Atlas */
            isAtlas = true;
            return false;
        }
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


    protected boolean buildHephaestus(){  /* Hephaestus build un blocco in più */
        if (worker.canBuildBlock(position)){
            model.sendMessage(Messages.Hephaestus);
            return true;
        } else {
            return false;
        }
    }
}

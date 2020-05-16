package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.Hephaestus;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.view.View;

public class BuildController {

    private Model model;
    private Tile position;
    private UndecoratedWorker worker;
    private boolean isAtlas;
    private boolean isHephaestus;
    private boolean changed = false;
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

    public boolean checkBlockDome(String command){
        if(command.equals("DOME")){
            worker.buildDome(position);
            return true;
        }else if(command.equals("BLOCK")){
            worker.buildBlock(position);
            return true;
        }
        return false;
    }

    protected void setChanged(){
        changed = true;
    }

    public boolean build(Operation operation){
        isAtlas = false;
        isHephaestus = false;
        changed = false;
        this.operation = operation;
        worker = model.getCurrentTurn().getChosenWorker();
        if(worker instanceof Hephaestus){
            isHephaestus = true;
        }
        position = model.commandToTile(operation.getRow(), operation.getColumn());
        if(checkPosition(position)){
            //worker.build(position);
            return true;  /* build andato a buon fine */
        }else{
            return false;  /* da ripetere build */
        }
    }

    private boolean checkPosition(Tile position){
        //return worker.getCurrentPosition(worker).availableToBuild(position);  /* worker.canBuild;  da usare */
        if(worker.canBuildDome(position) && worker.canBuildBlock(position)){  /* Atlas */
            isAtlas = true;
            return false;
        }
        if(worker.canBuildBlock(position)){
            worker.buildBlock(position);
            if(isHephaestus){  /* Hephaestus build un blocco in più */
                if (worker.canBuildBlock(position)){
                    model.sendMessage(Messages.Hephaestus);
                    waitChange();

                }
            }
            return true;
        }
        if(worker.canBuildDome(position)){
            worker.buildDome(position);
            return true;
        }
        return false;
    }

    private void waitChange(){
        while(true){
            if(changed){
                break;
            }
        }
        changed = false;
    }
}

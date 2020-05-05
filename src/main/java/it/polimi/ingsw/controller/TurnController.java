package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class TurnController extends Controller {

    private View view;

    public TurnController(Model model, View view) {
        super(model, view);
    }

    private Turn currentTurn;
    private ArrayList<Player> playerList;
    private Worker chosenWorker;
    private Tile builtTile;

    @Override
    public void update(Object arg) {
        if(arg instanceof Worker){

            if(((Worker)arg).canMove()){  //controllare se il Worker scelta possa fare la mossa o no
                chosenWorker = (Worker)arg;
            }else{
                //view.chooseWorker;  -> richiedere la scelta
            }
        }
        if(arg instanceof Tile){
            builtTile = (Tile)arg;
        }

    }

    public void endMove() {
        currentTurn.setFinalTile(chosenWorker.getCurrentPosition());
        //view.build();  ->  chiedere al player di Buildare
    }

    public void endTurn() {
        currentTurn.setBuiltTile(builtTile);
        //illustrare qualche messaggio sulla view -> attesa end
        nextTurn();
    }

    public void nextTurn() {
        currentTurn = model.getCurrentTurn();
        playerList = model.getMatchPlayersList();
        int turnNumber = currentTurn.getTurnNumber() + 1;
        currentTurn.setTurnNumber(turnNumber);
        int index = model.getNextPlayerIndex();  //trovare indice del player successivo
        currentTurn.setCurrentPlayer(playerList.get(index));
        //view.chooseWorker;  -> far scegliere al player il worker dalla view
        currentTurn.setChosenWorker(chosenWorker);
        currentTurn.setInitialTile(chosenWorker.getCurrentPosition());
        currentTurn.setFinalTile(null);  //inizializzare Final e Buit
        currentTurn.setBuiltTile(null);
        //view.moveWorker();  ->  passare alla scelta della mossa
    }

}
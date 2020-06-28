package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.Tags;
import it.polimi.ingsw.model.*;

public class InitController {

    private Model model;

    private int indexWorker;
    private boolean endInitialize;


    public InitController(Model model) {
        this.model = model;
        endInitialize = false;
    }

    protected boolean isEndInitialize(){
        return endInitialize;
    }

    /* subito dopo la notifica setup */
    public void initializeMatch(){
        /* inizia il Challenger */
        model.challengerStart();
    }

    /* scelta god per definire la GodList -> challenger */
    protected void defineGodList(String god, GodList godList){
        //eseguire solo al challenger
        if(!godList.checkLength()) {
            if(model.defineGodList(god)){
                /* true if end define -> start to choose god by other Players */
                model.nextChoiceGod();
            }
        }
    }

    /* scelta God dal Player */
    protected void chooseGod(String god){
        god = god.toUpperCase();
        if(model.chooseGod(god)){
            /* end chose God -> next player have to choose god */
            model.nextChoiceGod();
        }
    }

    /* selezione StartingPlayer -> Inizializzazione Turno 0 */
    protected void setStartingPlayer(String startingPlayerNickname){
        /* sceglire il startingPlayer attraverso Nickname */
        if(model.setStartingPlayer(startingPlayerNickname)) {
            /* inizializzazione turno dopo scelta God e StartingPlayer */
            model.startTurn();
        }

    }

    /* posizionare Worker -> fine inizializzazione */
    protected void placeWorker(Operation position){
        Tile currentPosition = model.commandToTile(position.getRow(), position.getColumn());
        if(currentPosition.isOccupiedByWorker()){
            model.sendMessage(Tags.boardMsg,Messages.occupiedTile);
        }else {
            Player currentPlayer = model.getCurrentTurn().getCurrentPlayer();
            /* posizionare il worker */
            currentPlayer.chooseWorker(indexWorker).setPosition(currentPosition);
            indexWorker++;

            if(indexWorker > 1){
                model.sendMessage(Tags.boardMsg,Messages.endTurn);
                /* passare al nextPlayer */
                indexWorker = 0;
                Player nextPlayer = model.getMatchPlayersList().get(model.getNextPlayerIndex());
                if(nextPlayer.getPlayerID() == model.getStartingPlayerID()){
                    /* all players placed their workers */
                    endInitialize = true;
                }else {
                    /* next player has to place workers */
                    model.getCurrentTurn().setCurrentPlayer(nextPlayer);
                }
            }
        }
        if(!endInitialize) {
            /* continuare con posizionare i worker */
            model.placeWorker(indexWorker);
        }
    }


}

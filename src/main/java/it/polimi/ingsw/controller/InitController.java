package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.Tags;
import it.polimi.ingsw.model.*;

/**
 *Controller that handles the initialization of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class InitController {

    private Model model;

    private int indexWorker;
    private boolean endInitialize;

    /**
     *Creates an <code>InitController</code> with the specified attributes.
     * @param model Variable that represents the match. Also represents the Model in the MVC pattern.
     */
    public InitController(Model model) {
        this.model = model;
        endInitialize = false;
    }

    /**
     * Gets the <code>endInitialize</code> variable.
     * @return A boolean that is used to end the initializing sequence of the game.
     */
    protected boolean isEndInitialize(){
        return endInitialize;
    }

    /**
     * Starts after setup notification to set up the challenger players.
     */
    /* subito dopo la notifica setup */
    public void initializeMatch(){
        /* inizia il Challenger */
        model.challengerStart();
    }

    /**
     * Handles the choices of Gods from the challenger and the players.
     * @param god Variable that indicates the God chosen by the player at issue.
     * @param godList Variable that is the list of all the <code>Gods</code>
     */
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

    /**
     *Checks if the player's chosen God is correct and assigns it to the player at issue.
     * @param god Variable that indicates the God chosen by the player at issue.
     */
    /* scelta God dal Player */
    protected void chooseGod(String god){
        god = god.toUpperCase();
        if(model.chooseGod(god)){
            /* end chose God -> next player have to choose god */
            model.nextChoiceGod();
        }
    }

    /**
     * Handles the selection of the starting player.
     * @param startingPlayerNickname Variable which represents the players who is the first to start.
     */
    /* selezione StartingPlayer -> Inizializzazione Turno 0 */
    protected void setStartingPlayer(String startingPlayerNickname){
        /* sceglire il startingPlayer attraverso Nickname */
        if(model.setStartingPlayer(startingPlayerNickname)) {
            /* inizializzazione turno dopo scelta God e StartingPlayer */
            model.startTurn();
        }

    }

    /**
     * Handles the positioning of the first 2 workers at the start of the game.
     * @param position Variable that indicates the position of the <code>Tile</code> where the <code>worker</code>
     *                 is put.
     */
    /* posizionare Worker -> fine inizializzazione */
    protected void placeWorker(Operation position){
        Tile currentPosition = model.commandToTile(position.getRow(), position.getColumn());
        if(currentPosition.isOccupiedByWorker()){
            model.sendMessage(Tags.BOARD_MSG,Messages.occupiedTile);
        }else {
            Player currentPlayer = model.getCurrentTurn().getCurrentPlayer();
            /* posizionare il worker */
            currentPlayer.chooseWorker(indexWorker).setPosition(currentPosition);
            indexWorker++;

            if(indexWorker > 1){
                model.sendMessage(Tags.BOARD_MSG,Messages.endTurn);
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

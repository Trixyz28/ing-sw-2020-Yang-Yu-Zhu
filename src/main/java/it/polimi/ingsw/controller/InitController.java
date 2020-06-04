package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

public class InitController {

    private Model model;

    private boolean nameChanged;
    private int indexWorker;
    private boolean endInitialize;


    public InitController(Model model) {
        this.model = model;
        nameChanged = false;
        endInitialize = false;
    }

    protected boolean isNameChanged(){
        return nameChanged;
    }

    protected boolean isEndInitialize(){
        return endInitialize;
    }

    /* subito dopo la notifica setup */
    public void initializeMatch(){
        model.randomChooseChallenger(); /* scegliere challenger per Random  */
        model.challengerStart();  /* inizia il Challenger */
    }

    /* scelta God dal Player */
    protected void chooseGod(String god){

        god = god.toUpperCase();
        model.chooseGod(god);
    }


    /* scelta god per definire la GodList */
    protected void defineGodList(String god, GodList godList){  //eseguire solo al challenger
        if(!godList.checkLength()) {
            if(model.defineGodList(god, godList)){  /* true if end define */
                model.nextChoiceGod();
            }
        }
    }

    /* inizializzazione turno dopo scelta God e StartingPlayer */
    private void initializeTurn(){   /* fase di preparazione alla partita in Turno 0 per posizionare i workers */
        model.startTurn();
          /* dalla view passa al Controller notificando la posizione (Operation) */
    }

    /* posizionare Worker -> fine inizializzazione */
    protected void placeWorker(Operation position){  //currentPosition = Tile dove posizionare il worker
        Tile currentPosition = model.commandToTile(position.getRow(), position.getColumn());
        if(currentPosition.isOccupiedByWorker()){
            model.sendMessage("Tile Occupied!");
        }else {
            /* mostrare mappa aggiornata */
            Player currentPlayer = model.getCurrentTurn().getCurrentPlayer();
            currentPlayer.chooseWorker(indexWorker).setPosition(currentPosition);  /* posizionare il worker */
            indexWorker++;
            model.showBoard();
            if(indexWorker > 1){  /* passare al nextPlayer */
                indexWorker = 0;
                Turn currentTurn = model.getCurrentTurn();
                Player nextPlayer = model.getMatchPlayersList().get(model.getNextPlayerIndex());
                if(nextPlayer.getPlayerID() == model.getStartingPlayerID()){  /* fine giro : inizio partita */
                    endInitialize = true;  //fine inizializzazione Turno
                }else {
                    currentTurn.setCurrentPlayer(nextPlayer);
                }

            }
        }
        if(!endInitialize) {  /* continuare con posizionare i worker */
            model.placeWoker(indexWorker);  /* dalla view passa al Controller notificando la posizione (Operation) */
        }
    }

    /* selezione StartingPlayer -> Inizializzazione Turno 0 */
    protected void setStartingPlayer(String startingPlayerNickname){  /* per sceglire il startingPlayer attraverso Nickname */

        if(model.setStartingPlayer(startingPlayerNickname)) {
            nameChanged = true;
            /* fine parte scelta StartingPlayer inizio iniazializzazione turno : nameChanged = true */
            initializeTurn();
        }

    }



}

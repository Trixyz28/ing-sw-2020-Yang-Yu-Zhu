package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.util.Map;

public class InitController {

    private Model model;
    private Map<Player, View> views;

    private Player startingPlayer;
    private Player challenger;
    private Player currentPlayer;
    private boolean godChanged;
    private boolean nameChanged;
    private int indexWorker;
    private boolean endInitialize;


    public InitController(Model model, Map<Player, View> views) {
        this.model = model;
        this.views = views;
        nameChanged = false;
        godChanged = false;
        endInitialize = false;
    }

    protected boolean isGodChanged(){
        return godChanged;
    }

    protected boolean isNameChanged(){
        return nameChanged;
    }

    protected boolean isEndInitialize(){
        return endInitialize;
    }

    protected String getCurrentPlayer() {
        return currentPlayer.getPlayerNickname();
    }

    /* subito dopo la notifica setup */
    public void initializeMatch(){
        model.randomChooseChallenger(); /* scegliere challenger per Random  */
        challengerStart();  /* inizia il Challenger */

    }

    /* preparazione scelta godList dal challenger */
    private void challengerStart(){
        challenger = model.getMatchPlayersList().get(model.getChallengerID()); /* ID = indice iniziale */
        currentPlayer = challenger;
        views.get(challenger).showMessage("Sei il Challenger!");
        model.showCompleteGodList();  /* mandare al Challenger la lista completa dei God */
    }

    /* dopo fine defineGodList -> inizio scelta God dal Player next al Challenger */
    private void startChoosingGod(){
        int index = challenger.getPlayerID()+1;  /* Player next al Challenger */
        if(index == model.getMatchPlayersList().size()){
            index = 0;
        }
        currentPlayer = model.getMatchPlayersList().get(index);
        View view = views.get(currentPlayer);
        view.showMessage("Scegli la tua divinità");



    }

    /* scelta God dal Player */
    protected void chooseGod(String player, String god){
        if (player.equals(currentPlayer.getPlayerNickname())){
            GodList godList = model.getGodsList();
            god = god.toUpperCase();
            godList.selectGod(god);
            if(godList.checkGod()){
                //far printare alla view la conferma della scelta
                views.get(currentPlayer).showMessage("Hai scelto " + god + "!");

                for(Player p : model.getMatchPlayersList()){  /* notificare la scelta agli altri giocatori */
                    if(p != currentPlayer){
                        String name = currentPlayer.getPlayerNickname();
                        views.get(p).showMessage("Il player " + name + " ha scelto " +
                                god + ".");
                    }
                }

                currentPlayer.godChoice(god);  /* assegnare al Player il God scelto */
                currentPlayer.createWorker(god);  /* creare worker determinato God */
                godList.removeFromGodList(god);  /* eliminare dalla currentGodList il god scelto */
                int index = currentPlayer.getPlayerID()+1;  /* next player*/
                if(index == model.getMatchPlayersList().size()){
                    index = 0;  /* quando l'indice arriva alla fine riiniziare*/
                }
                currentPlayer = model.getMatchPlayersList().get(index);
                if(index == challenger.getPlayerID()){  /* fine giro scelta God */
                    endGod();  /* dare direttamente la god rimanente al Challenger */
                }else {
                    View view = views.get(currentPlayer);
                    view.showMessage("Scegli la tua divinità");
                    model.showGodList();
                }
            }else{
                //far printare alla view la richiesta di ripetere la scelta
                View view = views.get(currentPlayer);
                view.showMessage("Riprova!\nScegli la tua divinità");
            }
        }else {
            for(Player p : model.getMatchPlayersList()){
                if(p.getPlayerNickname().equals(player)) {
                    views.get(p).showMessage(Messages.wrongTurn);
                    break;
                }
            }

        }
    }

    /* fine scelte God -> inizio scelta StartingPlayer */
    protected void endGod(){
        /* dare direttamente la god rimanente al Challenger */
        View view = views.get(challenger);

        GodList godList = model.getGodsList();
        String god = godList.getCurrentGodList().get(0);  /* il god rimanente nella currentGodList viene assegnata al challenger */
        view.showMessage("Il god rimasto è " + god + "!");  //far printare alla view il messaggio del god assegnato

        challenger.godChoice(god);
        challenger.createWorker(god);
        godChanged = true;
        /* fine parte scelta God */

        view.showMessage("Scegli il Player che inizia la partita!");
        /* necessario aspettare risposta dal client */

    }

    /* scelta god per definire la GodList */
    protected void defineGodList(String god, GodList godList){  //eseguire solo al challenger
        if(!godList.checkLength()) {
            //far scegliere Gods attraverso la view dal challenger )
            godList.selectGod(god);
            if(!godList.addInGodList()){  /* se il God scelto non viene aggiunto nella currentList */
                views.get(challenger).showMessage("Scelta invalida");
            }
            if(godList.checkLength()){
                for(Player p : model.getMatchPlayersList()){
                    views.get(p).showMessage("Il Challenger ha finito di scegliere i God! La Lista dei God scelti è :");
                }
                model.showGodList();

                startChoosingGod();
            }
        } else {
            views.get(challenger).showMessage(Messages.wrongTurn);  /* accesso invalido challenger */
        }
    }

    /* inizializzazione turno dopo scelta God e StartingPlayer */
    private void initializeTurn(){   /* fase di preparazione alla partita in Turno 0 per posizionare i workers */
        model.startCurrentTurn(); /* creare un nuovo Turn */
        Turn currentTurn = model.getCurrentTurn();
        currentPlayer = currentTurn.getCurrentPlayer();  /* primo currentPlayer == StartingPlayer */
        indexWorker = 0;
        model.showBoard();
        views.get(currentPlayer).showMessage("Posiziona il worker" + indexWorker);
        model.place();  /* dalla view passa al Controller notificando la posizione (Operation) */
    }

    /* posizionare Worker -> fine inizializzazione */
    protected void placeWorker(Operation position){  //currentPosition = Tile dove posizionare il worker
        Tile currentPosition = model.commandToTile(position.getRow(), position.getColumn());
        if(currentPosition.isOccupiedByWorker()){
            views.get(currentPlayer).showMessage("Tile Occupied!");
        }else {
            /* mostrare mappa aggiornata */
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
                    currentTurn.setCurrentPlayer(model.getMatchPlayersList().get(model.getNextPlayerIndex()));
                    currentPlayer = nextPlayer;
                }

            }
        }
        if(!endInitialize) {  /* continuare con posizionare i worker */
            views.get(currentPlayer).showMessage("Posiziona il worker" + indexWorker);
            model.place();  /* dalla view passa al Controller notificando la posizione (Operation) */
        }
    }

    /* selezione StartingPlayer -> Inizializzazione Turno 0 */
    public void setStartingPlayer(String startingPlayerNickname){  /* per sceglire il startingPlayer attraverso Nickname */

        for (Player p : model.getMatchPlayersList()) {
            if (p.getPlayerNickname().equals(startingPlayerNickname)) {
                startingPlayer = p;
                nameChanged = true;
                break;
            }
        }
        /* se esce dal for -> Nickname non trovato riprovare a chiedere */
        if(!nameChanged) {
            views.get(challenger).showMessage(Messages.tryAgain);
        }else{
            model.setStartingPlayerID(startingPlayer.getPlayerID());  //settare il startingPlayerID del model
            //messaggio di conferma
            for(Player p : model.getMatchPlayersList()){  /* notificare a tutti i Player il StartingPlayer */
                views.get(p).showMessage("Il primo player che fa la mossa è " + startingPlayer.getPlayerNickname());

            }
            /* fine parte scelta StartingPlayer inizio iniazializzazione turno : nameChanged = true */
            initializeTurn();
        }

    }



}

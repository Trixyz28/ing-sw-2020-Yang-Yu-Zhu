package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.Map;

public class InitController {

    private Model model;
    private Map<Player, View> views;

    private String god;
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

    public void initializeMatch(){
        model.randomChooseChallenger(); /* scegliere challenger per Random  */
        challengerStart();  /* inizia il Challenger */

    }

    private void challengerStart(){
        challenger = model.getMatchPlayersList().get(model.getChallengerID()); /* ID = indice iniziale */
        views.get(challenger).showMessage("Sei il Challenger!");
        model.showCompleteGodList();  /* mandare al Challenger la lista completa dei God */
    }

    private void startChoosingGod(){
        int index = challenger.getPlayerID()+1;  /* Player next al Challenger */
        if(index == model.getMatchPlayersList().size()){
            index = 0;
        }
        currentPlayer = model.getMatchPlayersList().get(index);
        View view = views.get(currentPlayer);
        view.showMessage("Scegli la tua divinità");



    }

    protected void chooseGod(Player player, String god){
        if (player.getPlayerNickname().equals(currentPlayer.getPlayerNickname())){
            GodList godList = model.getGodsList();
            godList.selectGod(god);
            if(godList.checkGod()){
                //far printare alla view la conferma della scelta
                views.get(currentPlayer).showMessage("Hai scelto" + god + "!");
                currentPlayer.godChoice(god);  /* assegnare al Player il God scelto */
                currentPlayer.createWorker(god);  /* creare worker determinato God */
                godList.removeFromGodList(god);  /* eliminare dalla currentGodList il god scelto */
                int index = currentPlayer.getPlayerID()+1;  /* next player*/
                if(index == model.getMatchPlayersList().size()){
                    index = 0;  /* quando l'indice arriva alla fine riiniziare*/
                }
                if(index == challenger.getPlayerID()){  /* fine giro scelta God */
                    fineGod();  /* dare direttamente la god rimanente al Challenger */
                }else{  /* continuare giro scleta */
                    currentPlayer = model.getMatchPlayersList().get(index);
                }
            }else{
                //far printare alla view la richiesta di ripetere la scelta
                views.get(player).showMessage("Riprova!");
                View view = views.get(currentPlayer);
                view.showMessage("Scegli la tua divinità");
            }
        }else {
            views.get(player).showMessage(Messages.wrongTurn);
        }
    }

    protected void fineGod(){
        /* dare direttamente la god rimanente al Challenger */
        View view = views.get(challenger);
        view.showMessage("Il god rimasto è " + god + "!");  //far printare alla view il messaggio del god assegnato
        GodList godList = model.getGodsList();
        god = godList.getCurrentGodList().get(0);  /* il god rimanente nella currentGodList viene assegnata al challenger */
        challenger.godChoice(god);
        challenger.createWorker(god);
        godChanged = true;
        /* fine parte scelta God */

        view.showMessage("Scegli il Player che inizia la partita!");
        /* necessario aspettare risposta dal client */

    }


    protected void defineGodList(String god, GodList godList){  //eseguire solo al challenger
        if(!godList.checkLength()) {
            //far scegliere Gods attraverso la view dal challenger )
            godList.selectGod(god);
            if(!godList.addInGodList()){  /* se il God scelto non viene aggiunto nella currentList */
                views.get(challenger).showMessage("Scelta invalida");
            }

            if(godList.checkLength()){
                for(Player p : model.getMatchPlayersList()){
                    views.get(p).showMessage("Il Challenger ha finito di scegliere i God");
                }
                startChoosingGod();
            }
        }
    }

    /* inizializzazione turno dopo scelta God e StartingPlayer */
    private void initializeTurn(){   /* fase di preparazione alla partita in Turno 0 per posizionare i workers */
        model.startCurrentTurn(); /* creare un nuovo Turn */
        Turn currentTurn = model.getCurrentTurn();
        currentPlayer = currentTurn.getCurrentPlayer();  /* primo currentPlayer == StartingPlayer */
        indexWorker = 0;
        views.get(currentPlayer).showMessage("Posiziona il worker" + indexWorker);
        model.place();  /* dalla view passa al Controller notificando la posizione (Operation) */
    }

    protected void placeWorker(Operation position){  //currentPosition = Tile dove posizionare il worker
        Tile currentPosition = model.commandToTile(position.getRow(), position.getColumn());
        if(currentPosition.isOccupiedByWorker()){
            views.get(currentPlayer).showMessage("Tile Occupied!");
        }else {
            /* mostrare mappa aggiornata */
            model.showBoard();
            //currentPlayer.chooseWorker(indexWorker).place(currentPlayer);
            indexWorker++;
            if(indexWorker > 1){  /* passare al nextPlayer */
                indexWorker = 0;
                Turn currentTurn = model.getCurrentTurn();
                currentTurn.setCurrentPlayer(model.getMatchPlayersList().get(model.getNextPlayerIndex()));
                currentPlayer = currentTurn.getCurrentPlayer();
                if(currentPlayer.getPlayerID() == model.getStartingPlayerID()){  /* fine giro : inizio partita */
                    endInitialize = true;  //fine inizializzazione Turno
                }

            }
            if(!endInitialize) {
                views.get(currentPlayer).showMessage("Posiziona il worker" + indexWorker);
                model.place();  /* dalla view passa al Controller notificando la posizione (Operation) */
            }
        }
    }

    public void setStartingPlayer(String startingPlayerNickname){  /* per sceglire il startingPlayer attraverso Nickname */

        if(startingPlayerNickname.equals(challenger.getPlayerNickname())) {
            views.get(challenger).showMessage("Non puoi scegliere te stesso!!!");
            nameChanged = false;
        }else {
            for (Player p : model.getMatchPlayersList()) {
                if (p.getPlayerNickname().equals(startingPlayerNickname)) {
                    startingPlayer = p;
                    nameChanged = true;
                    break;
                }
            }
        }
        /* se esce dal for -> Nickname non trovato riprovare a chiedere */
        if(!nameChanged) {
            views.get(challenger).showMessage("Riprova!");
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

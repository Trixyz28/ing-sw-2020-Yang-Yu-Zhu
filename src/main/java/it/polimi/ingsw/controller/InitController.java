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
    private Tile currentPosition;
    private boolean changed;
    private boolean godChanged;
    private boolean nameChanged;


    public InitController(Model model, Map views) {
        this.model = model;
        this.views = views;
        changed = false;
        nameChanged = false;
        godChanged = false;
    }

    public void setGod(String god) {
        this.god = god;
    }

    protected boolean isGodChanged(){
        return godChanged;
    }

    protected boolean isNameChanged(){
        return nameChanged;
    }

    protected void setChanged() {
        changed = true;
    }

    public void initializeMatch(){
        model.randomChooseChallenger(); /* scegliere challenger per Random  */
        challengerStart();  /* inizia il Challenger */
        model.startCurrentTurn(); /* creare un nuovo Turn */
        initializeTurn();
    }

    private void challengerStart(){
        challenger = model.getMatchPlayersList().get(model.getChallengerID()); /* ID = indice iniziale */
        GodList godList = model.getGodsList(); /* ottenere la GodList per poter accedere alla currentGodList dal Challenger */
        defineGodList(challenger, godList);  //eseguire solo al challenger
        int index = challenger.getPlayerID()+1;  /* Player next al Challenger */
        if(index == model.getMatchPlayersList().size()){
            index = 0;
        }
        View view;
        for (Player p = model.getMatchPlayersList().get(index); p != challenger; index++) {
            /* challenger deve essere ultimo a scegliere */
            view = views.get(p);
            do {
                //view.godChoice(p);  -> fa scegliere al player il god e notify(God scelto)
                changed = false;
                view.showMessage("Scegli la tua divinità");
                /* necessario aspettare risposta dal client */
                waitChange();
                godList.selectGod(god);
                if(godList.checkGod()){
                    //far printare alla view la conferma della scelta
                    view.showMessage("Hai scelto" + god + "!");
                }else{
                    //far printare alla view la richiesta di ripetere la scelta
                    view.showMessage("Riprova!");
                }
                changed = false;
            }while(!godList.checkGod());  //ripetere la scelta se il god scelto non è nella currentList
            p.godChoice(god);  /* assegnare al Player il God scelto */
            p.createWorker(god);  /* creare worker determinato God */
            godList.removeFromGodList(god);  /* eliminare dalla currentGodList il god scelto */
            if(index == model.getMatchPlayersList().size()-1){  /* quando l'indice arriva alla fine riiniziare fino ad arrivare al challenger*/
                index = 0;
            }
        }

        /* alternativa: dare direttamente la god rimanente al Challenger */
        view = views.get(challenger);
        view.showMessage("Il god rimasto è " + god + "!");  //far printare alla view il messaggio del god assegnato
        god = godList.getCurrentGodList().get(0);  /* il god rimanente nella currentGodList viene assegnata al challenger */
        challenger.godChoice(god);
        challenger.createWorker(god);
        godChanged = true;
        /* fine parte scelta God */

        changed = false;
        view.showMessage("Scegli il Player che inizia la partita!");
        //view.chooseStartPlayer(challenger);  //scegliere da view (notify playerNickname)
        /* necessario aspettare risposta dal client */
        do{
            waitChange();
        }while(!nameChanged);
        changed = false;
        model.setStartingPlayerID(startingPlayer.getPlayerID());  //settare il startingPlayerID del model
        //messaggio di conferma
        for(Player p : model.getMatchPlayersList()){  /* notificare a tutti i Player il StartingPlayer */
            views.get(p).showMessage("Il primo player che fa la mossa è " + startingPlayer.getPlayerNickname());

        }
        /* fine parte scelta StartingPlayer inizio nameChanged = true */

    }

    private void defineGodList(Player challenger, GodList godList){
        changed = false;
        views.get(challenger).showMessage("Sei il Challenger!");
        model.showCompleteGodList();  /* mandare al Challenger la lista completa dei God */
        while(!godList.checkLength()) {
            //view.defineGodList(challenger) -> far scegliere Gods attraverso la view dal challenger )
            waitChange();
            godList.selectGod(god);
            godList.addInGodList();
            changed = false;
        }
    }

    private void initializeTurn(){   /* fase di preparazione alla partita in Turno 0 per posizionare i workers */
        Turn currentTurn = model.getCurrentTurn();
        ArrayList<Player> playerList = model.getMatchPlayersList();
        Player currentPlayer;

        for (int i = 0; i < playerList.size(); i++) {  /* far posizionare i workers dai players */
            currentPlayer = currentTurn.getCurrentPlayer();  /* primo currentPlayer == StartingPlayer */
            for (int j = 0; j < 2; j++) {  /* ciclo per i 2 workers */
                changed = false;
                do {  /* ripetere la scelta se il Tile scelto è occupato */
                    views.get(currentPlayer).showMessage("Posiziona il worker" + j);// chiedere ai players di posizionare il worker
                    model.place();
                    /* dalla view passa al Controller notificando la posizione (Operation) */
                    waitChange();
                    changed = false;
                    /* mostrare mappa aggiornata */
                    model.showBoard();
                }while(currentPosition.isOccupiedByWorker());
                // currentPlayer.chooseWorker(j).move(currentPosition);  /* posizionare il worker al currentPosition */
            }
            currentTurn.setCurrentPlayer(playerList.get(model.getNextPlayerIndex()));  /* passare al nextPlayer */
        }
        //fine inizializzazione Turno

    }

    public void setStartingPlayer(String startingPlayerNickname){  /* per sceglire il startingPlayer attraverso Nickname */

        if(startingPlayer.equals(challenger.getPlayerNickname())) {
            views.get(challenger).showMessage("Non puoi scegliere te stesso!!!");
            nameChanged = false;
        }else if(nameChanged) {
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
        }

    }

    public void setCurrentPosition(Operation position){  //currentPosition = Tile dove posizionare il worker
        currentPosition = model.commandToTile(position.getRow(), position.getColumn());
    }

    private void waitChange(){
        while (true) {
            if (changed) {
                break;
            }
        }
    }


}

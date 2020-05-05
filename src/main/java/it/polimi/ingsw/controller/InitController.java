package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class InitController extends Controller {

    private Model model;
    private View view;

    private String god;
    private Player startingPlayer;
    private Tile currentPosition;


    public InitController(Model model, View view) {
        super(model,view);
    }


    public void initializeMatch(){
        model.randomChooseChallenger(); /* scegliere challenger per Random  */
        challengerStart();  /* inizia il Challenger */
        model.startCurrentTurn(); /* creare un nuovo Turn */
        initializeTurn();
    }

    private void challengerStart(){
        Player challenger = model.getMatchPlayersList().get(model.getChallengerID()); /* ID = indice */
        GodList godList = model.getGodsList(); /* ottenere la GodList per poter accedere alla currentGodList dal Challenger */
        defineGodList(challenger, godList);  //eseguire solo al challenger
        int index = challenger.getPlayerID()+1;  /* challengerID = indice del challenger */
        if(index == model.getMatchPlayersList().size()){
            index = 0;
        }
        for (Player p = model.getMatchPlayersList().get(index); p != challenger; index++) {
            /* challenger deve essere ultimo a scegliere */
            do {
                //view.godChoice(p);  -> fa scegliere al player il god e notify(God scelto)
                godList.selectGod(god);
                if(godList.checkGod()){
                    //far printare alla view la conferma della scelta
                }else{
                    //far printare alla view la richiesta di ripetere la scelta
                }
            }while(!godList.checkGod());  //ripetere la scelta se il god scelto non è nella currentList
            p.godChoice(god);  /* scegliere god dalla view */
            p.createWorker(god);  /* creare worker determinato God */
            godList.removeFromGodList(god);  /* eliminare dalla currentGodList il god scelto */
            if(index == model.getMatchPlayersList().size()-1){  /* quando l'indice arriva alla fine riiniziare fino ad arrivare al challenger*/
                index = 0;
            }
        }

        //view.godChoice(challenger); -> scelta God in view e notify(God scelto)  /* alternativa: dare direttamente la god rimanente */
        god = godList.getCurrentGodList().get(0);  /* il god rimanente nella currentGodList viene assegnata al challenger */
        challenger.godChoice(god);
        challenger.createWorker(god);
        //far printare alla view il messaggio del god assegnato

        //view.chooseStartPlayer(challenger);  //scegliere da view (notify un player)
        model.setStartingPlayerID(startingPlayer.getPlayerID());  //settare il startingPlayerID del model

    }

    private void defineGodList(Player challenger, GodList godList){
        while(!godList.checkLength()) {
            //view.defineGodList(challenger -> far scegliere Gods attraverso la view dal challenger )
            godList.selectGod(god);
            godList.addInGodList();
        }
    }

    private void initializeTurn(){   /* fase di preparazione alla partita in Turno 0 per posizionare i workers */
        Turn currentTurn = model.getCurrentTurn();
        ArrayList<Player> playerList = model.getMatchPlayersList();
        Player currentPlayer;
        for (int i = 0; i < playerList.size(); i++) {  /* far posizionare i workers dai players */
            currentPlayer = currentTurn.getCurrentPlayer();
            for (int j = 0; j < 2; j++) {  /* ciclo per i 2 workers */
                view.placeWorkers(currentPlayer);  // chiedere ai players di posizionare il worker
                /* dalla view passa al controller notificando la posizione (Operation) */
                currentPlayer.chooseWorker(j).move(currentPosition);  /* posizionare il worker al currentPosition */
            }
            currentTurn.setCurrentPlayer(playerList.get(model.getNextPlayerIndex()));  /* passare al nextPlayer */
        }
        //fine inizializzazione Turno

    }

    @Override
    public void update(Object arg) {

        if(arg instanceof String){ /* variabile provvisoria del God scelto */
            god = (String)arg;
        }
        if(arg instanceof Player){  /* notificata dalla view per sceglire il startingPlayer */
            startingPlayer = (Player)arg;
        }
        if(arg instanceof Operation){  //per ora: ottenere posizione iniziale workers
            if(((Operation) arg).getType() == 0){  //type 0 -> posizione default
                currentPosition = model.commandToTile(((Operation) arg).getRow(), ((Operation) arg).getColumn());
                //currentPosition = Tile dove posizionare il worker
            }
        }
    }
}

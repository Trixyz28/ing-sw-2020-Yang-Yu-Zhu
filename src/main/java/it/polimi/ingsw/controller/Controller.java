package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;



public class Controller implements Observer {

    private Match match;
    private View view;
    public String god;
    public Player startingPlayer;

    public Controller(Match match, View view) {
        this.match = match;
        this.view = view;
    }


    public void initializeMatch(Player p){
        match.createMap();  /* creare la mappa inizializzata */
        match.createPlayers();  /* match.playersList = playersList (come parametro) */
        match.randomChooseChallenger(); /* scegliere challenger per Random e riordinare la lista dei Players */
        match.setCurrentTurn(); /* creare un nuovo Turn */

    }

    public void challengerStart(){
        Player challenger = match.getPlayersList().get(0);
        challenger.defineGodList(); /* far scegliere Gods attraverso la view (view.defineGodList()) */
        for(Player p : match.getPlayersList()){
            if(p!=challenger) {  /* challenger deve essere ultimo a scegliere */
                //view.godChoice();  -> fa scegliere al player il god e notify(God scelto) + check
                p.godChoice(god);  /* scegliere god dalla view */
                p.createWorker(god);  /* creare worker determinato God */
            }
        }
        //view.godChoice(); -> scelta God in view e notify(God scelto)
        challenger.godChoice(god);  /* scegliere da view */
        challenger.createWorker(god);
        //view.chooseStartPlayer();  //scegliere da view
        challenger.chooseStartPlayer(startingPlayer.getPlayerID());  /* scegliere player che inizia e cambiare ordine lista Players */
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o==view && arg instanceof Player){  /* ??? non va bene ArrayList */
            initializeMatch((Player)arg);
            challengerStart();
        }
        if(o==view && arg instanceof String){ /* variabile provvisoria del God scelto */
                god = (String)arg;
        }
        if(o==view && arg instanceof Player){  /* notificata dalla view per sceglire il startingPlayer */
            startingPlayer = (Player)arg;
        }
    }


}

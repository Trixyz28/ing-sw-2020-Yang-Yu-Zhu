package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.Observer;

import it.polimi.ingsw.model.Player;

import java.util.ArrayList;


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
        match.randomChooseChallenger(); /* scegliere challenger per Random e riordinare la lista dei Players */
        match.startCurrentTurn(); /* creare un nuovo Turn */

    }

    public void challengerStart(){
        Player challenger = match.getPlayersList().get(match.getChallengerID()-1); /* ID = indice +1 */
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
    public void update(Object arg) {
        if(arg instanceof Player){  /* ??? non va bene ArrayList */
            initializeMatch((Player)arg);
            challengerStart();
        }
        if(arg instanceof String){ /* variabile provvisoria del God scelto */
                god = (String)arg;
        }
        if(arg instanceof Player){  /* notificata dalla view per sceglire il startingPlayer */
            startingPlayer = (Player)arg;
        }
    }
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GodList;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

public class InitController extends Controller {

    public String god;
    public Player startingPlayer;

    public InitController(Model model, View view) {
        super(model, view);
    }


    public void initializeMatch(Lobby lobby){
        lobby.createMatch(model);
        model.randomChooseChallenger(); /* scegliere challenger per Random  */
        model.startCurrentTurn(); /* creare un nuovo Turn */
    }

    public void challengerStart(){
        Player challenger = model.getMatchPlayersList().get(model.getChallengerID()-1); /* ID = indice +1 */
        GodList godList = model.getGodsList(); /* ottenere la GodList per poter accedere alla currentGodList dal Challenger */
        defineGodList(challenger, godList);  //eseguire solo al challenger
        int index = challenger.getPlayerID();  /* challengerID = indice del player next al challenger */
        for (Player p = model.getMatchPlayersList().get(index); index < model.getMatchPlayersList().size() && p != challenger; index++) {
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
        //challenger.chooseStartPlayer(startingPlayer.getPlayerID());  /* scegliere player che inizia e cambiare ordine lista Players */
    }

    public void defineGodList(Player challenger, GodList godList){
        while(!godList.checkLength()) {
            //view.defineGodList(challenger -> far scegliere Gods attraverso la view dal challenger )
            godList.selectGod(god);
            godList.addInGodList();
        }
    }

    @Override
    public void update(Object arg) {
        if(arg instanceof Lobby){  /* ??? non va bene ArrayList */
            initializeMatch((Lobby)arg);
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

package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.Observer;

import java.util.HashMap;
import java.util.Map;


public class Controller implements Observer {

    private Model model;
    private Map<Player, View> views = new HashMap<>();

    private InitController initController;
    private TurnController turnController;
    private MoveController moveController;
    private BuildController buildController;


    public Controller(Model model, Map views) {
        this.model = model;
        this.views = views;
    }

    public void minorControllers(Model model, Map views) {
        initController = new InitController(model, views);
        turnController = new TurnController(model, views);
        moveController = new MoveController(model);
        buildController = new BuildController(model);

    }


    @Override
    public void update(Object arg) {

        if (arg.equals("setup")) {
            initController.initializeMatch();  /* inizializzazione con decisioni Challenger */
            turnController.nextTurn();  /* inizio partita con Turn 1*/
        }

        if (arg instanceof String) {
            initController.setChanged();
            if(!initController.isGodChanged()) {  /* se non è finita parte scelta god arg = God */
                initController.setGod((String) arg);  /* variabile provvisoria del God scelto */
            }else {  /* finita parte God arg = StartingPlayerNickname */
                initController.setStartingPlayer((String) arg);  /* set il StartingPlayer dal Nickname */
            }
        }


        if (arg instanceof Operation) {
            Operation operation = (Operation) arg;

            if (operation.getType() == 0) {  //type 0 -> posizione default
                initController.setChanged();
                initController.setCurrentPosition((Operation) arg);
            }

            if (operation.getType() == 1) {  //type 1 -> move
                boolean flag = moveController.moveWorker((Operation) arg);
                if (flag) {  /* flag : true = move riuscita; false = richiedere move */
                    turnController.endMove();  /* aggiornare Turn fine Move */
                } else {
                    //mostrare view messaggio di posizione errata e ripetere mossa
                    //view.move();
                }
            }

            if (operation.getType() == 2) {  //type 2 -> build
                boolean flag = buildController.build((Operation) arg);
                if (flag) {  /* flag : true = build riuscita; false = richiedere build */
                    turnController.endTurn((Operation) arg);  /* aggiornare Turn fine Build */
                } else {
                    //messaggio view errato comando e ripetere scelta
                    //view.build();
                }
            }

        }

        if (arg instanceof Integer) {  /* indice del Worker scelto 0 o 1 */
            turnController.setChosenWorker((Integer) arg);
        }

    }
}

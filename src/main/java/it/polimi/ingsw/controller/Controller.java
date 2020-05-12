package it.polimi.ingsw.controller;

import it.polimi.ingsw.lobby.LobbyController;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.Observer;


public class Controller implements Observer {

    protected Model model;
    protected View view;

    private InitController initController;
    private TurnController turnController;
    private MoveController moveController;
    private BuildController buildController;


    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void minorControllers(Model model, View view) {
        initController = new InitController(model,view);
        turnController = new TurnController(model,view);
        moveController = new MoveController(model,view);
        buildController = new BuildController(model,view);

    }





    @Override
    public void update(Object arg) {

        if(arg.equals("setup")) {
            initController.initializeMatch();  /* inizializzazione con decisioni Challenger */
            turnController.nextTurn();  /* inizio partita con Turn 1*/
        }

        if(arg instanceof String){
            initController.setStartingPlayer((String)arg);  /* set il StartingPlayer dal Nickname */
        }


        if(arg instanceof Operation) {
            Operation operation = (Operation)arg;

            if(operation.getType() == 0){  //type 0 -> posizione default
                initController.setCurrentPosition((Operation)arg);
            }

            if(operation.getType()==1) {  //type 1 -> move
                boolean flag = moveController.moveWorker((Operation)arg);
                if(flag) {  /* flag : true = move riuscita; false = richiedere move */
                    turnController.endMove();  /* aggiornare Turn fine Move */
                }else{
                    //mostrare view messaggio di posizione errata e ripetere mossa
                    view.move();
                }
            }

            if(operation.getType()==2) {  //type 2 -> build
                boolean flag = buildController.build((Operation) arg);
                if(flag) {  /* flag : true = build riuscita; false = richiedere build */
                    turnController.endTurn((Operation) arg);  /* aggiornare Turn fine Build */
                }else{
                    //messaggio view errato comando e ripetere scelta
                    view.build();
                }
            }

        }

        if(arg instanceof Integer){  /* indice del Worker scelto 0 o 1 */
            turnController.setChosenWorker((Integer) arg);
        }

    }

    public void updateGod(String god) {  /* variabile provvisoria del God scelto */
        initController.setGod(god);
    }
}

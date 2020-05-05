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


        if(arg instanceof Operation) {
            Operation operation = (Operation)arg;

            if(operation.getType()==1) {



                //moveController.



            }

            if(operation.getType()==2) {

                //buildController.


            }

        }






    }
}

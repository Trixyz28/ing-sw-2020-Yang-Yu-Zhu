package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.observers.Observer;



public class Controller implements Observer {

    private Model model;

    private InitController initController;
    private TurnController turnController;
    private MoveController moveController;
    private BuildController buildController;


    public Controller(Model model) {
        this.model = model;
        initController = new InitController(model);
    }

    private void minorControllers(Model model) {
        turnController = new TurnController(model);
        moveController = new MoveController(model);
        buildController = new BuildController(model);
    }

    private boolean checkTurn(Object arg) {
        if(arg instanceof Operation){
           return model.checkTurn(((Operation) arg).getPlayer());
        }else if (arg instanceof GameMessage){
           return model.checkTurn(((GameMessage)arg).getPlayer());
        }else if (arg instanceof Integer){
            return true;
        }
        return false;
    }


    @Override
    public void update(Object arg) {

        if (arg.equals("setup")) {
            /* inizializzazione con decisioni Challenger */
            initController.initializeMatch();

        } else if (checkTurn(arg)) {

            if (arg instanceof Operation) {
                opUpdate((Operation) arg);
            } else if (arg instanceof Integer) {  /* indice del Worker scelto 0 o 1 */
                turnController.setChosenWorker((Integer) arg);
            } else if (arg instanceof GameMessage) {
                GameMessage gm = (GameMessage) arg;

                if (gm.getMessage() == null) {
                    /* Scelta god e StartingPlayerNickname */
                    stringUpdate(gm.getPlayer(), gm.getAnswer());
                } else {
                    /* GodPower answers */
                    if(model.checkAnswer((GameMessage)arg)) {
                        gmUpdate();
                    }
                }
            }
        }
    }

    private void opUpdate(Operation operation){
        if (operation.getType() == 0) {  //type 0 -> posizione default
            initController.placeWorker(operation);
            if(initController.isEndInitialize()){
                /* creazione controller minori */
                minorControllers(model);
                /* inizio partita con Turn 1 */
                turnController.nextTurn();
            }
        } else {
            boolean flag = false;
            if(operation.getType() == 1) {  //type 1 -> move
                flag = moveController.moveWorker(operation);
            }else if (operation.getType() == 2){
                flag = buildController.build(operation);
            }
            /* flag : true = move/build riuscita; false = richiedere move */
            if (flag) {
                turnController.endOperation();
            } else {
                //mostrare messaggio di posizione errata e ripetere mossa
                model.sendMessage(Messages.wrongOperation);
                model.operation();
            }
        }
    }


    private void stringUpdate(String player, String answer){
        Player challenger = model.getMatchPlayersList().get(model.getChallengerID());
        if(player.equals(challenger.getPlayerNickname())){
            /* se challenger -> definire currentList */
            if (!model.getGodsList().checkLength()) {
                /* se non è finita parte scelta god arg = God */
                initController.defineGodList(answer, model.getGodsList());
            }else{
                /* finita parte God arg = StartingPlayerNickname */
                initController.setStartingPlayer(answer);
            }
        }else {
            /* scelta God */
            initController.chooseGod(answer);
        }
    }

    private void gmUpdate() {
        UndecoratedWorker worker = model.getCurrentTurn().getChosenWorker();
        /* if godPower = true -> use Power (another operation)  false -> end operation */
        if(worker.getGodPower()){
            worker.setGodPower(false);
            model.showBoard();
            model.operation();
        }else {
            turnController.endOperation();
        }

    }
}

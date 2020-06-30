package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.messages.Tags;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.observers.Observer;


public class Controller implements Observer {

    private final Model model;

    private final InitController initController;
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

    private boolean checkTurn(Obj arg) {
        return model.checkTurn(arg.getReceiver());
    }


    @Override
    public void update(Object arg) {

        if (arg.equals("setup")) {
            /* initialize match -> challenger */
            initController.initializeMatch();

        } else if (checkTurn((Obj) arg)) {

            if (((Obj) arg).getTag().equals(Tags.operation)) {
                opUpdate(((Obj) arg).getOperation());

            } else if (((Obj) arg).getTag().equals(Tags.gMsg)) {
                GameMessage gm = ((Obj) arg).getGameMessage();

                if (gm.getMessage() == null) {
                    /* Choose god and StartingPlayerNickname */
                    stringUpdate(((Obj) arg).getReceiver(), gm.getAnswer());
                } else {
                    if (model.checkAnswer(gm)) {
                        /* Answers : WorkerIndex, ConfirmWorker, GodPower */
                        if (gm.getMessage().equals(Messages.worker)) {
                            /* worker index */
                            turnController.setChosenWorker(Integer.parseInt(gm.getAnswer()));
                        }else if (gm.getMessage().equals(Messages.confirmWorker)){
                            /* if answer is "YES" -> confirm worker */
                            turnController.choseWorker();
                        }else {
                            /* GodPower answers */
                            gmUpdate();
                        }
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
                model.sendMessage(Tags.boardMsg,Messages.wrongOperation);
                model.operation();
            }
        }
    }


    private void stringUpdate(String player, String answer){
        Player challenger = model.getMatchPlayersList().get(model.getChallengerID());
        if(player.equals(challenger.getPlayerNickname())){
            /* if the player is the challenger -> define currentList or set Start player*/
            if (!model.getGodsList().checkLength()) {
                /* se non è finita parte scelta god arg = God */
                initController.defineGodList(answer, model.getGodsList());
            } else {
                /* finita parte God arg = StartingPlayerNickname */
                initController.setStartingPlayer(answer);
            }
        } else {
            /* other players -> choose God */
            initController.chooseGod(answer);
        }
    }

    private void gmUpdate() {
        UndecoratedWorker worker = model.getCurrentTurn().getChosenWorker();
        /* if godPower = true -> use Power (another operation)  false -> end operation */
        if(worker.getGodPower()){
            /* se il power è attivato implica che la mossa è possibile -> no ulteriore check */
            worker.setGodPower(false);
            model.sendBoard();
            model.operation();
        }else {
            turnController.endOperation();
        }

    }
}

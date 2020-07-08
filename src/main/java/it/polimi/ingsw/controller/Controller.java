package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.Messages;
import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.messages.Tags;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.observers.Observer;

/**
 * Controller that handles all the other minorControllers for the flow of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Controller implements Observer<Obj> {

    private final Model model;

    private final InitController initController;
    private TurnController turnController;
    private MoveController moveController;
    private BuildController buildController;

    /**
     * Creates a <code>Controller</code> with the specified attributes.
     * @param model Variable that represents the match. Also represents the Model in the MVC pattern.
     */
    public Controller(Model model) {
        this.model = model;
        initController = new InitController(model);
    }

    /**
     * Creates the minorControllers associated with the main <code>Controller</code>.
     * @param model Variable that represents the match. Also represents the Model in the MVC pattern.
     */
    private void minorControllers(Model model) {
        turnController = new TurnController(model);
        moveController = new MoveController(model);
        buildController = new BuildController(model);
    }

    /**
     * Checks if the turn and the user are aligned.
     * @param arg Object that is used on client-server communication.
     * @return A boolean: <code>True</code> if it's the user's turn, otherwise<code>False</code>.
     */
    private synchronized boolean checkTurn(Obj arg) {
        return model.checkTurn(arg.getReceiver());
    }

    /**
     * {@inheritDoc}
     * Notified by a View, the controller handles the received Obj.
     * If the Tag is "SETUP", initialize the match. Otherwise checks the turn and updates the game state.
     */
    @Override
    public void update(Obj arg) {

        if (arg.getTag().equals(Tags.SETUP)) {
            /* initialize match -> challenger */
            initController.initializeMatch();

        } else if (checkTurn(arg)) {

            if (arg.getTag().equals(Tags.OPERATION)) {
                opUpdate(arg.getOperation());

            } else if (arg.getTag().equals(Tags.G_MSG)) {
                GameMessage gm = arg.getGameMessage();

                if (gm.getMessage() == null) {
                    /* Choose god and StartingPlayerNickname */
                    stringUpdate(arg.getReceiver(), gm.getAnswer());
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

    /**
     * Sets the game and controls the flow of the turns for <code>operation</code> messages.
     * @param operation The type of message between clients-server used for <code>place</code>, <code>move</code> and <code>build</code>.
     */
    private void opUpdate(Operation operation){
        if (operation.getType() == 0) {  //type 0 -> place
            initController.placeWorker(operation);
            if(initController.isEndInitialize()){
                /* create minor controller */
                minorControllers(model);
                /* start Turn 1 */
                turnController.nextTurn();
            }
        } else {
            boolean flag = false;
            if(operation.getType() == 1) {  //type 1 -> move
                flag = moveController.moveWorker(operation);
            }else if (operation.getType() == 2){
                flag = buildController.build(operation);
            }
            /* flag : true = move/build end successfully; false = try again */
            if (flag) {
                turnController.endOperation();
            } else {
                //send invalid operation message
                model.sendMessage(Tags.BOARD_MSG,Messages.wrongOperation);
                model.operation();
            }
        }
    }

    /**
     * Updates the game state based on the <code>answer</code> sent by a particular player.
     * Used in the first phases of the game: defining God Lists, choosing God Powers, choosing Start Player.
     * @param player Variable that indicates the player at issue.
     * @param answer Variable that indicates the answer sent by the player at issue.
     */
    private void stringUpdate(String player, String answer){
        Player challenger = model.getMatchPlayersList().get(model.getChallengerID());
        if(player.equals(challenger.getPlayerNickname())){
            /* if the player is the challenger -> define currentList or set Start player*/
            if (!model.getGodsList().checkLength()) {
                /* arg = God */
                initController.defineGodList(answer, model.getGodsList());
            } else {
                /* arg = StartingPlayerNickname */
                initController.setStartingPlayer(answer);
            }
        } else {
            /* other players -> choose God */
            initController.chooseGod(answer);
        }
    }

    /**
     * Handles the activation of GodPowers of the <code>workers</code> of the players
     */
    private void gmUpdate() {
        UndecoratedWorker worker = model.getCurrentTurn().getChosenWorker();
        /* if godPower = true -> use Power (another operation)  false -> end operation */
        if(worker.getGodPower()){
            /* godPower active -> another operation */
            worker.setGodPower(false);
            model.sendBoard();
            model.operation();
        }else {
            turnController.endOperation();
        }

    }
}

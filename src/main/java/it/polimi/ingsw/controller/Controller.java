package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.observers.Observer;

import java.util.HashMap;
import java.util.Map;


public class Controller implements Observer {

    private Model model;
    private Map<Player, View> views = new HashMap<>();

    private InitController initController;
    private TurnController turnController;
    private MoveController moveController;
    private BuildController buildController;


    public Controller(Model model, Map<Player, View> views) {
        this.model = model;
        this.views = views;
        minorControllers(model,views);
    }

    public void minorControllers(Model model, Map<Player, View> views) {
        initController = new InitController(model, views);
        turnController = new TurnController(model, views);
        moveController = new MoveController(model);
        buildController = new BuildController(model);
    }


    public boolean checkTurn(String player) {
        if (initController.isEndInitialize() || initController.getCurrentPlayer().equals(player)) {
            if (!initController.isEndInitialize()) {
                return true;
            }
            if (model.getCurrentTurn().getCurrentPlayer().getPlayerNickname().equals(player)) {
                return true;
            }
        }

        for (Player p : model.getMatchPlayersList()) {
            if (p.getPlayerNickname().equals(player)) {
                views.get(p).showMessage(Messages.wrongTurn);
                return false;
            }
        }
            return false;


    }


    @Override
    public void update(Object arg) {

        if (arg.equals("setup")) {
            initController.initializeMatch();  /* inizializzazione con decisioni Challenger */

        }else

        if (arg instanceof Operation) {
            Operation operation = (Operation) arg;
            if(checkTurn(operation.getPlayer())) {

                if (operation.getType() == 0) {  //type 0 -> posizione default
                    initController.placeWorker((Operation) arg);
                    if(initController.isEndInitialize()){  /* inizio partita con Turn 1 */
                        turnController.nextTurn();
                    }
                } else

                if (operation.getType() == 1) {  //type 1 -> move
                    //System.out.println("Entrando in moveController");
                    boolean flag;
                    if(turnController.isPrometheus()){  /* Prometheus ha fatto la Build prima della move*/
                        flag = moveController.moveWorker((Operation) arg, false);
                    }else {
                        flag = moveController.moveWorker((Operation) arg, turnController.CanMoveUp());  /* +condizione di canMoveUp */
                    }
                    //System.out.println("Uscito dal move" + turnController.CanMoveUp());
                    if (flag && turnController.isArtemis()) {
                        turnController.moveArtemis();
                    } else {
                        if (flag) {  /* flag : true = move riuscita; false = richiedere move */
                            turnController.endMove();  /* aggiornare Turn fine Move */
                        } else {
                            //mostrare view messaggio di posizione errata e ripetere mossa
                            //view.move();
                            views.get(model.getCurrentTurn().getCurrentPlayer()).showMessage("Impossibile fare questa mossa!");
                            model.move();
                        }
                    }
                }else

                if (operation.getType() == 2) {  //type 2 -> build
                    boolean flag = buildController.build((Operation) arg);
                    if (flag && turnController.isDemeter()) {  /* Se Demeter fare il secondo build */
                        turnController.buildDemeter();
                    } else if (buildController.isAtlas()) {
                        //views.get(model.getCurrentTurn().getCurrentPlayer()).showMessage("Block o Dome?");
                        model.sendMessage(Messages.Atlas);
                    } else if (flag && turnController.isPrometheus()) {  /* dopo build continuare normalmente */
                        turnController.startMove();  /* isPrometheus rimane true */
                    }else if (flag && turnController.isHephaestus()){  /* check Hephaestus build un blocco in più */
                        if(!buildController.buildHephaestus()) {  /* Hephaestus non può build */
                            turnController.endTurn((Operation) arg);
                        }
                    } else if (flag) {  /* flag : true = build riuscita; false = richiedere build */
                        turnController.endTurn((Operation) arg);  /* aggiornare Turn fine Build */
                    } else {
                        //messaggio view errato comando e ripetere scelta
                        views.get(model.getCurrentTurn().getCurrentPlayer()).showMessage("Impossibile fare questa mossa!");
                        model.build();
                    }
                }
            }

        }else

        if (arg instanceof Integer) {  /* indice del Worker scelto 0 o 1 */
            if (!turnController.isWorkerChanged()) {  /* accettare l'int solo se il worker non è ancora deciso */
                turnController.setChosenWorker((Integer) arg);
            }
        }else

        if(arg instanceof GameMessage) {
            String player = ((GameMessage) arg).getPlayer();
            String message = ((GameMessage) arg).getMessage();
            String answer = ((GameMessage) arg).getAnswer();

            if (message == null) {  /* dopo preparazione partita il controller non accetta più Stringhe */
                if (!initController.isGodChanged() || !initController.isNameChanged()) {
                    Player challenger = model.getMatchPlayersList().get(model.getChallengerID());
                    if (!initController.isGodChanged()) {  /* se non è finita parte scelta god arg = God */
                        if(player.equals(challenger.getPlayerNickname())){
                            /* se challenger -> definire currentList */
                            initController.defineGodList(answer, model.getGodsList());
                        }else {
                            /* scelta God */
                            initController.chooseGod(player, answer);
                        }
                    } else if(player.equals(challenger.getPlayerNickname())){
                        /* finita parte God arg = StartingPlayerNickname */
                        initController.setStartingPlayer(answer);  /* set il StartingPlayer dal Nickname */
                    } else {
                        checkTurn(player);
                    }
                }else {
                    checkTurn(player);
                }


            }else if(checkTurn(player)) {


                if (message.equals(Messages.Artemis)) {  /* YES or NO */
                    if (answer.equals("YES")) {  /* Yes -> move in più (con Artemis = false) */
                        turnController.moveArtemis();
                    } else {
                        turnController.endMove();  /* aggiornare Turn fine Move */
                    }
                }else
                if (message.equals(Messages.Atlas)) {  /* se Atlas controllare build BLOCK or DOME */
                    if (buildController.checkBlockDome(answer)) {  /* se il build va a buon fine */
                        model.showBoard();
                        turnController.endTurn(buildController.getOperation());
                    } else {  /* messaggio errato */
                        model.sendMessage(Messages.Atlas);
                    }
                }else
                if (message.equals(Messages.Demeter)) {  /* YES or NO */
                    if (answer.equals("YES")) {  /* Yes -> move in più (con Artemis = false) */
                        turnController.buildDemeter();
                    } else {  /* fine turno */
                        turnController.endTurn(buildController.getOperation());  /* ultima build effettuata */
                    }
                }else
                if (message.equals(Messages.Hephaestus)) {  /* Block aggiuntivo : YES or NO */
                    if (answer.equals("YES")) {
                        model.showBoard();
                        Operation op = buildController.getOperation();  /* ultima build effettuata */
                        model.getCurrentTurn().getChosenWorker().buildBlock(model.commandToTile(op.getRow(), op.getColumn()));
                    }

                }else
                if (message.equals(Messages.Prometheus)) {  /* Move or Build? */
                    if (answer.equals("MOVE")) {
                        turnController.movePrometheus();  /* Il worker procede come un worker normale */
                    } else if (answer.equals("BUILD")) {  /* isPrometheus rimane true */
                        views.get(model.getCurrentTurn().getCurrentPlayer()).showMessage("Builda!");
                        model.build();
                    }
                }
            }
        }
    }
}

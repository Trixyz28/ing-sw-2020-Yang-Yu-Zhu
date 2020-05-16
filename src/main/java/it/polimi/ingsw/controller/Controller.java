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

        if (arg instanceof String) {  /* dopo preparazione partita il controller non accetta più Stringhe */
            if (!initController.isGodChanged() || !initController.isNameChanged()) {
                initController.setChanged();
                if (!initController.isGodChanged()) {  /* se non è finita parte scelta god arg = God */
                    initController.setGod((String) arg);  /* variabile provvisoria del God scelto */
                } else {  /* finita parte God arg = StartingPlayerNickname */
                    initController.setStartingPlayer((String) arg);  /* set il StartingPlayer dal Nickname */
                }
            }

        }

        if (arg instanceof Operation) {
            Operation operation = (Operation) arg;

            if (operation.getType() == 0) {  //type 0 -> posizione default
                initController.setChanged();
                initController.setCurrentPosition((Operation) arg);
            }

            if (operation.getType() == 1) {  //type 1 -> move
                boolean flag = moveController.moveWorker((Operation) arg, turnController.CanMoveUp());  /* +condizione di canMoveUp */
                if (flag && turnController.isArtemis()) {
                    turnController.setChanged();
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
            }

            if (operation.getType() == 2) {  //type 2 -> build
                boolean flag = buildController.build((Operation) arg);
                if(flag && turnController.isDemeter()){  /* Se Demeter fare il secondo build */
                    turnController.setChanged();
                }else if (buildController.isAtlas()){
                    //views.get(model.getCurrentTurn().getCurrentPlayer()).showMessage("Block o Dome?");
                    model.sendMessage(Messages.Atlas);
                }else if(flag && turnController.isPrometheus()){  /* dopo build uscire dal while e continuare normalmente */
                    turnController.setChanged();
                } else if (flag) {  /* flag : true = build riuscita; false = richiedere build */
                    turnController.endTurn((Operation) arg);  /* aggiornare Turn fine Build */
                } else {
                    //messaggio view errato comando e ripetere scelta
                    views.get(model.getCurrentTurn().getCurrentPlayer()).showMessage("Impossibile fare questa mossa!");
                    model.build();
                }
            }

        }

        if (arg instanceof Integer) {  /* indice del Worker scelto 0 o 1 */
            if(!turnController.isWorkerChanged()) {  /* accettare l'int solo se il worker non è ancora deciso */
                turnController.setChosenWorker((Integer) arg);
            }
        }

        if(arg instanceof GameMessage){
            String message = ((GameMessage) arg).getMessage();
            String answer = ((GameMessage) arg).getAnswer();

            if(message.equals(Messages.Artemis)){
                if(answer.equals("YES")){  /* Yes -> move in più (con Artemis = false) */
                    turnController.moveArtemis();
                }else{
                    turnController.endMove();  /* aggiornare Turn fine Move */
                }
            }
            if(message.equals(Messages.Atlas)){  /* se Atlas controllare build BLOCK or DOME */
                if(buildController.checkBlockDome((String)arg)){  /* se il build va a buon fine */
                    turnController.endTurn(buildController.getOperation());
                }else{  /* messaggio errato */
                    model.sendMessage(Messages.Atlas);
                }
            }
            if(message.equals(Messages.Demeter)){
                if(answer.equals("YES")){  /* Yes -> move in più (con Artemis = false) */
                    turnController.buildDemeter();
                }else{  /* fine turno */
                    turnController.endTurn(buildController.getOperation());  /* ultima build effettuata */
                }
            }
            if(message.equals(Messages.Hephaestus)){
                if(answer.equals("YES")){
                    Operation op = buildController.getOperation();  /* ultima build effettuata */
                    model.getCurrentTurn().getChosenWorker().buildBlock(model.commandToTile(op.getRow(), op.getColumn()));
                }
                buildController.setChanged();
            }
            if(message.equals(Messages.Prometheus)){
                if(answer.equals("MOVE")){
                    turnController.movePrometheus();  /* Il worker procede come un worker normale */
                }else if(answer.equals("BUILD")){  /* isPrometheus rimane true */
                    views.get(model.getCurrentTurn().getCurrentPlayer()).showMessage("Builda!");
                    model.build();
                }
            }
        }

    }
}

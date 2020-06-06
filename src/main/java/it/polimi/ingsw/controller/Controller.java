package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.observers.Observer;

import java.util.Map;


public class Controller implements Observer {

    private Model model;

    private InitController initController;
    private TurnController turnController;
    private MoveController moveController;
    private BuildController buildController;


    public Controller(Model model, Map<Player, View> views) {
        this.model = model;
        minorControllers(model,views);
    }

    public void minorControllers(Model model, Map<Player, View> views) {
        initController = new InitController(model);
        turnController = new TurnController(model);
        moveController = new MoveController(model);
        buildController = new BuildController(model);
    }


    public boolean checkTurn(Object arg) {
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
            initController.initializeMatch();  /* inizializzazione con decisioni Challenger */

        } else if (checkTurn(arg)) {

            if (arg instanceof Operation) {
                opUpdate((Operation) arg);
            } else if (arg instanceof Integer) {  /* indice del Worker scelto 0 o 1 */
                //if (!turnController.isWorkerChanged()) {  /* accettare l'int solo se il worker non è ancora deciso */
                    turnController.setChosenWorker((Integer) arg);
                //}
            } else if (arg instanceof GameMessage) {
                GameMessage gm = (GameMessage) arg;

                if (gm.getMessage() == null) {  /* Scelta god e StartingPlayerNickname */
                    stringUpdate(gm.getPlayer(), gm.getAnswer());
                } else {
                    /* GodPower answers */
                        //gmUpdate(message, answer);
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
            if(initController.isEndInitialize()){  /* inizio partita con Turn 1 */
                turnController.nextTurn();
            }
        } else {
            boolean flag = false;
            if(operation.getType() == 1) {
                //type 1 -> move
                //System.out.println("Entrando in moveController");
                flag = moveController.moveWorker(operation);  /* +condizione di canMoveUp */
                //System.out.println("Uscito dal move" + turnController.CanMoveUp());
            }else if (operation.getType() == 2){
                flag = buildController.build(operation);
                /*
                UndecoratedWorker worker = model.getCurrentTurn().getChosenWorker();
            if (flag && !worker.getGodPower() && worker.useGodPower(true) == -1) {  /* dopo build continuare normalmente
                /* checkLosePrometheus
                model.showBoard();  /* mostrare mappa dopo build
                if(model.getCurrentTurn().getChosenWorker().canMove().size() != 0) {
                    model.move();  /* isPrometheus rimane true
                } else {
                    model.lose(model.getCurrentTurn().getCurrentPlayer());
                    turnController.checkGameOver();
                }
            }else
                */
            }
            if (flag) {  /* flag : true = move/build riuscita; false = richiedere move */
                turnController.endOperation();  /* aggiornare Turn fine Move */
            } else {
                //mostrare view messaggio di posizione errata e ripetere mossa
                model.sendMessage(Messages.wrongOperation);
                model.operation();
            }
        }
        /*
        else if  {  //type 2 -> build

            if (flag) {  /* flag : true = build riuscita; false = richiedere build
                turnController.endBuild();  /* aggiornare Turn fine Build
            } else {
                //messaggio view errato comando e ripetere scelta
                model.sendMessage(Messages.wrongOperation);
                model.build();
            }
        }
        */
    }


    private void stringUpdate(String player, String answer){
        /* dopo preparazione partita il controller non accetta più Stringhe */
        if (!initController.isNameChanged()) {
            Player challenger = model.getMatchPlayersList().get(model.getChallengerID());
            if(player.equals(challenger.getPlayerNickname())){
                /* se challenger -> definire currentList */
                if (!model.getGodsList().checkLength()) {   /* se non è finita parte scelta god arg = God */
                    initController.defineGodList(answer, model.getGodsList());
                }else{
                    /* finita parte God arg = StartingPlayerNickname */
                    initController.setStartingPlayer(answer);  /* set il StartingPlayer dal Nickname */
                }
            }else {
                /* scelta God */
                initController.chooseGod(answer);
            }
        }
    }

    private void gmUpdate(){
        UndecoratedWorker worker = model.getCurrentTurn().getChosenWorker();
        if(worker.getGodPower()){
            model.operation();
            worker.setGodPower(false);
        }else {
            turnController.endOperation();
            /*
            if (operation == 1){
                turnController.endMove();
            }else if(operation == 2){
                turnController.endTurn(buildController.getOperation());
            }
             */
        }

    }
/*
    private void gmUpdate(String message, String answer){
         if (message.equals(Messages.Artemis)) {  /* YES or NO */
        /*
            if (answer.equals("YES")) {  /* Yes -> move in più (con Artemis = false) */
    /*
                model.move();
            } else {
                turnController.endMove();  /* aggiornare Turn fine Move */
    /*
            }
        }else if (message.equals(Messages.Atlas)) {  /* se Atlas controllare build BLOCK or DOME */
    /*
            if (buildController.checkBlockDome(answer)) {  /* se il build va a buon fine */
                /* per stampa Board in Client */
    /*
                turnController.endTurn(buildController.getOperation());
            }
        }else if (message.equals(Messages.Demeter)) {  /* YES or NO */
    /*
            if (answer.equals("YES")) {  /* Yes -> move in più (con Artemis = false) */
    /*
                model.build();
            } else {  /* fine turno */
    /*
                turnController.endTurn(buildController.getOperation());  /* ultima build effettuata */
    /*
            }
        }else if (message.equals(Messages.Hephaestus)) {  /* Block aggiuntivo : YES or NO */
    /*
            Operation op = buildController.getOperation();  /* ultima build effettuata */
    /*
            if (answer.equals("YES")) {
                model.getCurrentTurn().getChosenWorker().buildBlock(null);
            }
            turnController.endTurn(op);
        }else if (message.equals(Messages.Prometheus)) {  /* Move or Build? */
    /*
            if (answer.equals("MOVE")) {  /* Il worker procede come un worker normale */
    /*
    turnController.startMove();
            } else if (answer.equals("BUILD")) {  /* isPrometheus rimane true */
                //views.get(model.getCurrentTurn().getCurrentPlayer()).showMessage("Builda!");
      /*
                model.build();
            }
        }
    }

       */
}

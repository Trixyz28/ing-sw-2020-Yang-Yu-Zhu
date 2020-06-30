package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.God.Conditions;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.WorkerView;


import java.util.ArrayList;
import java.util.Random;


public class Model extends Observable {

    //ID of the challenger
    private int challengerID;

    //ID of the starting player
    private int startingPlayerID;

    //ArrayList of the players
    private ArrayList<Player> matchPlayersList;

    //Map: 5x5 tiles
    private Board board;

    //Number of players in the match
    private int playersNumber;

    //GodList for the choice of the ispiring God for each player
    private GodList godsList;  //ho cambiato l'ArrayList in classe GodList

    //Current turn
    private Turn currentTurn;

    private Conditions conditions;

    private boolean workerChosen;

    public void setWorkerPending(boolean workerPending) {
        this.workerPending = workerPending;
    }

    private boolean workerPending = false;

    private boolean isGameOver = false;

    private ArrayList<UndecoratedWorker> totalWorkerList;


    //Constructor for Match class
    public void initialize(int playersNumber) {
        this.playersNumber = playersNumber;
        matchPlayersList = new ArrayList<>();  //inizializzare playerList con i playerName in parametri
        godsList = new GodList(playersNumber);
        workerChosen = false;
        totalWorkerList = new ArrayList<>();
        board = new Board();
        conditions = new Conditions();
    }


    //Create the players
    public void addPlayer(Player player) {
        matchPlayersList.add(player);
        player.setChallenger(false);
    }


    /* preparazione scelta godList dal challenger */
    public void challengerStart(){
        randomChooseChallenger();
        currentTurn = new Turn(matchPlayersList.get(challengerID));
        notify(new Obj(Tags.turn,currentTurn.getCurrentPlayer().getPlayerNickname()));
        sendMessage(Tags.generic,Messages.challenger);
        /* mandare al Challenger la lista completa dei God */
        showCompleteGodList();
    }


    //Choose a Challenger from playersList in a random way
    public void randomChooseChallenger() {
        Random r = new Random();
        challengerID = (r.nextInt(playersNumber));
        matchPlayersList.get(challengerID).setChallenger(true);
        broadcast(new Obj(Tags.generic,"The chosen challenger is: " + matchPlayersList.get(challengerID).getPlayerNickname()));
    }


    //print completeList
    public void showCompleteGodList(){
        notify(new Obj(Tags.completeList,godsList.getCompleteGodList()));
    }


    //far scegliere Gods dal challenger
    public boolean defineGodList(String god){
        godsList.selectGod(god);
        if(!godsList.addInGodList()){
            /* the input god can't be added in the currentGodList */
            sendMessage(Tags.godMsg,Messages.invalidChoice);
        } else {
            /* the chosen god is added in the currentGodList */
            notify(new Obj(Tags.defineGod,god.toUpperCase()));
        }
        if(godsList.checkLength()){
            /* end of defining currentGodList */
            broadcast(new Obj(Tags.generic,Messages.challengerChosen));
            return true;
        }

        return false;
    }


    //far scegliere God dai player
    public void nextChoiceGod(){

        int index = getNextPlayerIndex();
        currentTurn.setCurrentPlayer(matchPlayersList.get(index));
        notify(new Obj(Tags.turn,currentTurn.getCurrentPlayer().getPlayerNickname()));
        if(index == challengerID){
            /* dare direttamente la god rimanente al Challenger */
            String god = godsList.getCurrentGodList().get(0);

            Player challenger = matchPlayersList.get(challengerID);
            notify(new Obj(Tags.chooseGod,god,challenger.getPlayerNickname()));
            challenger.setGodCard(god);
            challenger.createWorker(god, getConditions(),getTotalWorkers());
            /* creare lista di tutti i workers */
            createTotalWorkerList();
            sendMessage(Tags.godMsg,Messages.chooseStartPlayer);
        }else {
            /* show currentList */
            showGodList();
            sendMessage(Tags.godMsg,Messages.godRequest);
        }
    }


    public void showGodList(){
        notify(new Obj(Tags.currentList,godsList.getCurrentGodList()));
    }


    /* Scelta God + creazione worker */
    public boolean chooseGod(String god){
        godsList.selectGod(god);
        if(godsList.checkGod()){
            /* the input god can be chosen */
            Player currentPlayer = currentTurn.getCurrentPlayer();
            String name = currentPlayer.getPlayerNickname();
            notify(new Obj(Tags.chooseGod,god,name));

            /* assegnare al Player il God scelto */
            currentPlayer.setGodCard(god);
            /* creare worker determinato God */
            currentPlayer.createWorker(god, getConditions(), getTotalWorkers());
            /* eliminare dalla currentGodList il god scelto */
            godsList.removeFromGodList(god);

            /* prossimo player */
            return true;
        }else{

            sendMessage(Tags.godMsg,Messages.tryAgain);
            return false;
        }
    }


    /* per scegliere il startingPlayer attraverso Nickname */
    public boolean setStartingPlayer(String startingPlayerNickname){

        Player startingPlayer;

        for (Player p : matchPlayersList) {
            if (p.getPlayerNickname().equals(startingPlayerNickname)) {
                /* if the input name is found -> set startPlayer */
                startingPlayer = p;
                //settare il startingPlayerID del model
                setStartingPlayerID(startingPlayer.getPlayerID());
                broadcast(new Obj(Tags.generic,"The Start player chosen is : " + startingPlayer.getPlayerNickname()));
                return true;
            }
        }
        /* se esce dal for -> Nickname non trovato riprovare a chiedere */
        sendMessage(Tags.generic,Messages.wrongArgument+ "\n" + Messages.tryAgain);
        return false;
    }


    //check turn dei messaggi ricevuti
    public boolean checkTurn(String playerNickname){
        if(playerNickname.equals(currentTurn.getCurrentPlayer().getPlayerNickname())) {
            return true;
        }else {
            Obj obj = new Obj(Tags.generic,Messages.wrongTurn);
            obj.setBroadcast(false);
            obj.setReceiver(new Player(playerNickname));
            notify(obj);
            return false;
        }
    }


    /* check risposta per divinità */
    public boolean checkAnswer(GameMessage gMessage){
        String answer = gMessage.getAnswer();

        if(gMessage.getMessage().equals(Messages.worker)){
            /* workerIndex */
            if(gMessage.getAnswer().equals("0") || gMessage.getAnswer().equals("1")){
                return true;
            }else {
                sendMessage(Tags.generic, Messages.invalidWorker);
                sendMessage(Tags.gMsg, Messages.worker);
                return false;
            }
        }

        if(gMessage.getMessage().equals(Messages.confirmWorker)){
            /* confirm chosen worker */
            if(gMessage.getAnswer().equals("YES")) {
                return true;
            }else if (gMessage.getAnswer().equals("NO")){
                setWorkerPending(false);
                setWorkerChosen(false);
                sendBoard();
                sendMessage(Tags.gMsg, Messages.worker);
                return false;
            }else {
                sendMessage(Tags.generic, Messages.tryAgain);
                sendMessage(Tags.gMsg, Messages.confirmWorker);
                return false;
            }
        }

        /* godPower */
        GodPowerMessage god = GodPowerMessage.valueOf(currentTurn.getCurrentPlayer().getGodCard());
        if(god.checkAnswer(answer) == 0){
            sendMessage(Tags.generic,Messages.tryAgain);
            sendMessage(Tags.gMsg,currentTurn.getCurrentPlayer().getGodCard());
            return false;
        }else {
            /* if check = true -> usePower(true) else usePower(false) */
            currentTurn.getChosenWorker().useGodPower(god.checkAnswer(answer) == 1);
            return true;
        }

    }



    public Board getBoard() {
        return board;
    }


    public void sendBoard() {
        notify(new Obj(createBoardView()));
    }


    public BoardView createBoardView() {
        Tile[][] mapToSend = board.getMap();
        WorkerView[] totalWorkerView = new WorkerView[totalWorkerList.size()];
        int chosenID = -1;

        for(UndecoratedWorker worker : totalWorkerList) {
            int index = totalWorkerList.indexOf(worker);
            totalWorkerView[index] = new WorkerView(worker);
            totalWorkerView[index].setWorkerID(index%2);

            if(workerChosen) {
                if(worker.equals(currentTurn.getChosenWorker())) {
                    chosenID = index;
                }
            }
        }

        if(chosenID != -1) {
            int op = currentTurn.getChosenWorker().getState();
            WorkerView chosen = totalWorkerView[chosenID];

            if(op==1 || workerPending) {
                op = 1;
                chosen.setMovableList(currentTurn.movableList(currentTurn.getChosenWorker()));
            }
            if(op>=2) {
                if(op==3) {
                    int index = chosenID%2==0 ? 1 : -1;
                    chosenID = chosenID+index;
                    chosen = totalWorkerView[chosenID];
                }
                chosen.setBuildableList(currentTurn.buildableList(currentTurn.getChosenWorker()));
            }

            chosen.setState(op);
        }

        return new BoardView(mapToSend,totalWorkerView,currentTurn.getCurrentPlayer(),chosenID);
    }



    //get the index of the nextPlayer
    public int getNextPlayerIndex(){
        int index = matchPlayersList.indexOf(currentTurn.getCurrentPlayer())+1;
        if(index == matchPlayersList.size()){
            return 0;
        }else{
            return index;
        }
    }


    public int getPlayersNumber() {
        return playersNumber;
    }

    public Conditions getConditions(){
        return conditions;
    }

    //get() of the arraylist made by Players
    public ArrayList<Player> getMatchPlayersList() {
        return matchPlayersList;
    }

    //get() of the challengerID
    public int getChallengerID() {
        return challengerID;
    }


    //get() starting playerID
    public int getStartingPlayerID() {
        return startingPlayerID;
    }
    //set of the starting playerID
    public void setStartingPlayerID(int id){
        startingPlayerID = id;
    }


    //get() of the GodList
    public GodList getGodsList() {
        return godsList;
    }


    public void startTurn() {
        /* set start player as current player and start first place */
        currentTurn.setCurrentPlayer(matchPlayersList.get(startingPlayerID));
        int indexWorker = 0;
        placeWorker(indexWorker);
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    //turn advancer nel turn controller/funzione che prosegue con la scelta del player successive, più  futura
    //implementazione oggetto chronobreak per salvare le partite


    public Tile commandToTile(int row,int column) {
        return board.getTile(row,column);
    }

    //Messages
    public void sendMessage(String tag,String arg) {
        if(tag.equals(Tags.gMsg)) {

            String message = null;
            try{
                GodPowerMessage god = Enum.valueOf(GodPowerMessage.class, arg);
                message = god.getMessage();
            } catch (IllegalArgumentException e){
                message = arg;
            } finally {
                unicastMsg(new Obj(new GameMessage(message)));
            }

        } else {
            unicastMsg(new Obj(tag,arg));
        }

    }


    public void unicastMsg(Obj obj) {
        obj.setBroadcast(false);
        obj.setReceiver(currentTurn.getCurrentPlayer());
        notify(obj);
    }

    public void broadcast(Obj obj){
        notify(obj);
    }


    public void operation(){  /* type 1 = move type 2 = build */
        int type = currentTurn.getState();
        unicastMsg(new Obj(new Operation(type, -1, -1)));
    }

    public void place(){  /* type 0 = place */
        unicastMsg(new Obj(new Operation(0, -1, -1)));
    }

    public void placeWorker(int indexWorker){
        sendBoard();
        sendMessage(Tags.boardMsg,Messages.place + indexWorker);
        place();
    }

    //metodi da implementare con il controller
    public boolean checkWin() {
        if (currentTurn.getChosenWorker().checkWin(currentTurn.getInitialTile())){
            setWorkerChosen(false);
            sendBoard();
            gameOver();
            return true;
        }
        return false;
    }

    public boolean checkLose(){
        if (currentTurn.checkLose()){
            /* lose */
            lose(currentTurn.getCurrentPlayer());
            return true;
        }else {
            return false;
        }
    }

    public void lose(Player player){
        if(matchPlayersList.size() > 2) {
            player.deleteWorker();
            /* precedente al currentPlayer (per nextTurn del turnController)*/
            int index = matchPlayersList.indexOf(player) - 1;
            if (index < 0) {
                index = matchPlayersList.size() - 1;
            }
            currentTurn.setCurrentPlayer(matchPlayersList.get(index));
            /* aggiornare totalWorkerList + conditions*/
            totalWorkerList.remove(player.chooseWorker(0));
            totalWorkerList.remove(player.chooseWorker(1));
            conditions.update(player.getPlayerID());
            matchPlayersList.remove(player);
            broadcast(new Obj(Tags.end,"lose",player.getPlayerNickname()));
        } else {
            currentTurn.nextTurn(matchPlayersList.get(getNextPlayerIndex()));
            gameOver();
        }
    }

    public boolean isWorkerChosen() {
        return workerChosen;
    }

    public void setWorkerChosen(boolean workerChosen) {
        this.workerChosen = workerChosen;
    }

    public void gameOver() {
        isGameOver = true;
        Obj obj = new Obj(Tags.end,"win",currentTurn.getCurrentPlayer().getPlayerNickname());
        obj.setReceiver(matchPlayersList.get(matchPlayersList.size()-1));
        broadcast(obj);
    }

    public boolean isGameOver() {
        return isGameOver;
    }


    public void createTotalWorkerList() {

        for (Player player : matchPlayersList) {
            totalWorkerList.addAll(player.getWorkerList());
        }
    }


    public ArrayList<UndecoratedWorker> getTotalWorkers() {
        return totalWorkerList;
    }


}

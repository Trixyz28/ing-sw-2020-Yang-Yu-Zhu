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

    private Conditions condition;

    private boolean workerChosen;

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
        condition = new Conditions();
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
        notify(new TurnMessage("god", currentTurn.getCurrentPlayer().getPlayerNickname()));
        sendMessage("Sei il Challenger");
        /* mandare al Challenger la lista completa dei God */
        showCompleteGodList();
    }


    //Choose a Challenger from playersList in a random way
    public void randomChooseChallenger() {
        Random r = new Random();
        challengerID = (r.nextInt(playersNumber));
        matchPlayersList.get(challengerID).setChallenger(true);
        broadcast("The chosen challenger is: " + matchPlayersList.get(challengerID).getPlayerNickname());
    }


    //print completeList
    public void showCompleteGodList(){
        notify(godsList.getCompleteGodList());
    }


    //far scegliere Gods dal challenger
    public boolean defineGodList(String god){
        godsList.selectGod(god);
        if(!godsList.addInGodList()){  /* se il God scelto non viene aggiunto nella currentList */
            //views.get(challenger).showMessage("Scelta invalida");
            sendMessage(Messages.invalidChoice);
        } else {
            notify(new GodChosenMessage("define",god.toUpperCase()));
        }
        if(godsList.checkLength()){
            //views.get(p).showMessage("Il Challenger ha finito di scegliere i God! La Lista dei God scelti è :");
            broadcast(Messages.challengerChosen);
            return true;
        }

        return false;
    }


    //far scegliere God dai player
    public void nextChoiceGod(){

        int index = getNextPlayerIndex();
        currentTurn.setCurrentPlayer(matchPlayersList.get(index));
        notify(new TurnMessage("god", currentTurn.getCurrentPlayer().getPlayerNickname()));
        if(index == challengerID){
            /* dare direttamente la god rimanente al Challenger */
            String god = godsList.getCurrentGodList().get(0);

            sendMessage("Il god rimasto è " + god + "!");
            Player challenger = matchPlayersList.get(challengerID);
            notify(new GodChosenMessage("choose",god,challenger.getPlayerNickname()));
            challenger.setGodCard(god);
            challenger.createWorker(god, getCondition(),getTotalWorkers());
            /* creare lista di tutti i workers */
            createTotalWorkerList();
            sendMessage(Messages.chooseStartPlayer);
        }else {
            /* show currentList */
            showGodList();
            sendMessage("Scegli la tua divinità");
        }
    }


    /* Scelta God + creazione worker */
    public boolean chooseGod(String god){
        godsList.selectGod(god);
        if(godsList.checkGod()){
            //far printare alla view la conferma della scelta
            Player currentPlayer = currentTurn.getCurrentPlayer();
            String name = currentPlayer.getPlayerNickname();
            notify(new GodChosenMessage("choose",god,name));

            /* assegnare al Player il God scelto */
            currentPlayer.setGodCard(god);
            /* creare worker determinato God */
            currentPlayer.createWorker(god, getCondition(), getTotalWorkers());
            /* eliminare dalla currentGodList il god scelto */
            godsList.removeFromGodList(god);
            /* prossimo player */
            return true;
        }else{
            //far printare alla view la richiesta di ripetere la scelta
            sendMessage(Messages.tryAgain);
            return false;
        }
    }


    /* per scegliere il startingPlayer attraverso Nickname */
    public boolean setStartingPlayer(String startingPlayerNickname){

        Player startingPlayer;
        for (Player p : matchPlayersList) {
            if (p.getPlayerNickname().equals(startingPlayerNickname)) {
                startingPlayer = p;
                //settare il startingPlayerID del model
                setStartingPlayerID(startingPlayer.getPlayerID());
                broadcast("Il primo player che fa la mossa è " + startingPlayer.getPlayerNickname());
                broadcast(Messages.boardStarting);
                return true;
            }
        }
        /* se esce dal for -> Nickname non trovato riprovare a chiedere */
        sendMessage(Messages.wrongArgument+ "\n" + Messages.tryAgain);
        return false;
    }


    //check turn dei messaggi ricevuti
    public boolean checkTurn(String playerNickname){
        if(playerNickname.equals(currentTurn.getCurrentPlayer().getPlayerNickname())) {
            return true;
        }else {
            notify(new GameMessage(new Player(playerNickname),Messages.wrongTurn,true));
            return false;
        }
    }


    /* check risposta per divinità */
    public boolean checkAnswer(GameMessage gMessage){
        String answer = gMessage.getAnswer();
        GodPowerMessage god = GodPowerMessage.valueOf(currentTurn.getCurrentPlayer().getGodCard());

        if(god.checkAnswer(answer) == 0){
            sendMessage(Messages.tryAgain);
            sendMessage(currentTurn.getCurrentPlayer().getGodCard());
            return false;
        }else {
            if (god.checkAnswer(answer) == 1){
                currentTurn.getChosenWorker().useGodPower(true);
            }else {
                currentTurn.getChosenWorker().useGodPower(false);
            }
            return true;
        }
    }



    public Board getBoard() {
        return board;
    }


    public void showBoard() {
        notify(createBoardView());
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

            if(op==1) {
                chosen.setMovableList(currentTurn.movableList(currentTurn.getChosenWorker()));
            }
            if(op==2) {
                chosen.setBuildableList(currentTurn.buildableList(currentTurn.getChosenWorker()));
            }
            if(op==3) {
                if(chosenID%2==0) {
                    chosenID++;
                } else {
                    chosenID--;
                }
                chosen.setBuildableList(currentTurn.buildableList(currentTurn.getCurrentPlayer().chooseWorker(chosenID)));
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

    public Conditions getCondition(){
        return condition;
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






    public void showGodList(){
        notify(godsList.getCurrentGodList());
    }

    public void startTurn() {
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
    public void sendMessage(String arg) {
        String message = null;
        boolean readOnly = false;
        try{
            GodPowerMessage god = Enum.valueOf(GodPowerMessage.class, arg);
            message = god.getMessage();
        }catch (IllegalArgumentException e){
            if(!arg.equals(Messages.Worker)){
                readOnly = true;
            }
            message = arg;
        }finally {
            notify(new GameMessage(currentTurn.getCurrentPlayer(), message, readOnly));
        }

    }


    public void broadcast(String message){
        notify(message);
    }


    public void operation(){  /* type 1 = move type 2 = build */
        int type = currentTurn.getState();
        notify(new Operation(currentTurn.getCurrentPlayer(), type, -1, -1));
    }

    public void place(){  /* type 0 = place */
        notify(new Operation(currentTurn.getCurrentPlayer(),0, -1, -1));
    }

    public void placeWorker(int indexWorker){
        showBoard();
        sendMessage("Posiziona il worker" + indexWorker);
        place();
        //notify(new Operation(currentTurn.getCurrentPlayer(),0, -1, -1));
    }

    //metodi da implementare con il controller
    public boolean checkWin() {
        if (currentTurn.getChosenWorker().checkWin(currentTurn.getInitialTile())){
            sendMessage("Hai vintoooooo!!!");
            gameOver();
            return true;
        }
        return false;
    }

    public boolean checkLose(){
        if (currentTurn.checkLose()){
            sendMessage(Messages.lose);
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
            /* aggioranare totalWorkerList + conditions*/
            totalWorkerList.remove(player.chooseWorker(0));
            totalWorkerList.remove(player.chooseWorker(1));
            condition.update(player.getPlayerID());
            matchPlayersList.remove(player);
            broadcast("The player " + player.getPlayerNickname() + " loses");
            losingPlayer(player);
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
        broadcast("The winner is " + currentTurn.getCurrentPlayer().getPlayerNickname() + "!");
        broadcast(Messages.gameOver);
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void losingPlayer(Player player) {
        notify(player);
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

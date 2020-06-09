package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.Conditions;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.view.cli.BoardView;
import it.polimi.ingsw.view.cli.WorkerView;


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

    /* preparazione scelta godList dal challenger */
    public void challengerStart(){
        //views.get(challenger).showMessage("Sei il Challenger!");
        currentTurn = new Turn(matchPlayersList.get(challengerID));
        sendMessage("Sei il Challenger");
        showCompleteGodList();  /* mandare al Challenger la lista completa dei God */
    }

    public boolean defineGodList(String god, GodList godList){  //eseguire solo al challenger
        //far scegliere Gods attraverso la view dal challenger )
        godList.selectGod(god);
        if(!godList.addInGodList()){  /* se il God scelto non viene aggiunto nella currentList */
            //views.get(challenger).showMessage("Scelta invalida");
            sendMessage("Scelta invalida");
        }
        if(godList.checkLength()){
            //views.get(p).showMessage("Il Challenger ha finito di scegliere i God! La Lista dei God scelti è :");
            broadcast("Il Challenger ha finito di scegliere i God! \nLa Lista dei God scelti è :");
            return true;
        }
        return false;
    }

    public void nextChoiceGod(){

        int index = getNextPlayerIndex();
        currentTurn.setCurrentPlayer(matchPlayersList.get(index));
        if(index == challengerID){  /* fine giro scelta God */
            /* dare direttamente la god rimanente al Challenger */
            String god = godsList.getCurrentGodList().get(0);  /* il god rimanente nella currentGodList viene assegnata al challenger */
            sendMessage("Il god rimasto è " + god + "!");  //far printare alla view il messaggio del god assegnato
            Player challenger = matchPlayersList.get(challengerID);
            challenger.godChoice(god);
            challenger.createWorker(god, getCondition(),getTotalWorkers());
            createTotalWorkerList();  /* creare lista di tutti i workers */
            sendMessage("Scegli il Player che inizia la partita!");
        }else {
            showGodList();
            sendMessage("Scegli la tua divinità");
        }
    }

    public void chooseGod(String god){
        godsList.selectGod(god);
        if(godsList.checkGod()){
            //far printare alla view la conferma della scelta
            Player currentPlayer = currentTurn.getCurrentPlayer();
            String name = currentPlayer.getPlayerNickname();
            broadcast("Il player " + name + " ha scelto " +
                    god + ".");
            currentPlayer.godChoice(god);  /* assegnare al Player il God scelto */
            currentPlayer.createWorker(god, getCondition(), getTotalWorkers());  /* creare worker determinato God */
            godsList.removeFromGodList(god);  /* eliminare dalla currentGodList il god scelto */
            nextChoiceGod();
        }else{
            //far printare alla view la richiesta di ripetere la scelta
            sendMessage(Messages.tryAgain);
        }
    }

    public boolean checkTurn(String playerNickname){
        if(playerNickname.equals(currentTurn.getCurrentPlayer().getPlayerNickname())) {
            return true;
        }else {
            notify(new GameMessage(new Player(playerNickname),Messages.wrongTurn,true));
            return false;
        }
    }

    public boolean checkAnswer(GameMessage gMessage){  /* check risposta per divinità */
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
        /*
        if(message.equals(GodPowerMessage.valueOf("ATLAS").getMessage())){
            if(answer.equals("BLOCK") || answer.equals("DOME")) {
                if (answer.equals("DOME")) {
                    currentTurn.getChosenWorker().useGodPower(true);
                }else {
                    currentTurn.getChosenWorker().useGodPower(false);
                }
                return true;
            }
        }else if(message.equals(GodPowerMessage.valueOf("PROMETHEUS").getMessage())){
            if (answer.equals("MOVE")  || answer.equals("BUILD")) {
                if (answer.equals("BUILD")){
                    currentTurn.getChosenWorker().useGodPower(true);
                }else {
                    currentTurn.getChosenWorker().useGodPower(false);
                }
                return true;
            }
        } else if (answer.equals("YES") || answer.equals("NO")){
            if(answer.equals("YES")){
                currentTurn.getChosenWorker().useGodPower(true);
            }else {
                currentTurn.getChosenWorker().useGodPower(false);
            }
            return true;
        }
         */


    }

    public boolean setStartingPlayer(String startingPlayerNickname){  /* per sceglire il startingPlayer attraverso Nickname */

        Player startingPlayer;
        for (Player p : matchPlayersList) {
            if (p.getPlayerNickname().equals(startingPlayerNickname)) {
                startingPlayer = p;
                startingPlayerID = startingPlayer.getPlayerID();  //settare il startingPlayerID del model
                broadcast("Il primo player che fa la mossa è " + startingPlayer.getPlayerNickname());
                return true;
            }
        }
        /* se esce dal for -> Nickname non trovato riprovare a chiedere */
        sendMessage(Messages.wrongArgument+ "\n" + Messages.tryAgain);
        return false;

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

    //Choose a Challenger from playersList in a random way
    public void randomChooseChallenger() {
        Random r = new Random();
        challengerID = (r.nextInt(playersNumber));
        matchPlayersList.get(challengerID).setChallenger(true);
        broadcast("The chosen challenger is: " + matchPlayersList.get(challengerID).getPlayerNickname());
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

    //print completeList
    public void showCompleteGodList(){
        notify(godsList.getCompleteGodList());
    }


   //Create the players
    public void addPlayer(Player player) {
        matchPlayersList.add(player);
        player.setChallenger(false);
    }

    public void showGodList(){
        notify(godsList.getCurrentGodList());
    }

    public void startTurn() {
        currentTurn.setCurrentPlayer(matchPlayersList.get(startingPlayerID));
        int indexWorker = 0;
        placeWoker(indexWorker);

    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    //turn advancer nel turn controller/funzione che prosegue con la scelta del player successive, più  futura
    //implementazione oggetto chronobreak per salvare le partite


    public Tile commandToTile(int row,int column) {
        return board.getTile(row,column);
    }


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
        /*
        if (arg.equals("ARTEMIS")) {
            message = Messages.Artemis;
        } else if (arg.equals("ATLAS")) {
            message = Messages.Atlas;
        } else if (arg.equals("DEMETER")) {
            message = Messages.Demeter;
        } else if (arg.equals("HEPHAESTUS")) {
            message = Messages.Hephaestus;
        } else if (arg.equals("PROMETHEUS")) {
            message = Messages.Prometheus;
        } else if (arg.equals("HESTIA")) {
            message = Messages.Hestia;
        }else if(arg.equals("POSEIDON")) {
            message = Messages.Poseidon;
        }else if(arg.equals("TRITON")){
            message = Messages.Triton;
        }else{
            if(!arg.equals(Messages.Worker)){
                readOnly = true;
            }
            message = arg;
        }
         */
    }

    public void broadcast(String message){
        notify(message);
    }


    public void operation(){  /* type 0 = place  type 1 = move type 2 = build */
        int type = currentTurn.getState();
        notify(new Operation(currentTurn.getCurrentPlayer(), type, -1, -1));
    }

    public void place(){  /* type 0 = place */
        notify(new Operation(currentTurn.getCurrentPlayer(),0, -1, -1));
    }

    public void move(){  /* type 1 = move */
        notify(new Operation(currentTurn.getCurrentPlayer(),1, -1, -1));
    }

    public void build(){  /* type 2 = build */
        notify(new Operation(currentTurn.getCurrentPlayer(),2, -1,-1));
    }

    public void placeWoker(int indexWorker){
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
        }
        return false;
    }

    public boolean checkLose(){
        if (currentTurn.checkLose()){
            sendMessage(Messages.lose);
            lose(currentTurn.getCurrentPlayer());  /* lose */
            return true;
        }else {
            return false;
        }
    }
/*
    public boolean checkLoseMove() {  /* se tutti i worker non hanno più tile da poter andare -> perde
        for (int i = 0; i < 2 ; i++) {
            UndecoratedWorker worker = currentTurn.getCurrentPlayer().chooseWorker(i);
            if (worker.canMove().size() != 0) {
                return false;
            }
        }

        sendMessage("Spiacenti! Non sei più in grado di fare mosse, hai perso!!!!");
        lose(currentTurn.getCurrentPlayer());  /* lose
        return true;
    }

    public boolean checkLoseBuild(UndecoratedWorker worker){  /* se il worker non può né build Block né build Dome
        for(Tile t : worker.getPosition().getAdjacentTiles()){
            if(worker.canBuildBlock(t) || worker.canBuildDome(t)){
                return false;
            }
        }
        sendMessage("Spiacenti! Non sei più in grado di buildare, hai perso!!!!");
        lose(currentTurn.getCurrentPlayer());  /* lose
        return true;
    }


 */
    public void lose(Player player){
        if(matchPlayersList.size() > 2) {
            player.deleteWorker();
            int index = matchPlayersList.indexOf(player) - 1;  /* precedente al currentPlayer (per nextTurn del turnController)*/
            if (index < 0) {
                index = matchPlayersList.size() - 1;
            }
            currentTurn.setCurrentPlayer(matchPlayersList.get(index));
            totalWorkerList.remove(player.chooseWorker(0));  /* aggioranare totalWorkerList */
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

        for(int i=0;i<matchPlayersList.size();i++) {
            totalWorkerList.addAll(matchPlayersList.get(i).getWorkerList());
        }

    }

    public ArrayList<UndecoratedWorker> getTotalWorkers() {
        return totalWorkerList;
    }


}

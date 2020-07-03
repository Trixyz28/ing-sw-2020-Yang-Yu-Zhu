package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.God.Conditions;
import it.polimi.ingsw.model.God.UndecoratedWorker;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.WorkerView;

import java.util.ArrayList;
import java.util.Random;
/**
 * Class that represents the match as the Model in the MVC pattern.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */

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

    //GodList for the choice of the inspiring God for each player
    private GodList godsList;

    //Current turn
    private Turn currentTurn;

    //If there's any God with particular move/build/win conditions.
    private Conditions conditions;

    private boolean workerChosen;

    /**
     * Checks if there's any worker pending in the choice.
     * @param workerPending A boolean: <code>true</code> if there's a worker pending, otherwise <code>false</code>.
     */
    public void setWorkerPending(boolean workerPending) {
        this.workerPending = workerPending;
    }

    private boolean workerPending = false;

    private boolean isGameOver = false;

    private ArrayList<UndecoratedWorker> totalWorkerList;


    //Constructor for Match class

    /**
     * Initializes the match with all the structures needed.
     * @param playersNumber Variable that indicates the number of players of the lobby.
     */
    public void initialize(int playersNumber) {
        this.playersNumber = playersNumber;
        matchPlayersList = new ArrayList<>();  //initialize playerList with playerNames in parameter
        godsList = new GodList(playersNumber);
        workerChosen = false;
        totalWorkerList = new ArrayList<>();
        board = new Board();
        conditions = new Conditions();
    }


    //Create the players

    /**
     * Adds the player to the game.
     * @param player Variable that indicates which player is being added to the game.
     */
    public void addPlayer(Player player) {
        matchPlayersList.add(player);
        player.setChallenger(false);
    }


    /* preparation for the choice of the Challenger and subsequent choice of God Cards */

    /**
     * Chooses randomly the challenger of the game then asks him to choose the GodCards for the game.
     */
    public void challengerStart(){
        randomChooseChallenger();
        currentTurn = new Turn(matchPlayersList.get(challengerID));
        notify(new Obj(Tags.TURN,currentTurn.getCurrentPlayer().getPlayerNickname()));
        sendMessage(Tags.GENERIC,Messages.challenger);
        /* send the complete GodList to the challenger */
        showCompleteGodList();
    }


    //Choose a Challenger from playersList in a random way
    /**
     * Chooses a random challenger between the available players at the start of the game.
     */
    public void randomChooseChallenger() {
        Random r = new Random();
        challengerID = (r.nextInt(playersNumber));
        matchPlayersList.get(challengerID).setChallenger(true);
        broadcast(new Obj(Tags.GENERIC,"The chosen challenger is: " + matchPlayersList.get(challengerID).getPlayerNickname()));
    }


    //print completeList

    /**
     * Prints out the complete God List to the challenger for the choice of God Cards.
     */
    public void showCompleteGodList(){
        notify(new Obj(Tags.COMPLETE_LIST,godsList.getCompleteGodList()));
    }


    //let the Challenger choose the gods

    /**
     * Handles the choice of a God Card by the Challenger and adds it the the current God List.
     * @param god Variable that represents which particular God was chosen by the Challenger.
     * @return A boolean: <code>true</code> if the choice was correct and the God was added to the current God List,
     * otherwise <code>false</code>.
     */
    public boolean defineGodList(String god){
        godsList.selectGod(god);
        if(!godsList.addInGodList()){
            /* the input god can't be added in the currentGodList */
            sendMessage(Tags.GOD_MSG,Messages.invalidChoice);
        } else {
            /* the chosen god is added in the currentGodList */
            notify(new Obj(Tags.DEFINE_GOD,god.toUpperCase()));
        }
        if(godsList.checkLength()){
            /* end of defining currentGodList */
            broadcast(new Obj(Tags.GENERIC,Messages.challengerChosen));
            return true;
        }

        return false;
    }


    //make the next player choose the GodCard

    /**
     * Handles the choice of the God Cards throughout all the players after the Challenger chose the current GodList.
     */
    public void nextChoiceGod(){

        int index = getNextPlayerIndex();
        currentTurn.setCurrentPlayer(matchPlayersList.get(index));
        notify(new Obj(Tags.TURN,currentTurn.getCurrentPlayer().getPlayerNickname()));
        if(index == challengerID){
            /* give the remaining god card to the challenger. */
            String god = godsList.getCurrentGodList().get(0);

            Player challenger = matchPlayersList.get(challengerID);
            notify(new Obj(Tags.CHOOSE_GOD,god,challenger.getPlayerNickname()));
            challenger.setGodCard(god);
            challenger.createWorker(god, getConditions(),getTotalWorkers());
            /*create the list of all the workers */
            createTotalWorkerList();
            sendMessage(Tags.GOD_MSG,Messages.chooseStartPlayer);
        }else {
            /* show currentList */
            showGodList();
            sendMessage(Tags.GOD_MSG,Messages.godRequest);
        }
    }

    /**
     * Shows the current GodList to the player at issue.
     */
    public void showGodList(){
        notify(new Obj(Tags.CURRENT_LIST,godsList.getCurrentGodList()));
    }


    /* God Card choice + worker creation */

    /**
     * Handles the selection of the God and sets up the player and his workers with the chosen God Powers.
     * @param god Variable which is a string that indicates which God was chosen by the player.
     * @return A boolean: <code>true</code> if the God chosen was in the current GodList and assigned to the player,
     * otherwise <code>false</code>.
     */
    public boolean chooseGod(String god){
        godsList.selectGod(god);
        if(godsList.checkGod()){
            /* the input god can be chosen */
            Player currentPlayer = currentTurn.getCurrentPlayer();
            String name = currentPlayer.getPlayerNickname();
            notify(new Obj(Tags.CHOOSE_GOD,god,name));

            /* assign the chosen god card to the player */
            currentPlayer.setGodCard(god);
            /* creation of the worker with the God Card powers */
            currentPlayer.createWorker(god, getConditions(), getTotalWorkers());
            /* deletes from the current GodList the chosen God Card */
            godsList.removeFromGodList(god);

            /* next player */
            return true;
        }else{

            sendMessage(Tags.GOD_MSG,Messages.tryAgain);
            return false;
        }
    }


    /* if want to select the starting player with the nickname */

    /**
     * Selects the starting player of the game.
     * @param startingPlayerNickname A variable which is a string that represents the nickname of the chosen player.
     * @return A boolean: <code>true</code> if the nickname was validated and the starting player was set,
     * otherwise <code>false</code>.
     */
    public boolean setStartingPlayer(String startingPlayerNickname){

        Player startingPlayer;

        for (Player p : matchPlayersList) {
            if (p.getPlayerNickname().equals(startingPlayerNickname)) {
                /* if the input name is found -> set startPlayer */
                startingPlayer = p;
                //sets the starting playerID in the model
                setStartingPlayerID(startingPlayer.getPlayerID());
                broadcast(new Obj(Tags.GENERIC,"The Start player chosen is : " + startingPlayer.getPlayerNickname()));
                return true;
            }
        }
        /* if goes out of the loop -> nickname not found and asks again  */
        sendMessage(Tags.GENERIC,Messages.wrongArgument+ "\n" + Messages.tryAgain);
        return false;
    }


    //check turn of the received messages

    /**
     * Checks if the player who sent the messages is the owner of the current turn,
     * as only him is allowed to do actions in his turn.
     * @param playerNickname A variable which is a string that represents the nickname of the player at issue,
     * @return A boolean: <code>true</code> if the player is the owner of the current turn, otherwise <code>false</code>.
     */
    public boolean checkTurn(String playerNickname){
        if(playerNickname.equals(currentTurn.getCurrentPlayer().getPlayerNickname())) {
            return true;
        }else {
            Obj obj = new Obj(Tags.GENERIC,Messages.wrongTurn);
            obj.setBroadcast(false);
            obj.setReceiver(new Player(playerNickname));
            notify(obj);
            return false;
        }
    }


    /* check answer of which worker is to be used or if the worker is supposed to use his divine powers bestowed by the god*/

    /**
     * Handles the choice between two of the workers and the usage of the GodPower.
     * @param gMessage Variable that indicates which choice is to be made : worker selection or GodPower usage.
     * @return A boolean: <code>true</code> if:
     * <p>
     * -the worker is chosen.
     * <p>
     * -the chosen worker is confirmed to be the one chosen.
     * <p>
     * -the GodPower is confirmed to be used.
     * <p>
     * Otherwise <code>false</code>.
     */
    public boolean checkAnswer(GameMessage gMessage){
        String answer = gMessage.getAnswer();

        if(gMessage.getMessage().equals(Messages.worker)){
            /* workerIndex */
            if(gMessage.getAnswer().equals("0") || gMessage.getAnswer().equals("1")){
                return true;
            }else {
                sendMessage(Tags.GENERIC, Messages.invalidWorker);
                sendMessage(Tags.G_MSG, Messages.worker);
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
                sendMessage(Tags.G_MSG, Messages.worker);
                return false;
            }else {
                sendMessage(Tags.GENERIC, Messages.tryAgain);
                sendMessage(Tags.G_MSG, Messages.confirmWorker);
                return false;
            }
        }

        /* godPower */
        GodPowerMessage god = GodPowerMessage.valueOf(currentTurn.getCurrentPlayer().getGodCard());
        if(god.checkAnswer(answer) == 0){
            sendMessage(Tags.GENERIC,Messages.tryAgain);
            sendMessage(Tags.G_MSG,currentTurn.getCurrentPlayer().getGodCard());
            return false;
        }else {
            /* if check = true -> usePower(true) else usePower(false) */
            currentTurn.getChosenWorker().useGodPower(god.checkAnswer(answer) == 1);
            return true;
        }

    }


    /**
     * Gets the board associated with the Model.
     * @return The <code>Board</code> object of the current match.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Send an update of the board to the clients.
     */
    public void sendBoard() {
        notify(new Obj(createBoardView()));
    }

    /**
     * Handles the BoardView based on the updates of the changes to the Model.
     * <p>
     * Use of the state of the worker to determine which type of operation is being issued.
     * @return The updated BoardView after every action.
     */
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

    /**
     * Finds the index of the player which turn is next.
     * @return An integer that represents the player's index, otherwise 0 at the end of every cycle of turns equals to the number of players
     * in the game.
     */
    public int getNextPlayerIndex(){
        int index = matchPlayersList.indexOf(currentTurn.getCurrentPlayer())+1;
        if(index == matchPlayersList.size()){
            return 0;
        }else{
            return index;
        }
    }

    /**
     * Gets the players number of the game.
     * @return An integer which represents the number of the players.
     */
    public int getPlayersNumber() {
        return playersNumber;
    }


    /**
     * Gets the conditions imposed by some particular Gods of the current game.
     * @return An object <code>Conditions</code> that encapsulates the some extra rules of the game.
     */
    public Conditions getConditions(){
        return conditions;
    }

    //get() of the arraylist made by Players
    /**
     * Gets a List of the players of the game.
     * @return An arrayList of <code>Players</code> objects which represents the Players currently in the game.
     */
    public ArrayList<Player> getMatchPlayersList() {
        return matchPlayersList;
    }

    //get() of the challengerID

    /**
     * Gets the ID of the challenger.
     * @return An integer which represents the ID of the challenger player.
     */
    public int getChallengerID() {
        return challengerID;
    }


    //get() starting playerID

    /**
     * Gets the ID of the starting player of the game.
     * @return An integer which represents the ID of the starting player of the game.
     */
    public int getStartingPlayerID() {
        return startingPlayerID;
    }
    //set of the starting playerID

    /**
     * Sets the ID of the starting player of the game.
     * @param id Variable that represents the ID of the starting player of the current game.
     */
    public void setStartingPlayerID(int id){
        startingPlayerID = id;
    }


    //get() of the GodList

    /**
     * Gets the GodList of the current game.
     * @return An object <code>GodList</code> that encapsulates a list with all the current Gods in the game.
     */
    public GodList getGodsList() {
        return godsList;
    }

    /**
     * Starts the defined starting player as the current player and start the flow of the turns.
     */
    public void startTurn() {
        /* set start player as current player and start first place */
        currentTurn.setCurrentPlayer(matchPlayersList.get(startingPlayerID));
        int indexWorker = 0;
        placeWorker(indexWorker);
    }

    /**
     * Gets the current turn of the game.
     * @return A <code>Turn</code> object that encapsulates information about the current turn of the game.
     */
    public Turn getCurrentTurn() {
        return currentTurn;
    }


    /**
     * Associates a command to a specific tile of the current board.
     * @param row Row value of the chosen <code>Tile</code>.
     * @param column Column value of the chosen <code>Tile</code>.
     * @return A <code>Tile</code> object that is the one selected from the command.
     */
    public Tile commandToTile(int row,int column) {
        return board.getTile(row,column);
    }

    //Messages

    /**
     * Sends a message to the client at issue.
     * @param tag Variable that gives more information about the type and use of the message sent.
     * @param arg Variable that represents the message to be sent.
     */
    public void sendMessage(String tag,String arg) {
        if(tag.equals(Tags.G_MSG)) {

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

    /**
     * Sends an unicast Message to the client of the current player.
     * @param obj Variable that represents the message that needs to be casted.
     */
    public void unicastMsg(Obj obj) {
        obj.setBroadcast(false);
        obj.setReceiver(currentTurn.getCurrentPlayer());
        notify(obj);
    }

    /**
     * Sends a broadcast to all the players currently in the game.
     * @param obj Variable that represents the message that needs to be broadcasted.
     */
    public void broadcast(Obj obj){
        notify(obj);
    }

    /**
     * Gets the current state of the turn and casts it to the current player.
     */
    public void operation(){  /* type 1 = move type 2 = build */
        int type = currentTurn.getState();
        unicastMsg(new Obj(new Operation(type, -1, -1)));
    }

    /**
     * Place a worker and casts it to the current player.
     */
    public void place(){  /* type 0 = place */
        unicastMsg(new Obj(new Operation(0, -1, -1)));
    }

    /**
     * Asks the user to choose the placement of the board of the specified worker.
     * @param indexWorker Variable that indicates the selected worker that needs to be placed.
     */
    public void placeWorker(int indexWorker){
        sendBoard();
        sendMessage(Tags.BOARD_MSG,Messages.place + indexWorker);
        place();
    }

    /**
     * Checks if the worker satisfies any winning condition.
     * @return A boolean: <code>true</code> if any winning condition is met, otherwise <code>false</code>.
     */
    public boolean checkWin() {
        if (currentTurn.getChosenWorker().checkWin(currentTurn.getInitialTile())){
            setWorkerChosen(false);
            sendBoard();
            gameOver();
            return true;
        }
        return false;
    }

    /**
     * Checks if the worker satisfies any losing condition.
     * @return A boolean: <code>true</code> if any losing condition is met, otherwise <code>false</code>.
     */
    public boolean checkLose(){
        if (currentTurn.checkLose()){
            /* lose */
            lose(currentTurn.getCurrentPlayer());
            return true;
        }else {
            return false;
        }
    }

    /**
     * Handles the loss of a player.
     * @param player Variable that indicates the player that lost.
     */
    public void lose(Player player){
        if(matchPlayersList.size() > 2) {
            player.deleteWorker();
            /* before the currentPlayer (in nextTurn of turnController)*/
            int index = matchPlayersList.indexOf(player) - 1;
            if (index < 0) {
                index = matchPlayersList.size() - 1;
            }
            currentTurn.setCurrentPlayer(matchPlayersList.get(index));
            /*updates worker list and eventual conditions*/
            totalWorkerList.remove(player.chooseWorker(0));
            totalWorkerList.remove(player.chooseWorker(1));
            conditions.update(player.getPlayerID());
            matchPlayersList.remove(player);
            broadcast(new Obj(Tags.END,"lose",player.getPlayerNickname()));
        } else {
            currentTurn.nextTurn(matchPlayersList.get(getNextPlayerIndex()));
            gameOver();
        }
    }

    /**
     * Checks if the worker is chosen.
     * @return A boolean: <code>true</code> if the worker is chosen, otherwise <code>false</code>.
     */
    public boolean isWorkerChosen() {
        return workerChosen;
    }

    /**
     * Sets the worker at issue as chosen.
     * @param workerChosen Variable that represents the worker chosen by the player.
     */
    public void setWorkerChosen(boolean workerChosen) {
        this.workerChosen = workerChosen;
    }

    /**
     * Broadcasts a message that the game ended.
     */
    public void gameOver() {
        isGameOver = true;
        Obj obj = new Obj(Tags.END,"win",currentTurn.getCurrentPlayer().getPlayerNickname());
        obj.setReceiver(matchPlayersList.get(matchPlayersList.size()-1));
        broadcast(obj);
    }

    /**
     * Checks if the game is over.
     * @return A boolean: <code>true</code> if the game is over, otherwise <code>false</code>.
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Creats the total list of workers in the game for each player.
     */
    public void createTotalWorkerList() {

        for (Player player : matchPlayersList) {
            totalWorkerList.addAll(player.getWorkerList());
        }
    }

    /**
     * Gets the total list of workers in the game.
     * @return An arrayList of workers.
     */
    public ArrayList<UndecoratedWorker> getTotalWorkers() {
        return totalWorkerList;
    }


}

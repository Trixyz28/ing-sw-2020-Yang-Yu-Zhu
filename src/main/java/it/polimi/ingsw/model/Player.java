package it.polimi.ingsw.model;

import java.util.List;

public class Player {


    //ID assigned to each player in order by their playing turn
    private String playerID;
    public String getPlayerID() {
        return playerID;
    }
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }


    private String playerNickname;
    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }



    private boolean isChallenger;
    public boolean isChallenger() {
        return isChallenger;
    }

    private String startPlayerID;



    private String godCard;
    public String getGodCard() {
        return godCard;
    }



    //Select the full God list if isChallenger == true
    public void defineGodList() {

    }

    //Choose a God from the list
    public String godChoice(String god) {
        godCard = god;
        return godCard;
    }

    //Workers owned by this player
    private List<Worker> workerList;



    //Choose a start player if isChallenger == true
    public void chooseStartPlayer(String id) {

    }

    //Create 2 specific worker classes as indicated in godCard
    public List<Worker> createWorker(String godCard) {

        return workerList;
    }

    //Select the worker to move/build
    public void chooseWorker(int index) {


    }

    //Lost in 3-players game, delete workers
    public void clearWorker() {

    }


    public void losingCondition() {

    }





}

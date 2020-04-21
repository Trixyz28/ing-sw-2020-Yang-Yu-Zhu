package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.Apollo;

import java.util.List;

public class Player {

    // da sistemare implementazione dei workers: prima c'era una classe astratta worker estesa dai vari gods(worker)
    //introduzione di interfaccia workerMove, workerBuild


    //ID assigned to each player in order by their playing turn
    private int playerID;
    public int getPlayerID() {
        return playerID;
    }
    public void setPlayerID(int playerID) {
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



    //Choose a start player if isChallenger == true ( a chiamata dal controller, da sistemare
    public void chooseStartPlayer(int id) {

    }

    //Create 2 specific worker classes as indicated in godCard
    public List<Worker> createWorker(String godCard) {
        if(godCard.equals("APOLLO")) {
            workerList.add();

        }
        if(godCard.equals("ARTEMIS")) {

        }

        if(godCard.equals("ATHENA")) {

        }

        if(godCard.equals("ATLAS")) {

        }

        if(godCard.equals("DEMETER")) {

        }

        if(godCard.equals("HEPHAESTUS")) {

        }

        if(godCard.equals("MINOTAUR")) {

        }

        if(godCard.equals("PAN")) {

        }

        if(godCard.equals("PROMETHEUS")) {

        }




        return workerList;
    }

    //Select the worker to move/build
    public Worker chooseWorker(int index) {

        return workerList.get(index);
    }

    //Lost in 3-players game, delete workers
    public void clearWorker() {

    }







}

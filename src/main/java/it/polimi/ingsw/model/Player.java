package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player {

    // da sistemare implementazione dei workers: prima c'era una classe astratta worker estesa dai vari gods(worker)
    //introduzione di interfaccia workerMove, workerBuild


    //ID assigned to each player in order by their join succession
    private int playerID;
    private String playerNickname;
    private boolean challenger;
    private String godCard;

    //Workers owned by this player
    private List<UndecoratedWorker> workerList;

    public Player(String playerName) {
        this.playerNickname = playerName;
    }


    public String getPlayerNickname() {
        return playerNickname;
    }


    //PlayerID getter&setter
    public int getPlayerID() {
        return playerID;
    }
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }


    //Challenger getter&setter
    public boolean isChallenger() {
        return challenger;
    }
    public void setChallenger(boolean challenger){
        this.challenger = challenger;
    }


    //Choose a God from the list
    public void godChoice(String god) {
        godCard = god;
    }
    public String getGodCard() {
        return godCard;
    }




    //Create 2 specific worker classes as indicated in godCard ( da sistemare)
    public List<UndecoratedWorker> createWorker(String godCard, Conditions condition, List<UndecoratedWorker> totalWorkerList) {

        workerList = new ArrayList<>();
        UndecoratedWorker w1 = new NoGod(condition);
        UndecoratedWorker w2 = new NoGod(condition);

        switch (godCard) {
            case "APOLLO":
                workerList.add(new Apollo(w1, totalWorkerList));
                workerList.add(new Apollo(w2, totalWorkerList));
                break;

            case "ARTEMIS":
                workerList.add(new Artemis(w1));
                workerList.add(new Artemis(w2));
                break;

            case "ATHENA":
                workerList.add(new Athena(w1));
                workerList.add(new Athena(w2));
                break;

            case "ATLAS":
                workerList.add(new Atlas(w1));
                workerList.add(new Atlas(w2));
                break;

            case "DEMETER":
                workerList.add(new Demeter(w1));
                workerList.add(new Demeter(w2));
                break;

            case "HEPHAESTUS":
                workerList.add(new Hephaestus(w1));
                workerList.add(new Hephaestus(w2));
                break;

            case "MINOTAUR":
                workerList.add(new Minotaur(w1, totalWorkerList));
                workerList.add(new Minotaur(w2, totalWorkerList));
                break;

            case "PAN":
                workerList.add(new Pan(w1));
                workerList.add(new Pan(w2));
                break;

            case "PROMETHEUS":
                workerList.add(new Prometheus(w1));
                workerList.add(new Prometheus(w2));
                break;
        }

        return workerList;
    }

    public List<UndecoratedWorker> getWorkerList() {
        return workerList;
    }


    //Select the worker to move/build
    public UndecoratedWorker chooseWorker(int index) {
        return workerList.get(index);
    }


    //Lost in 3-players game, delete workers
    public void deleteWorker() {
        for (UndecoratedWorker w : workerList){
            w.getPosition().setOccupiedByWorker(false);
        }
    }

    /*
    public Tile getWorkerPosition(){
        // da sistemare
    }
    */

}

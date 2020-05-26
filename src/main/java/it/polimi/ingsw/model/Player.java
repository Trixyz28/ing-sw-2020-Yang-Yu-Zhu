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
    public List<UndecoratedWorker> createWorker(String godCard, List<UndecoratedWorker> totalWorkerList) {

        workerList = new ArrayList<>();

        switch (godCard) {
            case "APOLLO":
                workerList.add(new Apollo(new NoGod(), totalWorkerList));
                workerList.add(new Apollo(new NoGod(), totalWorkerList));
                break;

            case "ARTEMIS":
                workerList.add(new Artemis(new NoGod()));
                workerList.add(new Artemis(new NoGod()));
                break;

            case "ATHENA":
                workerList.add(new Athena(new NoGod()));
                workerList.add(new Athena(new NoGod()));
                break;

            case "ATLAS":
                workerList.add(new Atlas(new NoGod()));
                workerList.add(new Atlas(new NoGod()));
                break;

            case "DEMETER":
                workerList.add(new Demeter(new NoGod()));
                workerList.add(new Demeter(new NoGod()));
                break;

            case "HEPHAESTUS":
                workerList.add(new Hephaestus(new NoGod()));
                workerList.add(new Hephaestus(new NoGod()));
                break;

            case "MINOTAUR":
                workerList.add(new Minotaur(new NoGod(), totalWorkerList));
                workerList.add(new Minotaur(new NoGod(), totalWorkerList));
                break;

            case "PAN":
                workerList.add(new Pan(new NoGod()));
                workerList.add(new Pan(new NoGod()));
                break;

            case "PROMETHEUS":
                workerList.add(new Prometheus(new NoGod()));
                workerList.add(new Prometheus(new NoGod()));
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

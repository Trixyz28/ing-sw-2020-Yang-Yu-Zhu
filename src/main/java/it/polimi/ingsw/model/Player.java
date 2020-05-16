package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.*;

import java.util.ArrayList;
import java.util.List;

public class Player {

    // da sistemare implementazione dei workers: prima c'era una classe astratta worker estesa dai vari gods(worker)
    //introduzione di interfaccia workerMove, workerBuild


    //ID assigned to each player in order by their join succession
    private int playerID;
    private String playerNickname;
    private boolean challenger;  /* non è più necessario (può essere usato per i test) */
    private String godCard;

    //Workers owned by this player
    private List<UndecoratedWorker> workerList;


    public int getPlayerID() {
        return playerID;
    }
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }


    public String getPlayerNickname() {
        return playerNickname;
    }
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }


    public void setChallenger(boolean challenger){
        this.challenger = challenger;
    }
    public boolean isChallenger() {
        return challenger;
    }

    public String getGodCard() {
        return godCard;
    }


    /* Select the full God list if isChallenger == true da mettere nella view e controller
    public void defineGodList() {
        if(isChallenger == true){
            while(!godList.checkLength()) {

            }
        }
    }
    */

    //Choose a God from the list
    public void godChoice(String god) {
        godCard = god;
    }





    //Choose a start player if isChallenger == true -> non è necessario metterlo nel Player
    /*
    public void chooseStartPlayer(int id) {    }
    */

    //Create 2 specific worker classes as indicated in godCard ( da sistemare)
    public List<UndecoratedWorker> createWorker(String godCard) {

        workerList = new ArrayList<>();

        if(godCard.equals("APOLLO")) {
            workerList.add(new Apollo(new NoGod()));
            workerList.add(new Apollo(new NoGod()));
        }

        if(godCard.equals("ARTEMIS")) {
            workerList.add(new Artemis(new NoGod()));
            workerList.add(new Artemis(new NoGod()));
        }

        if(godCard.equals("ATHENA")) {
            workerList.add(new Athena(new NoGod()));
            workerList.add(new Athena(new NoGod()));
        }

        if(godCard.equals("ATLAS")) {
            workerList.add(new Atlas(new NoGod()));
            workerList.add(new Atlas(new NoGod()));
        }

        if(godCard.equals("DEMETER")) {
            workerList.add(new Demeter(new NoGod()));
            workerList.add(new Demeter(new NoGod()));
        }

        if(godCard.equals("HEPHAESTUS")) {
            workerList.add(new Hephaestus(new NoGod()));
            workerList.add(new Hephaestus(new NoGod()));
        }

        if(godCard.equals("MINOTAUR")) {
            workerList.add(new Minotaur(new NoGod()));
            workerList.add(new Minotaur(new NoGod()));
        }

        if(godCard.equals("PAN")) {
            workerList.add(new Pan(new NoGod()));
            workerList.add(new Pan(new NoGod()));
        }

        if(godCard.equals("PROMETHEUS")) {
            workerList.add(new Prometheus(new NoGod()));
            workerList.add(new Prometheus(new NoGod()));
        }

        return workerList;
    }

    //Select the worker to move/build
    public UndecoratedWorker chooseWorker(int index) {

        return workerList.get(index);
    }

    //Lost in 3-players game, delete workers
    public void deleteWorker() {

    }

    /*
    public Tile getWorkerPosition(){
        // da sistemare
    }
    */

}

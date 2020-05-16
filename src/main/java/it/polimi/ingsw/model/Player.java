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
        workerList.add(new NoGod());
        workerList.add(new NoGod());

        if(godCard.equals("APOLLO")) {
            for(UndecoratedWorker worker : workerList){
                worker = new Apollo(worker);
            }
        }

        if(godCard.equals("ARTEMIS")) {
            for(UndecoratedWorker worker : workerList){
                worker = new Artemis(worker);
            }
        }

        if(godCard.equals("ATHENA")) {
            for(UndecoratedWorker worker : workerList){
                worker = new Athena(worker);
            }
        }

        if(godCard.equals("ATLAS")) {
            for(UndecoratedWorker worker : workerList){
                worker = new Atlas(worker);
            }
        }

        if(godCard.equals("DEMETER")) {
            for(UndecoratedWorker worker : workerList){
                worker = new Demeter(worker);
            }
        }

        if(godCard.equals("HEPHAESTUS")) {
            for(UndecoratedWorker worker : workerList){
                worker = new Hephaestus(worker);
            }
        }

        if(godCard.equals("MINOTAUR")) {
            for(UndecoratedWorker worker : workerList){
                worker = new Minotaur(worker);
            }
        }

        if(godCard.equals("PAN")) {
            for(UndecoratedWorker worker : workerList){
                worker = new Pan(worker);
            }
        }

        if(godCard.equals("PROMETHEUS")) {
            for(UndecoratedWorker worker : workerList){
                worker = new Prometheus(worker);
            }
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

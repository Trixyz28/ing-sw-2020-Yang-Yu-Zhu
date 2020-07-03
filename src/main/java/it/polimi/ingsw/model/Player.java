package it.polimi.ingsw.model;

import it.polimi.ingsw.model.God.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Class used to represent the PLayer.
 * <p></p>
 * Each user has his own Player.
 * <p>
 * In the game each player has got 2 workers and a GodPower card.
 * <p>
 * One player is the Challenger: he selects the available GodPowers in the game and he chooses the starting player.
 * <p>
 * A player wins if his worker does the "move" operation from a level 2 "Block" <code>Tile</code> to a level 3 "Block" <code>Tile</code>.
 * <p>
 * If a player cannot complete a "move" operation and a "build" operation in his turn, he loses.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Player {


    //ID assigned to each player in order by their join succession
    private int playerID;
    private String playerNickname;
    private boolean challenger;
    private String godCard;

    //Workers owned by this player
    private List<UndecoratedWorker> workerList;

    /**
     * Sets the player's nickname.
     * @param playerName Variable that represents the player's nickname as a string.
     */
    public Player(String playerName) {
        this.playerNickname = playerName;
    }

    /**
     * Gets the player's nickname.
     * @return A string which represents the player's nickname.
     */
    public String getPlayerNickname() {
        return playerNickname;
    }


    //PlayerID getter&setter

    /**
     * Gets the player's ID in the lobby.
     * @return An integer that represents the ID of the player in the current lobby.
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Sets the player's ID in the lobby.
     * @param playerID Variable that represents the ID of the player in the current lobby.
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }


    //Challenger getter&setter

    /**
     * Checks if the player is the challenger.
     * @return A boolean: <code>true</code> if the player at issue is the challenger, otherwise <code>false</code>.
     */
    public boolean isChallenger() {
        return challenger;
    }

    /**
     * Sets the player at issue as the challenger.
     * @param challenger Variable that represents a boolean that indicates if the player at issue is the challenger.
     */
    public void setChallenger(boolean challenger){
        this.challenger = challenger;
    }


    //When the player chooses a God from the list

    /**
     * Sets a God Card to the player at issue.
     * @param god Variable that represents which GodCard is at issue.
     */
    public void setGodCard(String god) {
        godCard = god;
    }

    /**
     * Gets the specific God Card of the player at issue.
     * @return A string that represents the God Card at issue.
     */
    public String getGodCard() {
        return godCard;
    }




    //Create 2 specific worker classes as indicated in godCard
    /**
     *Creats 2 specific <code>worker</code> classes as indicated by the GodCard.
     * @param godCard Variable that represents which GodCard is at issue.
     * @param condition Variable that represents the <code>condition</code> as some specific Gods need.
     * @param totalWorkerList Variable that is a list of strings that represents all the workers in the game.
     */
    public void createWorker(String godCard, Conditions condition, List<UndecoratedWorker> totalWorkerList) {

        workerList = new ArrayList<>();
        UndecoratedWorker w1 = new NoGod(playerID, condition);
        UndecoratedWorker w2 = new NoGod(playerID, condition);

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
            case "ZEUS":
                workerList.add(new Zeus(w1));
                workerList.add(new Zeus(w2));
                break;

            case "HESTIA":
                workerList.add(new Hestia(w1));
                workerList.add(new Hestia(w2));
                break;

            case "POSEIDON":
                workerList.add(new Poseidon(w1, totalWorkerList));
                workerList.add(new Poseidon(w2, totalWorkerList));
                break;

            case "LIMUS":
                workerList.add(new Limus(w1));
                workerList.add(new Limus(w2));
                break;

            case "TRITON":
                workerList.add(new Triton(w1));
                workerList.add(new Triton(w2));
                break;

            case "HERA":
                workerList.add(new Hera(w1));
                workerList.add(new Hera(w2));
        }

    }

    /**
     * Gets the current worker list of the game.
     * @return A list of strings that represents all the workers in the game.
     */
    public List<UndecoratedWorker> getWorkerList() {
        return workerList;
    }


    //Select the worker to move/build

    /**
     * Selects a particular worker which is the one the player choose to do a "move" or "build" operation.
     * @param index Variable that indicates the index of the worker in question.
     * @return The worker that was chosen by the player at issue.
     */
    public UndecoratedWorker chooseWorker(int index) {
        return workerList.get(index);
    }


    //Lost in 3-players game, delete workers

    /**
     *Deletes the workers of a particular player.This method is only needed in a 3-players game.
     */
    public void deleteWorker() {
        for (UndecoratedWorker w : workerList){
            w.getPosition().setOccupiedByWorker(false);
        }
    }


}

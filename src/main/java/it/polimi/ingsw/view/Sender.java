package it.polimi.ingsw.view;

import it.polimi.ingsw.observers.Observable;

/**
 * Class that implements the Sender of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Sender extends Observable<String> {

    private boolean chooseWorker;

    /**
     *Creates a <code>Sender</code> with the specified attributes.
     */
    public Sender() {
        this.chooseWorker = false;
    }

    /**
     *Sends an input.
     * @param str The input that needs to be sent.
     */
    public void sendInput(String str) {
        notify(str);
    }

    /**
     *Checks if it is in the choose worker phase.
     * @return A boolean: <code>true</code> if the input required is a worker index, otherwise <code>false</code>.
     */
    public boolean isChooseWorker() {
        return chooseWorker;
    }

    /**
     * Sets the choose worker phase.
     * @param condition Variable that updates the choose worker phase.
     */
    public void setChooseWorker(boolean condition) {
        this.chooseWorker = condition;
    }

}

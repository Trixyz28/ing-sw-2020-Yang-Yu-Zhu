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
     *Checks if the worker at issue is chosen with a condition.
     * @return A boolean: <code>true</code> if the worker at issue is chosen, otherwise <code>false</code>.
     */
    public boolean isChooseWorker() {
        return chooseWorker;
    }

    /**
     *Sets the worker at issue as chosen with a condition.
     * @param condition Variable that represents the condition that is afflicted to the worker.
     */
    public void setChooseWorker(boolean condition) {
        this.chooseWorker = condition;
    }

}

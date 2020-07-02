package it.polimi.ingsw.observers;

/**
 * Class that implements the Observer interface.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public interface Observer<T> {

    /**
     * Updates the Observers based on the message.
     * @param message Variable that indicates the changes of the Observable.
     */
    void update(T message);

}

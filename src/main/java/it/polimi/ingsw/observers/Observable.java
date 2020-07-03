package it.polimi.ingsw.observers;

import java.util.ArrayList;
import java.util.List;
/**
 * Class that implements the Observable.
 * @param <T> Variable that represents the object that is used for notify and update.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();

    /**
     * Adds the Observers to the Observable
     * @param observer Variable that indicates which Observer is being added.
     */
    public void addObservers(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * Notifies any changes for Observers.
     * @param message Variable that indicates which message is being sent.
     */
    public void notify(T message){
        synchronized (observers) {
            for(Observer<T> o: observers) {
                o.update(message);
            }
        }

    }


}

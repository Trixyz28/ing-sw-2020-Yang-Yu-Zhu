package it.polimi.ingsw.observers;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private List<Observer<T>> observers = new ArrayList<>();

    public void addObservers(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void notify(T message){
        synchronized (observers) {
            for(Observer<T> o: observers) {
                o.update(message);
            }
        }
    }

    /*
    public void notifyGod(String god){
        for(Observer o: observers) {
            o.updateGod(god.toUpperCase());
        }
    }

    da definire
     */

}

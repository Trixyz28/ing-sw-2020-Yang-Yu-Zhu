package it.polimi.ingsw.observers;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private final List<Observer> observers = new ArrayList<>();

    public void addObservers(Observer observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void notify(Object message){
        synchronized (observers) {
            for(Observer o: observers) {
                o.update(message);
            }
        }

    }


}

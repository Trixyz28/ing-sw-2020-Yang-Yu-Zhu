package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private boolean changed = false;
    public boolean isChanged() {
        return changed;
    }

    public void setChanged() {

        this.changed = true;
    }
    public void clearChanged() {
        this.changed = false;
    }



    private List<Observer<T>> observers = new ArrayList<>();

    public void addObservers(Observer<T> observer){
        observers.add(observer);
    }

    public void notify(T message){
        for(Observer<T> observer: observers){
            observer.update(message);
        }
    }
}

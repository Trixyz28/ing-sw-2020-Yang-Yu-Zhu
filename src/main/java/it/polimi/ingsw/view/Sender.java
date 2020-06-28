package it.polimi.ingsw.view;

import it.polimi.ingsw.observers.Observable;


public class Sender extends Observable {

    public void sendInput(String str) {
        notify(str);
    }

}

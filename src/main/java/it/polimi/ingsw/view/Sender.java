package it.polimi.ingsw.view;

import it.polimi.ingsw.observers.Observable;


public class Sender extends Observable<String> {

    private boolean chooseWorker;

    public Sender() {
        this.chooseWorker = false;
    }


    public void sendInput(String str) {
        notify(str);
    }

    public boolean isChooseWorker() {
        return chooseWorker;
    }

    public void setChooseWorker(boolean condition) {
        this.chooseWorker = condition;
    }

}

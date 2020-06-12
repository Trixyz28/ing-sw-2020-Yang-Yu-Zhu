package it.polimi.ingsw.messages;

import java.io.Serializable;

public class TurnMessage implements Serializable {

    private int currentID;

    public TurnMessage(int id) {
        this.currentID = id;
    }

    public int getCurrentID() {
        return currentID;
    }

}

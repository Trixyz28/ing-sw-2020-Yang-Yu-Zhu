package it.polimi.ingsw.messages;


import java.io.Serializable;

public class GodChosenMessage implements Serializable  {

    private final String god;

    public GodChosenMessage(String god) {
        this.god = god;
    }

    public String getGod() {
        return god;
    }



}

package it.polimi.ingsw.messages;


import java.io.Serializable;


public class LobbyMessage implements Serializable {

    private final String command;
    private final int number;

    public LobbyMessage(String command,int number) {
        this.command = command;
        this.number = number;
    }

    public String getCommand() {
        return command;
    }

    public int getNumber() {
        return number;
    }


}

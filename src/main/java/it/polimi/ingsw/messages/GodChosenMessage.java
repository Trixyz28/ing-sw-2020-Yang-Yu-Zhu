package it.polimi.ingsw.messages;


import java.io.Serializable;


public class GodChosenMessage implements Serializable  {

    private final String command;
    private final String god;
    private String player;

    public GodChosenMessage(String command,String god) {
        this.command = command;
        this.god = god;
    }

    public GodChosenMessage(String command,String god,String player) {
        this.command = command;
        this.god = god;
        setPlayer(player);
    }


    public String getCommand() {
        return command;
    }

    public String getGod() {
        return god;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String name) {
        this.player = name;
    }

}

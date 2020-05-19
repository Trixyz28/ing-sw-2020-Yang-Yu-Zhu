package it.polimi.ingsw.model;

import java.io.Serializable;

public class GameMessage implements Serializable {
    private final String message;
    private String answer;
    private final String player;

    public GameMessage(final Player player, final String message){
        this.player = player.getPlayerNickname();
        this.message = message;
    }

    public String getPlayer(){
        return player;
    }

    public String getMessage(){
        return message;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }


}

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


    //Player name getter
    public String getPlayer(){
        return player;
    }


    //Message getter
    public String getMessage(){
        return message;
    }


    //Answer getter&setter
    public String getAnswer(){
        return answer;
    }
    public void setAnswer(String answer){
        this.answer = answer;
    }

}

package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Player;

import java.io.Serializable;


public class GameMessage implements Serializable {


    private final String message;
    private String answer;
    private final String player;
    private final boolean readOnly;

    public GameMessage(final Player player, final String message, final boolean readOnly){
        this.player = player.getPlayerNickname();
        this.message = message;
        this.readOnly = readOnly;
    }


    //Player name getter
    public String getPlayer(){
        return player;
    }

    public boolean readOnly() {
        return readOnly;
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

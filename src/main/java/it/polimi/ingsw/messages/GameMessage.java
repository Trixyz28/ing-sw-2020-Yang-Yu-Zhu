package it.polimi.ingsw.messages;

import java.io.Serializable;


public class GameMessage implements Serializable {


    private final String message;
    private String answer;

    public GameMessage(final String message){
        this.message = message;
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

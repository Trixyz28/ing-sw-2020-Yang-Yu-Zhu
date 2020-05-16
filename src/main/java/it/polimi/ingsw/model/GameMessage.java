package it.polimi.ingsw.model;

public class GameMessage {
    private String message;
    private String answer;
    private Player player;

    public GameMessage(final Player player, final String message){
        this.player = player;
        this.message = message;
    }

    public Player getPlayer(){
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

package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.BoardView;

import java.io.Serializable;
import java.util.ArrayList;


public class Obj implements Serializable {


    private final String tag;

    private String player;
    private String message;
    private ArrayList<String> list;

    private BoardView boardView;
    private Operation operation;
    private GameMessage gameMessage;

    private boolean broadcast = true;
    private String receiver;

    public Obj(String tag, String message) {
        this.tag = tag;
        this.message = message;
    }

    public Obj(String tag, String message, String player) {
        this.tag = tag;
        this.message = message;
        this.player = player;
    }

    public Obj(String tag, ArrayList<String> list) {
        this.tag = tag;
        this.list = list;
    }

    public Obj(BoardView boardView) {
        this.tag = Tags.BOARD;
        this.boardView = boardView;
    }

    public Obj(Operation operation) {
        this.tag = Tags.OPERATION;
        this.operation = operation;
    }

    public Obj(GameMessage gameMessage) {
        this.tag = Tags.G_MSG;
        this.gameMessage = gameMessage;
    }


    public String getTag() {
        return tag;
    }

    public String getMessage() {
        return message;
    }

    public String getPlayer() {
        return player;
    }

    public ArrayList<String> getList() {
        return list;
    }



    public BoardView getBoardView() {
        return boardView;
    }

    public Operation getOperation() {
        return operation;
    }

    public GameMessage getGameMessage() { return gameMessage; }



    public boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean b) {
        this.broadcast = b;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(Player player) {
        this.receiver = player.getPlayerNickname();
    }

}

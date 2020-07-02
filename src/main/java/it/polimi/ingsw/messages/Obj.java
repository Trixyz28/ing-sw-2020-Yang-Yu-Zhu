package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.BoardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Obj used in the communication between clients-server.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Obj implements Serializable {

    private final String tag;

    private String player;
    private String message;
    private List<String> list;

    private BoardView boardView;
    private Operation operation;
    private GameMessage gameMessage;

    private boolean broadcast = true;
    private String receiver;

    /**
     * Creates a <code>Obj</code> for tag and message attributes.
     * @param tag Variable that indicates the tag attribute that must be encapsulated.
     * @param message Variable that indicates the message attribute that must be encapsulated.
     */
    public Obj(String tag, String message) {
        this.tag = tag;
        this.message = message;
    }

    /**
     * Creates a <code>Obj</code> for tag,message and player attributes.
     * @param tag Variable that indicates the tag attribute that must be encapsulated.
     * @param message Variable that indicates the message attribute that must be encapsulated.
     * @param player Variable that indicates the player attribute that must be encapsulated.
     */
    public Obj(String tag, String message, String player) {
        this.tag = tag;
        this.message = message;
        this.player = player;
    }

    /**
     * Creates a <code>Obj</code>  for tag and list attributes.
     * @param tag Variable that indicates the tag attribute that must be encapsulated.
     * @param list Variable that indicates the list attribute that must be encapsulated.
     */
    public Obj(String tag, List<String> list) {
        this.tag = tag;
        this.list = list;
    }

    /**
     * Creates a <code>Obj</code> for boardView.
     * @param boardView Variable that represents the boardView that must be encapsulated.
     */
    public Obj(BoardView boardView) {
        this.tag = Tags.BOARD;
        this.boardView = boardView;
    }

    /**
     * Creates a <code>Obj</code>  for operation.
     * @param operation Variable that represents the operation that must be encapsulated.
     */
    public Obj(Operation operation) {
        this.tag = Tags.OPERATION;
        this.operation = operation;
    }

    /**
     * Creates a <code>Obj</code> for GameMessage.
     * @param gameMessage Variable that represents the gameMessage that must be encapsulated.
     */
    public Obj(GameMessage gameMessage) {
        this.tag = Tags.G_MSG;
        this.gameMessage = gameMessage;
    }

    /**
     * Gets a tag encapsulated in the obj.
     * @return The value of the tag attribute.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Gets a message encapsulated in the obj.
     * @return The value of the message attribute.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets a player encapsulated in the obj.
     * @return The value of the player attribute.
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Gets a list encapsulated in the obj.
     * @return THe value of the list attribute.
     */
    public List<String> getList() {
        return list;
    }


    /**
     * Gets a boardView encapsulated in the obj.
     * @return The value of the boardView.
     */
    public BoardView getBoardView() {
        return boardView;
    }

    /**
     * Gets an operation encapsulated in the obj.
     * @return The value of the operation.
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * Gets a gameMessage encapsulated in the obj.
     * @return The value of the gameMessage.
     */
    public GameMessage getGameMessage() { return gameMessage; }


    /**
     *Checks if the obj is a broadcast.
     * @return The value of the broadcast attribute.
     */
    public boolean isBroadcast() {
        return broadcast;
    }

    /**
     * Sets the parameter as the broadcast of the obj.
     * @param b A boolean that indicates if the obj is a broadcast.
     */
    public void setBroadcast(boolean b) {
        this.broadcast = b;
    }

    /**
     * Gets a receiver encapsulated in the obj.
     * @return The value of the receiver attribute.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the parameter as the receiver of the obj.
     * @param player Variable that represents the player that is the recipient of the obj.
     */
    public void setReceiver(Player player) {
        this.receiver = player.getPlayerNickname();
    }

}

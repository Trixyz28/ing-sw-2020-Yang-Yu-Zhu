package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.observers.Observer;

/**
 * Class that implements the view of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public abstract class View extends Observable<Obj> implements Observer<Obj> {

    protected Player player;
    protected Operation operation;
    protected GameMessage gameMessage;
    protected Obj obj;

    /**
     *Creates a <code>View</code> with the specified attributes.
     * @param player Variable that represents the player at issue.
     */
    public View(Player player){
        this.player = player;
    }

    /**
     * Modifies the previously saved operation based on client input.
     * @param input Variable that describes the input at issue.
     */
    protected void handleOp(String input){  /* Update the saved Operation with Client input */
        try {
            String[] inputs = input.split(",");
            int row = Integer.parseInt(inputs[0]);
            int column = Integer.parseInt(inputs[1]);
            operation = obj.getOperation();
            operation.setPosition(row, column);
            System.out.println("Received: Operation type " + operation.getType() + " ("
                    + operation.getRow() + "," + operation.getColumn() + ")");
            notify(obj);

        }catch (IllegalArgumentException e){
            System.out.println("Invalid input");
        }
    }

    /**
     * Modifies the previously saved GameMessage based on client input.
     * @param input Variable that describes the input at issue.
     */
    protected void handleGm(String input){  /* Update the saved GameMessage with Client input */
        gameMessage = obj.getGameMessage();

        gameMessage.setAnswer(input);
        System.out.println("Received: Answer = " + gameMessage.getAnswer());
        notify(obj);
    }

    /**
     *Handles the Messages that are sent in input by a client.
     * @param input Variable that describes the messages sent by the client at issue.
     */
    protected void messageString(String input) {
        GameMessage gm = new GameMessage(null);
        gm.setAnswer(input);
        Obj obj1 = new Obj(gm);
        obj1.setReceiver(player);
        notify(obj1);
    }

}

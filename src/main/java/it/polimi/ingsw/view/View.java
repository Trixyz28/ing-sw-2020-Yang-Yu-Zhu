package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.observers.Observer;


public abstract class View extends Observable<Obj> implements Observer<Obj> {

    protected Player player;
    protected Operation operation;
    protected GameMessage gameMessage;
    protected Obj obj;


    public View(Player player){
        this.player = player;
    }


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


    protected void handleGm(String input){  /* Update the saved GameMessage with Client input */
        gameMessage = obj.getGameMessage();

        gameMessage.setAnswer(input);
        System.out.println("Received: Answer = " + gameMessage.getAnswer());
        notify(obj);
    }


    void messageString(String input) {
        GameMessage gm = new GameMessage(null);
        gm.setAnswer(input);
        Obj obj1 = new Obj(gm);
        obj1.setReceiver(player);
        notify(obj1);
    }

}

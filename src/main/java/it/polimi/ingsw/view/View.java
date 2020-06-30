package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.observers.Observer;


public abstract class View extends Observable implements Observer {

    protected Player player;
    protected Operation operation;
    protected GameMessage gameMessage;
    protected Obj obj;


    public View(Player player){
        this.player = player;
    }



    protected void handleOp(String input){  /* modificare l'Op precedentemente salvata con l'input dal Client */
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
            System.out.println("Inserimento invalido");
        }
    }


    protected void handleGm(String input){  /* modificare Gm precedentemente salvata con l'input dal Client */
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

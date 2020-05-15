package it.polimi.ingsw.view;

import it.polimi.ingsw.Observer;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.Connection;

public class RemoteView extends View {

    private Connection clientConnection;

    private class MessageReceiver implements Observer {

        @Override
        public void update(Object message) {  /* ricevere messaggi dal Client: Nickname o God */
            String input = (String) message;
            System.out.println("Received: " + input);
            try{
                RemoteView.this.notify(input);
            }catch (IllegalArgumentException e){
                clientConnection.asyncSend("Error");
            }

        }
    }

    private class OperationReceiver implements Observer {  /* ricevere move o build dal Client */

        @Override
        public void update(Object message) {
            Operation operation = (Operation) message;
            System.out.println("Received: Operation type " + operation.getType() + " ("
                    + operation.getRow() + ", " + operation.getColumn() + ")");
            try{
                RemoteView.this.notify(operation);
            }catch (IllegalArgumentException e){
                clientConnection.asyncSend("Error");
            }
        }
    }

    public RemoteView(Player player, Connection c){
        super(player);
        this.clientConnection = c;
        c.addObservers(new MessageReceiver());
        c.addObservers(new OperationReceiver());
    }

    public void showMessage(String message){  /* utilizzato dal Controller per messaggi al Client */
        clientConnection.asyncSend(message);
    }

    @Override
    public void update(Object message) {

        if(message instanceof String[]){  /* godList Completo */
            showComplete((String[]) message);
        }
    }

    private void showComplete(String[] godList){
        clientConnection.asyncSend("Le divinità che puoi scegliere sono:");
        clientConnection.asyncSend(godList);  /* Array di Stringhe da stampare al lato Client */

    }

}

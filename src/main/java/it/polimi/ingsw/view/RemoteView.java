package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.server.SocketConnection;

import java.util.ArrayList;


public class RemoteView extends View {

    private SocketConnection clientConnection;

    private class MessageReceiver implements Observer {

        @Override
        public void update(Object message) {  /* ricevere messaggi dal Client: Nickname o God */
            if(message instanceof String) {
                String input = (String) message;
                System.out.println("Received: " + input);

                messageString(input);
            }
            if(message instanceof Operation){
                Operation operation = (Operation) message;
                System.out.println("Received: Operation");

                messageOperation(operation);

            }
            if(message instanceof GameMessage){
                GameMessage gm = (GameMessage) message;
                System.out.println("Received: GameMessage");

                if(gm.getMessage().equals(Messages.Worker)){  //Answer = 0 o 1
                        workerIndex(gm.getAnswer());
                }else {
                    gameMessage(gm);
                }
            }
        }
    }

    public RemoteView(Player player, SocketConnection c){
        super(player);
        this.clientConnection = c;
        c.addObservers(new MessageReceiver());
    }

    public void showMessage(String message){  /* utilizzato dal Controller per messaggi al Client */
        clientConnection.asyncSend(message);
    }

    @Override
    public void update(Object message) {

        if (message instanceof ArrayList){
            clientConnection.asyncSend(message);  /* currentList */
        }

        if(message instanceof String[]){  /* godList Completo */
            showComplete((String[]) message);
        }

        if(message instanceof Board){  /* Mappa */
            clientConnection.asyncSend(message);
        }

        if(message instanceof Operation){  /* inviare l'Operation con tipo già definito solo al currentPlayer*/
            if(((Operation) message).getPlayer().equals(player.getPlayerNickname())) {
                clientConnection.asyncSend(message);
            }
        }
        if(message instanceof GameMessage){  /* inviare richiesta solo al currentPlayer*/
            if(((GameMessage) message).getPlayer().equals(player.getPlayerNickname())) {
                clientConnection.asyncSend(message);
            }
        }

        if(message instanceof String) {
            clientConnection.asyncSend(message);
        }
    }

    private void showComplete(String[] godList){
        if(clientConnection.getPlayer().isChallenger()) {
            clientConnection.asyncSend("You can choose godcards from this list: ");
            clientConnection.asyncSend(godList);  /* Array di Stringhe da stampare al lato Client */
        } else {
            clientConnection.asyncSend("Waiting for the challenger's choice");
        }


    }

}

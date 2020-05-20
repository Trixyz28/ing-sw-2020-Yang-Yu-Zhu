package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.server.SocketConnection;
import it.polimi.ingsw.view.cli.BoardView;

import java.util.ArrayList;


public class RemoteView extends View {

    private SocketConnection clientConnection;
    protected boolean opSend;
    protected boolean gmSend;

    private class MessageReceiver implements Observer {

        @Override
        public void update(Object message) {  /* ricevere messaggi dal Client: Nickname o God */
            if(message instanceof String) {
                if(opSend){  /* se precedentemente è stata inviata una Operation */
                    opSend = false;
                    handleOp((String)message);
                }else if(gmSend){  /* se precedentemente è stata inviata una Operation */
                    gmSend = false;
                    handleGm((String)message);
                }else{
                    String input = (String) message;
                    System.out.println("Received: " + input);
                    messageString(input);
                }
            }
        }
    }

    public RemoteView(Player player, SocketConnection c){
        super(player);
        this.clientConnection = c;
        c.addObservers(new MessageReceiver());
        opSend = false;
        gmSend = false;
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

        if(message instanceof BoardView){  /* Mappa */
            clientConnection.asyncSend(message);
        }

        if(message instanceof Operation){  /* inviare l'Operation con tipo già definito solo al currentPlayer*/
            if(((Operation) message).getPlayer().equals(player.getPlayerNickname())) {
                operation = (Operation)message;  /* salvare l'op nella view e notify */
                opSend = true;
                clientConnection.asyncSend(message);
            }
        }
        if(message instanceof GameMessage){  /* inviare richiesta solo al currentPlayer*/
            if(((GameMessage) message).getPlayer().equals(player.getPlayerNickname())) {
                gameMessage = (GameMessage)message;  /* salvare prima di notify */
                gmSend = true;
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

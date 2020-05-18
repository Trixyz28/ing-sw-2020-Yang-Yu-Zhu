package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.server.SocketConnection;


public class RemoteView extends View {

    private SocketConnection clientConnection;

    private class MessageReceiver implements Observer {

        @Override
        public void update(Object message) {  /* ricevere messaggi dal Client: Nickname o God */
            if(message instanceof String) {
                String input = (String) message;
                System.out.println("Received: " + input);
                try {
                    GameMessage gm = new GameMessage(player, null);
                    gm.setAnswer(input);
                    RemoteView.this.notify(gm);
                } catch (IllegalArgumentException e) {
                    clientConnection.asyncSend("Error");
                }
            }
            if(message instanceof Operation){
                Operation operation = (Operation) message;
                System.out.println("Received: Operation type " + operation.getType() + " ("
                        + operation.getRow() + ", " + operation.getColumn() + ")");
                try{
                    RemoteView.this.notify(operation);
                }catch (IllegalArgumentException e){
                    clientConnection.asyncSend("Error");
                }
            }
            if(message instanceof GameMessage){
                GameMessage gm = (GameMessage) message;
                System.out.println("Received: Answer : " + gm.getAnswer());
                try{
                    if(gm.getMessage().equals(Messages.Worker)){  //Answer = 0 o 1
                        RemoteView.this.notify(Integer.parseInt(gm.getAnswer()));
                    }else {
                        RemoteView.this.notify(gm);
                    }
                }catch (IllegalArgumentException e){
                    clientConnection.asyncSend("Error");
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

        if(message instanceof String[]){  /* godList Completo */
            showComplete((String[]) message);
        }

        if(message instanceof Board){  /* Mappa */
            clientConnection.asyncSend(message);
        }

        if(message instanceof Operation){  /* inviare l'Operation con tipo già definito solo al currentPlayer*/
            if(((Operation) message).getPlayer().getPlayerNickname().equals(player.getPlayerNickname())) {
                clientConnection.asyncSend(message);
            }
        }
        if(message instanceof GameMessage){  /* inviare richiesta solo al currentPlayer*/
            if(((GameMessage) message).getPlayer().getPlayerNickname().equals(player.getPlayerNickname())) {
                clientConnection.asyncSend(message);
            }
        }
    }

    private void showComplete(String[] godList){
        clientConnection.asyncSend("Le divinità che puoi scegliere sono:");
        clientConnection.asyncSend(godList);  /* Array di Stringhe da stampare al lato Client */

    }

}

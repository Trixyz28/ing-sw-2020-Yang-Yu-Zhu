package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.server.SocketConnection;


public class RemoteView extends View {

    private SocketConnection connection;
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
        this.connection = c;
        c.addObservers(new MessageReceiver());
        opSend = false;
        gmSend = false;
    }

    @Override
    public void update(Object message) {

        Obj obj = (Obj)message;

        if(connection.isActive()) {

            if(obj.isBroadcast()) {

                if(obj.getTag().equals(Tags.completeList)) {
                    if(connection.getPlayer().isChallenger()) {
                        connection.asyncSend(message);
                    }
                } else if (obj.getTag().equals(Tags.end)) {
                    connection.asyncSend(obj);

                    if(player.getPlayerNickname().equals(obj.getPlayer())) {
                        if(obj.getMessage().equals("lose")) {
                            connection.setLost(true);
                        } else {
                            connection.closeMatch();
                        }
                    }
                } else {
                    connection.asyncSend(message);
                }

            } else {
                if(connection.getPlayer().getPlayerNickname().equals(obj.getReceiver())) {

                    if(obj.getTag().equals(Tags.operation)) {
                        this.obj = obj;  /* salvare l'op nella view e notify */
                        opSend = true;
                    } else if (obj.getTag().equals(Tags.gMsg)) {
                        /* salvare prima di notify */
                        this.obj = obj;
                        gmSend = true;
                    }
                    connection.asyncSend(message);
                }
            }

        }


    }

}

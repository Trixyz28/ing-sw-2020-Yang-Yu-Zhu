package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.server.SocketConnection;


public class RemoteView extends View {

    private final SocketConnection connection;
    protected boolean opSend;
    protected boolean gmSend;


    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {  /* ricevere messaggi dal Client: Nickname o God */
            if(opSend){  /* se precedentemente è stata inviata una Operation */
                opSend = false;
                handleOp(message);
            }else if(gmSend){  /* se precedentemente è stata inviata una Operation */
                gmSend = false;
                handleGm(message);
            }else{
                String input = message;
                System.out.println("Received: " + input);
                messageString(input);
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
    public void update(Obj message) {

        if(connection.isActive()) {

            if(message.isBroadcast()) {

                if(message.getTag().equals(Tags.COMPLETE_LIST)) {
                    if(connection.getPlayer().isChallenger()) {
                        connection.send(message);
                    }
                } else if (message.getTag().equals(Tags.END)) {
                    connection.send(message);

                    if(message.getMessage().equals("lose")) {
                        if (player.getPlayerNickname().equals(message.getPlayer())) {
                            connection.setLost(true);
                        }
                    } else {
                        if(message.getReceiver().equals(player.getPlayerNickname())) {
                            connection.closeMatch();
                        }
                    }
                } else {
                    connection.send(message);
                }

            } else {
                if(connection.getPlayer().getPlayerNickname().equals(message.getReceiver())) {

                    if(message.getTag().equals(Tags.OPERATION)) {
                        this.obj = message;  /* salvare l'op nella view e notify */
                        opSend = true;
                    } else if (message.getTag().equals(Tags.G_MSG)) {
                        /* salvare prima di notify */
                        this.obj = message;
                        gmSend = true;
                    }
                    connection.send(message);
                }
            }
        }

    }

}

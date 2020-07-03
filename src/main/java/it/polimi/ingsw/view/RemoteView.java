package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.server.SocketConnection;
/**
 * Class that implements the remote view of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */

public class RemoteView extends View {

    private final SocketConnection connection;
    protected boolean opSend;
    protected boolean gmSend;


    private class MessageReceiver implements Observer<String> {

        /**
         * {@inheritDoc}
         */
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

    /**
     * Creates a <code>RemoteView</code> with the specified attributes.
     * @param player Variable that represents the player of the current client.
     * @param c Variable that indicates the socket connection of the current client.
     */
    public RemoteView(Player player, SocketConnection c){
        super(player);
        this.connection = c;
        c.addObservers(new MessageReceiver());
        opSend = false;
        gmSend = false;
    }

    /**
     * {@inheritDoc}
     */
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

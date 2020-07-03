package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.cli.CLI;

import java.io.*;
import java.net.Socket;


/**
 * Sets up the client of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class Client implements Observer<String>, Runnable {

    private Socket socket;
    private ObjectInputStream socketIn;
    private PrintWriter socketOut;

    private boolean active;
    private boolean opReceived;
    private boolean gmReceived;

    private Ui ui;
    private Thread t0;

    /**
     *Creates a <code>Client</code> with the specified attributes.
     */
    public Client() {
        this.opReceived = false;
        this.gmReceived = false;
        this.active = true;
    }

    /**
     * Sets up the <code>Client</code>.
     * <p>
     * If there's any IOException prints out the subsequent error.
     * @param uiStyle Variable for the choice of the style of the <code>ui</code>.
     * @param socket Variable for the socket functionality.
     */
    public void setupClient(String uiStyle, Socket socket) {

        //Create CLI
        if(uiStyle.equalsIgnoreCase("CLI")) {
            ui = new CLI();
        }
        this.socket = socket;

        try {
            //Set I/O streams
            this.socketIn = new ObjectInputStream(socket.getInputStream());
            this.socketOut = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {

        try {
            t0 = asyncReadFromSocket(socketIn);
            t0.join();

        } catch (Exception e) {
            System.out.println("Client.run() stopped");

        } finally{
            try {
                ui.showMessage(Messages.connectionClosed);
                socketOut.close();
                socketIn.close();
                socket.close();
                System.out.println("Socket closed");
            } catch (IOException e) {
                System.out.println("Error when closing socket");
            }
        }
    }

    /**
     * Starts a thread connected to a socket with the server.
     * @param socketIn The socket which is used to connect with the server.
     * @return The thread that was created and used with the socket.
     * @throws IllegalArgumentException Is thrown if the input is not correct.
     */
    public Thread asyncReadFromSocket(ObjectInputStream socketIn) {

        Thread t = new Thread(() -> {

            try {
                while(isActive()) {
                    Object inputObject = socketIn.readObject();

                    if(inputObject instanceof String) {
                        ui.showMessage((String)inputObject);

                    } else if (inputObject instanceof Obj) {
                        Obj obj = (Obj) inputObject;
                        splitMessage(obj);
                    }
                }
            } catch (Exception e) {
                System.out.println("Reading thread stopped");
                setActive(false);
            }
        });

        t.start();
        return t;
    }

    /**
     * Set of the <code>ui</code> variable.
     * @param ui Variable that is used for the user interface.
     */
    public void setUi(Ui ui) {
        this.ui = ui;
    }


    /**
     * Method that is used to send the choices of the user to the server.
     * @param input A string that represents the input from the user.
     */
    public void writeToSocket(String input) {

        if(opReceived) {

            try {  /* coordinate Tile */
                String[] inputs = input.split(",");
                int row = Integer.parseInt(inputs[0]);
                int column = Integer.parseInt(inputs[1]);
                if (row < 5 && column < 5 && row >= 0 && column >= 0) {
                    socketOut.println(input);
                    socketOut.flush();
                    opReceived = false;
                }else {
                    ui.showMessage(Messages.invalidTile);
                }

            } catch (Exception e) {
                ui.showMessage(Messages.wrongArgument);
            }

        } else if (gmReceived) {  /* Answer to message*/

            input = input.toUpperCase();

            socketOut.println(input);
            socketOut.flush();
            gmReceived = false;

        } else {
            socketOut.println(input);
            socketOut.flush();
        }
    }

    /**
     * Gets the status of the client's <code>active</code> attribute.
     * @return The value of the attribute active.
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * Sets the client's <code>active</code> attribute.
     * @param active Indicates if the client at issue is in his turn or not.
     */
    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    /**
     *Splits the obj,interprets it and updates the game state.
     * @param obj Comes from the socket stream encapsulating game updates.
     */
    public void splitMessage(Obj obj) {
        switch (obj.getTag()) {
            case Tags.OPERATION -> {
                opReceived = true;  /* receveid an Operation */
                Operation operation = obj.getOperation();
                if (operation.getType() == 1) {
                    ui.handleBoardMsg(Messages.move);
                } else if (operation.getType() == 2) {
                    ui.handleBoardMsg(Messages.Build);
                }
            }
            case Tags.G_MSG -> {
                gmReceived = true;
                ui.handleGameMsg(obj.getGameMessage().getMessage());
            }
            case Tags.PLAYER_LIST -> ui.handlePlayerList(obj.getList());
            case Tags.TURN -> ui.handleTurn(obj.getMessage());
            case Tags.DEFINE_GOD -> ui.handleDefineGod(obj.getMessage());
            case Tags.CHOOSE_GOD -> ui.handleChooseGod(obj);
            case Tags.BOARD -> ui.updateBoard(obj.getBoardView());
            case Tags.BOARD_MSG -> ui.handleBoardMsg(obj.getMessage());
            case Tags.END -> ui.endGame(obj);
            case Tags.GENERIC -> ui.showMessage(obj.getMessage());
            default -> ui.showObj(obj);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(String message) {
        writeToSocket(message);
    }

}


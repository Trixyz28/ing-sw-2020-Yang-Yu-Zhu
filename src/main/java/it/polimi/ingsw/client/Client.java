package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.cli.CLI;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;


public class Client implements Observer, Runnable {

    private Socket socket;
    private ObjectInputStream socketIn;
    private PrintWriter socketOut;

    private boolean active;
    private boolean opReceived;
    private boolean gmReceived;
    private GameMessage gMessage;

    private Ui ui;
    private Thread t0;

    public Client() {
        this.opReceived = false;
        this.gmReceived = false;
        this.active = true;
    }


    public void setupClient(String uiStyle, Socket socket) {

        //Create CLI
        if(uiStyle.toUpperCase().equals("CLI")) {
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

    @Override
    public void run() {

        try {
            t0 = asyncReadFromSocket(socketIn);
            t0.join();

        } catch (NoSuchElementException | InterruptedException e) {
            ui.showMessage(Messages.connectionClosed);
            System.out.println("Client.run() stopped");

        } finally{
            try {
                socketOut.close();
                socketIn.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("Error when closing socket");
            }
        }
    }

    public Thread asyncReadFromSocket(ObjectInputStream socketIn) {

        Thread t = new Thread(() -> {

            try {
                while(isActive()) {
                    Object inputObject = socketIn.readObject();

                    if(inputObject instanceof String) {
                        ui.showMessage((String)inputObject);


                    } else if (inputObject instanceof Obj) {
                        Obj obj = (Obj)inputObject;
                        splitMessage(obj);

                    } else {
                        throw new IllegalArgumentException();
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


    public void setUi(Ui ui) {
        this.ui = ui;
    }



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

        } else if (gmReceived) {  /* Risposta al messaggio */

            input = input.toUpperCase();
            /*
            if (gMessage.getMessage().equals(Messages.Worker)) {
                try {  /* indice worker o 0 o 1
                    int index = Integer.parseInt(input);
                    if (index == 0 || index == 1) {
                        socketOut.println(input);
                        socketOut.flush();
                        gmReceived = false;
                        gMessage = null;
                    } else {
                        ui.showMessage(Messages.invalidWorker);
                    }
                } catch (Exception e) {
                    ui.showMessage(Messages.wrongArgument);
                }
            } else {  /* problema yes or no

             */
                socketOut.println(input);
                socketOut.flush();
                gmReceived = false;
                gMessage = null;

        } else {
            socketOut.println(input);
            socketOut.flush();
        }

    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }


    public void splitMessage(Obj obj) {
        switch (obj.getTag()) {
            case Tags.operation -> {
                opReceived = true;  /* ricevuto un Operation */
                Operation operation = obj.getOperation();
                if (operation.getType() == 1) {
                    ui.handleBoardMsg(Messages.move);
                } else if (operation.getType() == 2) {
                    ui.handleBoardMsg(Messages.Build);
                }
            }
            case Tags.gMsg -> {
                gmReceived = true;
                gMessage = obj.getGameMessage();
                ui.handleGameMsg(obj.getGameMessage().getMessage());
            }
            case Tags.playerList -> ui.handlePlayerList(obj.getList());
            case Tags.turn -> ui.handleTurn(obj.getMessage());
            case Tags.defineGod -> ui.handleDefineGod(obj.getMessage());
            case Tags.chooseGod -> ui.handleChooseGod(obj);
            case Tags.board -> ui.updateBoard(obj.getBoardView());
            case Tags.boardMsg -> ui.handleBoardMsg(obj.getMessage());
            case Tags.end -> ui.endGame(obj);
            case Tags.generic -> ui.showMessage(obj.getMessage());
            default -> ui.showObj(obj);
        }
    }


    @Override
    public void update(Object message) {
        writeToSocket((String)message);
    }

}


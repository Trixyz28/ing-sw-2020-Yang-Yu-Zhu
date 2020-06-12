package it.polimi.ingsw.client;

import it.polimi.ingsw.model.GameMessage;
import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Client implements Runnable {

    private Socket socket;
    private ObjectInputStream socketIn;
    private PrintWriter socketOut;

    private boolean active;
    private boolean opReceived;
    private boolean gmReceived;
    private GameMessage gMessage;

    private Ui ui;
    private Thread t0;
    private Thread t1;




    public Client() {
        this.opReceived = false;
        this.gmReceived = false;
        this.active = true;
    }


    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }


    public void startClient(String uiStyle,Socket socket) {

        //Create CLI
        if(uiStyle.toUpperCase().equals("CLI") || uiStyle.isBlank()) {
            ui = new CLI();
        }

        //Create GUI
        if(uiStyle.toUpperCase().equals("GUI")) {
        }

        this.socket = socket;

        try {
            //Set I/O streams
            this.socketIn = new ObjectInputStream(socket.getInputStream());
            this.socketOut = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        ui.showMessage(Messages.connectionReady);
    }


    public Thread asyncReadFromSocket(ObjectInputStream socketIn) {

        Thread t = new Thread(() -> {

            try {
                while(isActive()) {
                    Object inputObject = socketIn.readObject();

                    if(inputObject instanceof String) {
                        ui.showMessage((String)inputObject);
                        if(inputObject.equals(Messages.gameOver)) {
                            setActive(false);
                        }

                    } else if (inputObject instanceof BoardView) {
                        ui.showBoard((BoardView)inputObject);

                    } else if(inputObject instanceof String[]) {
                        for(String s : (String[])inputObject) {
                            ui.showMessage(s);
                        }
                    } else if(inputObject instanceof ArrayList){  /* currentGodList */
                        ui.showList((ArrayList<String>) inputObject);

                    } else if(inputObject instanceof Operation){
                        opReceived = true;  /* ricevuto un Operation */
                        Operation operation = (Operation)inputObject;
                        if(operation.getType() == 0){
                            ui.showMessage(Messages.Place + Messages.Operation);
                        }else  if(operation.getType() == 1){
                            ui.showMessage(Messages.Move + Messages.Operation);
                        }else{
                            ui.showMessage(Messages.Build + Messages.Operation);
                        }
                    }else if(inputObject instanceof GameMessage){
                        gmReceived = true;  /* ricevuto un GameMessage */
                        gMessage = (GameMessage)inputObject;
                        ui.showMessage(gMessage.getMessage());
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (Exception e) {
                setActive(false);
            }
        });

        t.start();
        return t;
    }


    public Thread cliWriteToSocket(final PrintWriter socketOut) {

        Thread t = new Thread(() -> {

            try {
                while(isActive()) {
                    String input = ui.getInput();
                    sendInput(input);
                }

            } catch(Exception e) {
                setActive(false);
            }
        });

        t.start();
        return t;
    }

    public void setUi(GUI gui) {
        this.ui = gui;
    }

    @Override
    public void run() {
        boolean cli = false;

        try {
            t0 = asyncReadFromSocket(socketIn);
            if(ui instanceof CLI) {
                cli = true;
                t1 = cliWriteToSocket(socketOut);
            }
            t0.join();

            if(cli) {
                if(isActive()) {
                    t1.join();
                } else {
                    t1.interrupt();
                }
            }

        } catch (Exception e) {
            ui.showMessage(Messages.connectionClosed);

        } finally{
            try {
                socketOut.close();
                socketIn.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendInput(String input) {

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

                    ui.showMessage(Messages.tryAgain + "\n" + Messages.Operation);
                    //ui.showMessage(Messages.Operation);
                }
            } catch (Exception e) {
                ui.showMessage(Messages.wrongArgument);
            }

        }else if (gmReceived) {  /* Risposta al messaggio */

            input = input.toUpperCase();

            if (gMessage.getMessage().equals(Messages.Worker)) {
                try {  /* indice worker o 0 o 1 */
                    int index = Integer.parseInt(input);
                    if (index == 0 || index == 1) {
                        socketOut.println(input);
                        socketOut.flush();
                        gmReceived = false;
                        gMessage = null;
                    } else {
                        ui.showMessage(Messages.tryAgain + "\n" + gMessage.getMessage());
                        //ui.showMessage(gMessage.getMessage());
                    }
                } catch (IllegalArgumentException e) {
                        ui.showMessage(Messages.wrongArgument);
                }
            }else {  /* problema yes or no */
                socketOut.println(input);
                socketOut.flush();
                gmReceived = false;
                gMessage = null;
            }
        } else {
            socketOut.println(input);
            socketOut.flush();
        }
    }
}


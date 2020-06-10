package it.polimi.ingsw.client;

import it.polimi.ingsw.model.GameMessage;
import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.cli.BoardView;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.gui.GuiHandler;
import it.polimi.ingsw.view.gui.GuiLauncher;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Client {

    private String ip;
    private int port;
    private Ui ui;
    private boolean active;
    private boolean opReceived;
    private boolean gmReceived;
    private GameMessage gMessage;

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


    public void startClient(String uiStyle,String inputIp,String inputPort) throws Exception {

        //Create CLI
        if(uiStyle.toUpperCase().equals("CLI") || uiStyle.isBlank()) {
            ui = new CLI();
        }

        //Create GUI
        if(uiStyle.toUpperCase().equals("GUI")) {
        }

        //Read socket's ip and port
        this.ip = inputIp;
        this.port = Integer.parseInt(inputPort);

        //Create socket
        Socket socket = new Socket(ip,port);

        //Set I/O streams
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());

        ui.showMessage(Messages.connectionReady);


        try {
            t0 = asyncReadFromSocket(socketIn);
            t1 = asyncWriteToSocket(socketOut);
            t0.join();
            if(isActive()) {
                t1.join();
            } else {
                t1.interrupt();
            }


        } catch (Exception e) {
            ui.showMessage(Messages.connectionClosed);

        } finally{
            socketOut.close();
            socketIn.close();
            socket.close();
        }

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
                        ArrayList nameList = (ArrayList) inputObject;
                        if(nameList.size() != 0 && nameList.get(0) instanceof String){
                            for (Object o : nameList){
                                String s = (String) o;
                                ui.showMessage(s);
                            }
                        }
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


    public Thread asyncWriteToSocket(final PrintWriter socketOut) {

        Thread t = new Thread(() -> {

            try {
                while(isActive()) {
                    String input = ui.getInput();


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

}

package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.GameMessage;
import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.cli.BoardView;
import it.polimi.ingsw.view.cli.CLI;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Client {

    private String ip;
    private int port;
    private Ui ui;
    private boolean active = true;
    private boolean opReceived;
    private boolean gmReceived;
    private GameMessage gMessage;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        opReceived = false;
        gmReceived = false;
    }


    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }


    public void startClient() throws IOException {

        //Create socket
        Socket socket = new Socket(ip,port);

        //Set I/O streams
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        //Choose between cli and gui
        System.out.println("Which interface do you want to use, CLI or GUI?");;
        String setUi;
        do {
            setUi = stdin.nextLine().toUpperCase();
        } while(!(setUi.equals("CLI") || setUi.equals("GUI")));

        if(setUi.equals("CLI")) {
            ui = new CLI();
        }
        if(setUi.equals("GUI")) {
            ui = new CLI();
            ui.showMessage("GUI not implemented yet, have fun with CLI ;)");
        }

        ui.showMessage(Messages.connectionReady);


        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(socketOut);
            t0.join();
            t1.join();

        } catch (NoSuchElementException | InterruptedException e) {
            ui.showMessage(Messages.connectionClosed);

        } finally{
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }

    }


    public Thread asyncReadFromSocket(ObjectInputStream socketIn) {

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
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
                            //operation = (Operation)inputObject;
                            System.out.println("mossa (x,y)");
                        }else if(inputObject instanceof GameMessage){
                            gmReceived = true;  /* ricevuto un GameMessage */
                            gMessage = (GameMessage)inputObject;
                            System.out.println(gMessage.getMessage());
                        }
                        else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e) {
                    setActive(false);
                }
            }
        });

        t.start();
        return t;
    }


    public Thread asyncWriteToSocket(final PrintWriter socketOut) {

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
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
                            } catch (IllegalArgumentException e) {
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
                            }else if((gMessage.getMessage().equals(Messages.Atlas)) && (input.equals("BLOCK") || input.equals("DOME"))
                            || (gMessage.getMessage().equals(Messages.Prometheus)) && (input.equals("MOVE")  || input.equals("BUILD"))
                            || (input.equals("YES") || input.equals("NO"))){  /* problema yes or no */
                                    socketOut.println(input);
                                    socketOut.flush();
                                    gmReceived = false;
                                    gMessage = null;
                                }
                                /*
                            }else if(gMessage.getMessage().equals(Messages.Atlas)) {
                                if(input.equals("BLOCK") || input.equals("DOME")){
                                    socketOut.println(input);
                                    socketOut.flush();
                                    gmReceived = false;
                                    gMessage = null;
                                }else {
                                    System.out.println("Riprova!\n" + gMessage.getMessage());
                                }
                            }else if(gMessage.getMessage().equals(Messages.Prometheus)){
                                if(input.equals("BLOCK") || input.equals("DOME")){
                                    socketOut.println(input);
                                    socketOut.flush();
                                    gmReceived = false;
                                    gMessage = null;
                                }else {
                                    System.out.println("Riprova!\n" + gMessage.getMessage());
                                }
                            }else if(input.equals("YES") || input.equals("NO")){
                                socketOut.println(input);
                                socketOut.flush();
                                gmReceived = false;
                                gMessage = null;
                            }
                            */
                            else {
                                ui.showMessage(Messages.tryAgain + "\n" + gMessage.getMessage());
                                //ui.showMessage(gMessage.getMessage());
                            }
                        } else {
                            socketOut.println(input);
                            socketOut.flush();
                        }
                    }
                } catch(Exception e) {
                    setActive(false);
                }
            }
        });

        t.start();
        return t;
    }

}

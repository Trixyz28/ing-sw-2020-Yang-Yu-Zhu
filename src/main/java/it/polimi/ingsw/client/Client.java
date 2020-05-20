package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.GameMessage;
import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Client {

    private String ip;
    private int port;
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
        System.out.println("Connection established");

        //Set scanners and printers
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        System.out.println("Scanners and printers ready");

        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin,socketOut);
            t0.join();
            t1.join();

        } catch (NoSuchElementException | InterruptedException e) {
            System.out.println("Connection closed");

        } finally{
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }

    }


    public Thread asyncReadFromSocket(final ObjectInputStream socketIn) {

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while(isActive()) {
                        Object inputObject = socketIn.readObject();

                        if(inputObject instanceof String) {
                            System.out.println((String)inputObject);

                        } else if (inputObject instanceof Board) {
                            System.out.println("Print map");

                        } else if(inputObject instanceof String[]) {
                            for(String s : (String[])inputObject) {
                                System.out.println(s);
                            }
                        } else if(inputObject instanceof ArrayList){  /* currentGodList */
                            ArrayList godList = (ArrayList) inputObject;
                            if(godList.size() != 0 && godList.get(0) instanceof String){
                                for (Object o : godList){
                                    String s = (String) o;
                                    System.out.println(s);
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


    public Thread asyncWriteToSocket(final Scanner stdin,final PrintWriter socketOut) {

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while(isActive()) {
                        String input = stdin.nextLine();
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
                                    System.out.println("Riprova!\nmossa (x,y)");
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println("Inserimento invalido");
                            }

                        }else if (gmReceived) {  /* Risposta al messaggio */

                             input = input.toUpperCase();

                            if(gMessage.getMessage().equals(Messages.Worker)){
                                try {  /* indice worker o 0 o 1 */
                                    int index = Integer.parseInt(input);
                                    if (index == 0 || index == 1) {
                                        socketOut.println(input);
                                        socketOut.flush();
                                        gmReceived = false;
                                        gMessage = null;
                                    }else {
                                        System.out.println("Riprova!\n" + gMessage.getMessage());
                                    }
                                }catch(IllegalArgumentException e){
                                    System.out.println("Inserimento invalido");
                                }
                            }else if(gMessage.getMessage().equals(Messages.Atlas)){
                                if(input.equals("BLOCK") || input.equals("DOME")){
                                    socketOut.println(input);
                                    socketOut.flush();
                                    gmReceived = false;
                                    gMessage = null;
                                }else {
                                    System.out.println("Riprova!\n" + gMessage.getMessage());
                                }
                            }else if(gMessage.getMessage().equals(Messages.Prometheus)){
                                if(input.equals("MOVE")  || input.equals("BUILD")){
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
                            }else {
                                System.out.println("Riprova!\n" + gMessage.getMessage());
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

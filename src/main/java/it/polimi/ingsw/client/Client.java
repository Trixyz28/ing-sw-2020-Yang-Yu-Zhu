package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Board;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Client {

    private String ip;
    private int port;
    private boolean active = true;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
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
                        socketOut.println(input);
                        socketOut.flush();
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

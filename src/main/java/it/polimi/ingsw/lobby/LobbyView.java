package it.polimi.ingsw.lobby;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;

import java.util.ArrayList;
import java.util.Scanner;


public class LobbyView extends Observable implements Observer {

    private Scanner scanner;
    private boolean end;

    public LobbyView() {
        scanner = new Scanner(System.in);
    }

    //Show all players who join this server session
    @Override
    public void update(Object message) {
        if(message instanceof ArrayList) {
            ArrayList<String> playerName = (ArrayList<String>)message;

            System.out.println("Number of players in game: " + playerName.size());

            if(playerName.size()!=0) {
                System.out.println("Current players are:");
                for(String s : playerName) {
                    System.out.println(s);
                }
            }
        }


    }


    public void run() {
        System.out.println("Welcome to Santorini!");
        notify("setup");
    }

    //Read the nickname of the player from input
    public String readNickname() {
        System.out.println("What's your nickname?");
        return scanner.next();
    }

    //The first player (creator) chooses the number of players in the lobby
    public int readPlayerNumber(){

        int num;

        System.out.println("You can create a lobby");
        System.out.println("How many players can join this match? (2-3)");
        num = scanner.nextInt();
        while(num<2 || num>3) {
            System.out.println("Insert 2 or 3");
            num = scanner.nextInt();
        }

        return num;
    }


}

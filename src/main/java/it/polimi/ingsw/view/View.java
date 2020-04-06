package it.polimi.ingsw.view;

import it.polimi.ingsw.Observable;
import it.polimi.ingsw.Observer;

import java.io.PrintStream;
import java.util.Scanner;


public class View extends Observable implements Observer, Runnable {

    private Scanner scanner;
    private PrintStream outputStream;
    private boolean endGame = false;

    public View(){
        scanner = new Scanner(System.in);
        outputStream = new PrintStream(System.out);
    }


    @Override
    public void update(Object message) {

    }

    @Override
    public void run() {
        while(!endGame) {



        }
    }
}

package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.cli.BoardView;
import it.polimi.ingsw.view.gui.controllers.LoadingController;
import it.polimi.ingsw.view.gui.controllers.StartController;

import java.io.*;
import java.util.Scanner;

public class GUI implements Ui {

    private String playerName;
    private String input = new String();
    private boolean waitingMsg = false;
    private Scanner scanner = new Scanner(System.in);


    public GUI() {

    }


    @Override
    public void showMessage(String str) {
        System.out.println(str);
    }


    @Override
    public void showBoard(BoardView boardView) {

    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }


    public void setInput(String str) {
        input = str;
        waitingMsg = true;
    }



    public void print() {
        System.out.println("print");
    }

}

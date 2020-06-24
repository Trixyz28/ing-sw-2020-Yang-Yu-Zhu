package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.BoardView;

import java.util.ArrayList;
import java.util.Scanner;


public class GUI extends Observable implements Ui {

    private Scanner scanner = new Scanner(System.in);


    @Override
    public void showMessage(String str) {
        System.out.println("Received in gui: " + str);
        notify(str);
    }


    @Override
    public void showBoard(BoardView boardView,boolean command) {
        notify(boardView);
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }

    @Override
    public void showGameMsg(GameMessage message) {
        notify(message);
    }

    @Override
    public void showObj(Obj message) {
        notify(message);
    }

}

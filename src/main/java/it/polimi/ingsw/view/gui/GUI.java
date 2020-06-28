package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.view.Ui;
import java.util.Scanner;


public class GUI extends Observable implements Ui {

    private Scanner scanner = new Scanner(System.in);


    @Override
    public void showMessage(String str) {
        System.out.println("Received in gui: " + str);
        notify(str);
    }


    @Override
    public void showObj(Obj message) {
        notify(message);
    }

}

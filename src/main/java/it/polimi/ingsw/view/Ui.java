package it.polimi.ingsw.view;

import it.polimi.ingsw.view.cli.BoardView;

import java.util.ArrayList;
import java.util.Scanner;


public interface Ui {

    //Display a message
    void showMessage(String str);

    //Display the game board
    void showBoard(BoardView boardView);

    //Receive a message in input
    String getInput();



}

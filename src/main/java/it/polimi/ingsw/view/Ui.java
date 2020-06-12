package it.polimi.ingsw.view;

import java.util.ArrayList;


public interface Ui {

    //Display a message
    void showMessage(String str);

    //Display the game board
    void showBoard(BoardView boardView);

    //Receive a message in input
    String getInput();

    void showList(ArrayList<String> list);



}

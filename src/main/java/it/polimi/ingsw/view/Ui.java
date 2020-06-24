package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.*;

import java.util.ArrayList;


public interface Ui {

    //Display a message
    void showMessage(String str);

    //Display the game board
    void showBoard(BoardView boardView,boolean command);

    //Receive a message in input
    String getInput();


    void showGameMsg(GameMessage message);

    void showObj(Obj message);


}

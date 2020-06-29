package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.*;

import java.util.ArrayList;


public interface Ui {

    void showObj(Obj message);

    //Display a message
    void showMessage(String str);

    void handlePlayerList(ArrayList<String> list);

    void handleTurn(String message);

    void handleDefineGod(String message);

    void handleChooseGod(Obj obj);

    void updateBoard(BoardView boardView);

    void handleBoardMsg(String message);

    void handleGameMsg(String message);

    void endGame(Obj obj);
}

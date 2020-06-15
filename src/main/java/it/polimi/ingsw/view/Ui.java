package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.messages.GodChosenMessage;
import it.polimi.ingsw.messages.LobbyMessage;
import it.polimi.ingsw.messages.TurnMessage;

import java.util.ArrayList;


public interface Ui {

    //Display a message
    void showMessage(String str);

    //Display the game board
    void showBoard(BoardView boardView,boolean command);

    //Receive a message in input
    String getInput();

    void showList(ArrayList<String> list);


    void showGodChosen(GodChosenMessage message);

    void showLobbyMsg(LobbyMessage message);

    void showTurnMsg(TurnMessage message);

    void showGameMsg(GameMessage message);



}

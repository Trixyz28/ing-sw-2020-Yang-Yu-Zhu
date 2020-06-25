package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.*;

import java.util.ArrayList;


public interface Ui {



    //Receive a message in input
    String getInput();


    void showObj(Obj message);

    //Display a message
    void showMessage(String str);

}

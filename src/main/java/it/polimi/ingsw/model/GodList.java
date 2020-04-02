package it.polimi.ingsw.model;

import java.util.ArrayList;

public class GodList {

    //List with all Gods
    private String[] completeGodList;



    //List with Gods selected by the Challenger
    private ArrayList<String> currentGodList;

    //Current choice of God
    private String selectedGod;

    //currentGodList length
    private int listLength;

    //Number of players in match
    private int playerNumber;



    //Initializing completeGodList
    public String[] setComplete(){

        return completeGodList;
    }


    public ArrayList<String> getCurrentGodList() {
        return currentGodList;
    }


    //Print completeGodList
    public void showComplete() {

    }


    //Select a god to add to currentGodList
    public void selectGod() {

    }

    //Add selectedGod to currentGodList & listLength+1
    public ArrayList<String> addInGodList(String selectedGod) {

        return currentGodList;
    }


    //Get the dimension of currentGodList -> listLength
    public void getLength() {

    }

    //Remove a God from currentGodList & listLength-1
    public ArrayList<String> removeFromGodList(String selectedGod) {

        return currentGodList;
    }

    //Refresh listLength after add/remove
    public void setLength() {

    }



}

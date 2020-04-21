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


    public GodList (int playerNumber){
        completeGodList = new String[9];
        setComplete();
        currentGodList = new ArrayList<>();
        listLength = 0;
        this.playerNumber = playerNumber;
    }

    //Initializing completeGodList
    public void setComplete(){
        completeGodList[0] = "APOLLO";
        completeGodList[1] = "ARTEMIS";
        completeGodList[2] = "ATHENA";
        completeGodList[3] = "ATLAS";
        completeGodList[4] = "DEMETER";
        completeGodList[5] = "HEPHAESTUS";
        completeGodList[6] = "MINOTAUR";
        completeGodList[7] = "PAN";
        completeGodList[8] = "PROMETHEUS";
    }


    public ArrayList<String> getCurrentGodList() {
        return currentGodList;
    }


    //Print completeGodList
    public String[] showComplete() {  /* notificare la view dal model */
        return completeGodList;
        /*
        for(String s : completeGodList){
            System.out.println(s);
        }
        */
    }


    //Select a god to add to currentGodList (manca ancora il check per la decisione)
    public void selectGod(String selectedGod) {
        this.selectedGod = selectedGod;
    }

    //Add selectedGod to currentGodList & listLength+1
    public void addInGodList() {
        if(!checkGod()) {
            currentGodList.add(selectedGod);
            listLength++;
        }
    }
    //controllare se il god scelto sia già presente in currentGodList -> true se esiste già
    public boolean checkGod(){
        for(String s : currentGodList){
            if(s.equals(selectedGod)){
                return true;
            }
        }
        return false;
    }


    //Get the dimension of currentGodList -> listLength
    public int getLength() {
        return listLength;
    }

    //Remove a God from currentGodList & listLength-1
    public void removeFromGodList(String selectedGod) {
        for(String s : currentGodList){
            if(s.equals(selectedGod)){
                currentGodList.remove(s);
                listLength--;
                break;
            }
        }
    }

    //Check the dimension of currentGodList == playerNumber
    public boolean checkLength(){
        return listLength==playerNumber;
    }




}

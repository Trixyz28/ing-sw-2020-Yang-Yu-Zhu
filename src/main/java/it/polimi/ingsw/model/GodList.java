package it.polimi.ingsw.model;

import java.util.ArrayList;

public class GodList {

    //List of all Gods
    private String[] completeGodList;

    //List of Gods selected by the Challenger
    private ArrayList<String> currentGodList;

    //Current choice of God
    private String selectedGod;

    //currentGodList length
    private int listLength;

    //Number of players in match
    private int playerNumber;


    public GodList (int playerNumber){
        completeGodList = new String[14];
        setComplete();
        currentGodList = new ArrayList<>();
        listLength = 0;
        this.playerNumber = playerNumber;
    }


    //Initializing completeGodList
    private void setComplete(){
        completeGodList[0] = "APOLLO";
        completeGodList[1] = "ARTEMIS";
        completeGodList[2] = "ATHENA";
        completeGodList[3] = "ATLAS";
        completeGodList[4] = "DEMETER";
        completeGodList[5] = "HEPHAESTUS";
        completeGodList[6] = "HESTIA";
        completeGodList[7] = "MINOTAUR";
        completeGodList[8] = "PAN";
        completeGodList[9] = "POSEIDON";
        completeGodList[10] = "PROMETHEUS";
        completeGodList[11] = "ZEUS";
    }


    //CurrentGodList getter
    public ArrayList<String> getCurrentGodList() {
        return currentGodList;
    }


    //CompleteGodList getter
    public String[] getCompleteGodList() {
        return completeGodList;
    }


    //Select a god to add to currentGodList (manca ancora il check per la decisione)
    public void selectGod(String selectedGod) {
        this.selectedGod = selectedGod.toUpperCase();
    }


    //Add selectedGod to currentGodList & listLength+1
    public boolean addInGodList() {  /* true: andato a buon fine   false: non aggiunto */
        if(!checkGod()) {
            boolean flag = false;
            for(String god : completeGodList){  /* controllare se il selectedGod sia un God */
                if(selectedGod.equals(god)){
                    currentGodList.add(selectedGod);
                    listLength++;
                    return true;
                }
            }
        }
        return false;
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


    //Remove a God from currentGodList & listLength-1
    public void removeFromGodList(String selectedGod) {
        for(String s : currentGodList){
            if(s.equals(selectedGod)) {
                currentGodList.remove(s);
                break;
            }
        }
    }


    //Check the dimension of currentGodList == playerNumber
    public boolean checkLength(){
        return listLength==playerNumber;
    }


}

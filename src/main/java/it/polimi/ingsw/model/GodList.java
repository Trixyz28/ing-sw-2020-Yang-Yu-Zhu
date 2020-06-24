package it.polimi.ingsw.model;

import java.util.ArrayList;

public class GodList {

    //List of all Gods
    private ArrayList<String> completeGodList;

    //List of Gods selected by the Challenger
    private ArrayList<String> currentGodList;

    //Current choice of God
    private String selectedGod;

    //currentGodList length
    private int currentLength;

    //Number of players in match
    private int playerNumber;


    public GodList (int playerNumber){
        completeGodList = new ArrayList<>();
        setComplete();
        currentGodList = new ArrayList<>();
        currentLength = 0;
        this.playerNumber = playerNumber;
    }


    //Initializing completeGodList
    private void setComplete(){
        completeGodList.add("APOLLO");
        completeGodList.add("ARTEMIS");
        completeGodList.add("ATHENA");
        completeGodList.add("ATLAS");
        completeGodList.add("DEMETER");
        completeGodList.add("HEPHAESTUS");
        completeGodList.add("HERA");
        completeGodList.add("HESTIA");
        completeGodList.add("LIMUS");
        completeGodList.add("MINOTAUR");
        completeGodList.add("PAN");
        completeGodList.add("POSEIDON");
        completeGodList.add("PROMETHEUS");
        completeGodList.add("TRITON");
        completeGodList.add("ZEUS");
    }


    //CurrentGodList getter
    public ArrayList<String> getCurrentGodList() {
        return currentGodList;
    }


    //CompleteGodList getter
    public ArrayList<String> getCompleteGodList() {
        return completeGodList;
    }


    //Select a god to add to currentGodList (manca ancora il check per la decisione)
    public void selectGod(String selectedGod) {
        this.selectedGod = selectedGod.toUpperCase();
    }


    //Add selectedGod to currentGodList & listLength+1
    public boolean addInGodList() {  /* true: andato a buon fine   false: non aggiunto */
        if(!checkGod()) {
            /* controllare se il selectedGod sia un God */
            for(String god : completeGodList){
                if(selectedGod.equals(god)){
                    currentGodList.add(selectedGod);
                    currentLength++;
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
        return currentLength == playerNumber;
    }


}

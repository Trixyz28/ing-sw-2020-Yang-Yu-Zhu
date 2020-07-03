package it.polimi.ingsw.model;

import java.util.ArrayList;
/**
 * Class that is used to handle the GodList used to initialize the match in the lobby.
 *<p></p>
 * This class is used to create a GodList object which is fundamental to initialize the match itself to sort the God Powers
 * throughout the players.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
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

    /**
     * Creates a new <code>GodList</code> with the specified attributes.
     * @param playerNumber Variable that indicates the number of the players of the lobby.
     */
    public GodList (int playerNumber){
        completeGodList = new ArrayList<>();
        setComplete();
        currentGodList = new ArrayList<>();
        currentLength = 0;
        this.playerNumber = playerNumber;
    }


    //Initializing completeGodList

    /**
     *Create a complete GodList with all the Gods.
     */
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

    /**
     *Gets the current GodList available.
     * @return An arraylist of strings which are the Gods' names.
     */
    public ArrayList<String> getCurrentGodList() {
        return currentGodList;
    }


    //CompleteGodList getter

    /**
     *Gets the complete GodList available.
     * @return An arraylist of strings which are the Gods' name.
     */
    public ArrayList<String> getCompleteGodList() {
        return completeGodList;
    }


    //Select a god to add to currentGodList

    /**
     *Sets the parameter in selectedGod after changing it to upperCase.
     * @param selectedGod Variable which is a string of the God's name.
     */
    public void selectGod(String selectedGod) {
        this.selectedGod = selectedGod.toUpperCase();
    }


    //Add selectedGod to currentGodList & listLength+1

    /**
     *Adds the selectedGod to the GodList in the making.
     * @return A boolean: <code>true</code> if successful, otherwise <code>false</code>.
     */
    public boolean addInGodList() {
        if(!checkGod()) {
            /* check if selectedGod is a God*/
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

    /**
     *Checks if the selectedGod is already in the GodList.
     * @return A boolean: <code>true</code>  if it exists already, otherwise <code>false</code>.
     */
    public boolean checkGod(){
        for(String s : currentGodList){
            if(s.equals(selectedGod)){
                return true;
            }
        }
        return false;
    }


    //Removes a God from currentGodList & listLength-1

    /**
     *Removes the parameter from the GodList after it's use.
     * @param selectedGod Variable which is a string of the God's name.
     */
    public void removeFromGodList(String selectedGod) {
        for(String s : currentGodList){
            if(s.equals(selectedGod)) {
                currentGodList.remove(s);
                break;
            }
        }
    }


    //Check the dimension of currentGodList == playerNumber

    /**
     *Checks if the dimension of the list is equal to the number of players.
     * @return A boolean: <code>true</code> if the lenght of the list is the same as players' number, otherwise <code>false</code>.
     */
    public boolean checkLength(){
        return currentLength == playerNumber;
    }


}

package it.polimi.ingsw.model.God;

//public class with conditions of worker like Athenas with attribute booleans/set/get to check
//ATTENZIONE: potrebbe essere messo in Match come regola generale, pensare alla scalabilità in caso di aggiunta di divinità.
// in tal caso aggiungere metodo per creazione oggetto di classe Conditions

public class Conditions {

 //if true in currentTurn worker cant move up
    private boolean athenaRule = false;


    public boolean getAthenaRule() {
        return counter;
    }


    public void setAthenaRule(boolean i) {
        this.athenaRule = i;
    }

}
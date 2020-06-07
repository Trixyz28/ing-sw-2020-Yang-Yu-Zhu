package it.polimi.ingsw.model;

public enum GodPowerMessage {
    ARTEMIS ("Arthemis: Move again?"),
    ATLAS ("Atlas: Block or Dome?", "DOME", "BLOCK"),
    DEMETER ("Demeter: Build again?"),
    HEPHAESTUS ("Hephaestus: Another Block?"),
    PROMETHEUS ("Prometheus: Move or Build", "BUILD", "MOVE"),
    HESTIA ( "Hestia: Build again?"),
    POSEIDON ("Poseidon: Unmove worker Build?"),
    TRITON ("Triton: Move again?");

    private String message;

    private String answer1;

    private String answer2;

    GodPowerMessage(String message){
        this.message = message;
        this.answer1 = "YES";
        this.answer2 = "NO";
    }

    GodPowerMessage(String message, String answer1, String answer2){
        this.message = message;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    public String getMessage(){
        return message;
    }

    public int checkAnswer(String answer){
        if(answer.equals(answer1)){
            return 1;
        }
        if (answer.equals(answer2)){
            return 2;
        }
        return 0;
    }


}

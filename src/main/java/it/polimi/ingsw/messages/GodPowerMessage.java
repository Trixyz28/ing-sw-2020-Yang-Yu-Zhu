package it.polimi.ingsw.messages;


public enum GodPowerMessage {

    ARTEMIS ("Move again?"),
    ATLAS ("Block or Dome?", "DOME", "BLOCK"),
    DEMETER ("Build again?"),
    HEPHAESTUS ("Build another block?"),
    PROMETHEUS ("Move or Build?", "BUILD", "MOVE"),
    HESTIA ("Build again?"),
    POSEIDON ("Build with the unmoved worker?"),
    TRITON ("Move again?");

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

    public String getAnswer1() {
        return this.answer1;
    }

    public String getAnswer2() {
        return this.answer2;
    }


}

package it.polimi.ingsw.messages;

public enum GodPowerMessage {
    ARTEMIS ("Artemis: Move again? (Yes/No)"),
    ATLAS ("Atlas: Block or Dome?", "DOME", "BLOCK"),
    DEMETER ("Demeter: Build again? (Yes/No)"),
    HEPHAESTUS ("Hephaestus: Want to build another block? (Yes/No)"),
    PROMETHEUS ("Prometheus: Move or Build?", "BUILD", "MOVE"),
    HESTIA ("Hestia: Build again? (Yes/No)"),
    POSEIDON ("Poseidon: Want to build with the unmoved worker? (Yes/No)"),
    TRITON ("Triton: Move again? (Yes/No)");

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

package it.polimi.ingsw.model;

public enum GodPowerMessage {
    ARTEMIS ("Arthemis: Move again?"),
    ATLAS ("Atlas: Block or Dome?"),
    DEMETER ("Demeter: Build again?"),
    HEPHAESTUS ("Hephaestus: Another Block?"),
    PROMETHEUS ("Prometheus: Move or Build"),
    HESTIA ( "Hestia: Build again?"),
    POSEIDON ("Poseidon: Unmove worker Build?"),
    TRITON ("Triton: Move again?");

    private String message;

    GodPowerMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }


}

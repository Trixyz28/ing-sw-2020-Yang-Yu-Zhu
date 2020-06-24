package it.polimi.ingsw.messages;

import java.io.Serializable;
import java.util.ArrayList;


public class Obj implements Serializable {

    private final String classifier;
    private String receiver;

    private String player;
    private String message;
    private ArrayList<String> list;

    private boolean broadcast = true;


    public Obj(String classifier,String message) {
        this.classifier = classifier;
        this.message = message;
    }

    public Obj(String classifier,String message,String player) {
        this.classifier = classifier;
        this.message = message;
        this.player = player;
    }

    public Obj(String classifier,ArrayList<String> list) {
        this.classifier = classifier;
        this.list = list;
    }


    public String getClassifier() {
        return classifier;
    }

    public String getMessage() {
        return message;
    }

    public String getPlayer() {
        return player;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getReceiver() {
        return receiver;
    }

    public boolean isBroadcast() {
        return broadcast;
    }

}

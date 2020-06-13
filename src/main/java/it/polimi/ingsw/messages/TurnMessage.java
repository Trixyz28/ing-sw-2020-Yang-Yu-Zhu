package it.polimi.ingsw.messages;

import java.io.Serializable;


public class TurnMessage implements Serializable {

    //source = "god"/"board"
    private String source;
    private String name;

    public TurnMessage(String source,String name) {
        this.source = source;
        this.name = name;
    }


    public String getSource() {
        return source;
    }

    public String getName() {
        return name;
    }

}

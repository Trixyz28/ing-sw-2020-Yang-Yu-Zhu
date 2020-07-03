package it.polimi.ingsw.messages;

import java.io.Serializable;

/**
 * Class GameMessage used by the implementation between client and server.
 * <p></p>
 * This class needs a specific type of answer.
 * <p></p>
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class GameMessage implements Serializable {


    private final String message;
    private String answer;

    /**
     *Creates a <code>GameMessage</code> with the specified attributes.
     * @param message Variable that represents the message that is sent.
     */
    public GameMessage(final String message){
        this.message = message;
    }

    /**
     * Gets the message encapsulated in the GameMessage object.
     * @return The message that was sent.
     */
    //Message getter
    public String getMessage(){
        return message;
    }

    /**
     * Gets the answer to the message encapsulated in the GameMessage object.
     * @return The answer that was sent.
     */
    //Answer getter&setter
    public String getAnswer(){
        return answer;
    }

    /**
     * Sets a string as an answer encapsulating it in the GameMessage object.
     * @param answer The string that is the answer to the message sent.
     */
    public void setAnswer(String answer){
        this.answer = answer;
    }

}

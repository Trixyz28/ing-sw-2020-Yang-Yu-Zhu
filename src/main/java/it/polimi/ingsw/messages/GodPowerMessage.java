package it.polimi.ingsw.messages;

/**
 * Class GodPowerMessage that is used by the implementation between clients and server for God's powers.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public enum GodPowerMessage {

    ARTEMIS ("Move again?"),
    ATLAS ("Block or Dome?", "DOME", "BLOCK"),
    DEMETER ("Build again?"),
    HEPHAESTUS ("Build another block?"),
    PROMETHEUS ("Move or Build?", "BUILD", "MOVE"),
    HESTIA ("Build again?"),
    POSEIDON ("Build with unmoved worker?"),
    TRITON ("Move again?");

    private String message;
    private String answer1;
    private String answer2;

    /**
     * Creates a <code>GodPowerMessage</code> with the specified attributes.
     * @param message Variable that represents a message sent to the player.
     *                The message only accepts answers equals to answer1 or answer2.
     */
    GodPowerMessage(String message){
        this.message = message;
        this.answer1 = "YES";
        this.answer2 = "NO";
    }

    /**
     *Creates a <code>GodPowerMessage</code> with the specified attributes.
     * @param message Variable that represents a message sent to the player.
     * @param answer1 Variable that represents one answer accepted from the player.
     * @param answer2 Variable that represents another answer accepted from the player.
     */
    GodPowerMessage(String message, String answer1, String answer2){
        this.message = message;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    /**
     *Gets the <code>message</code> in the <code>GodPowerMessage</code>.
     * @return The message that was encapsulated in the message.
     */
    public String getMessage(){
        return message;
    }

    /**
     *Checks which answer is chosen.
     * @param answer The variable that indicates the answer that was chosen.
     * @return 0 if the answer chosen wasn't in GodPowerMessage. 1 if the answer chosen was in <code>answer1</code>.
     *         2 if the answer chosen was in <code>answer2</code>.
     */
    public int checkAnswer(String answer){
        if(answer.equals(answer1)){
            return 1;
        }
        if (answer.equals(answer2)){
            return 2;
        }
        return 0;
    }

    /**
     * Gets the <code>answer1</code> in the <code>GodPowerMessage</code>.
     * @return The string in <code>answer1</code> encapsulated in the message.
     */
    public String getAnswer1() {
        return this.answer1;
    }

    /**
     * Gets the <code>answer2</code> in the <code>GodPowerMessage</code>.
     * @return The string in <code>answer2</code> encapsulated in the message.
     */
    public String getAnswer2() {
        return this.answer2;
    }


}

package it.polimi.ingsw.view.gui.controllers;


import it.polimi.ingsw.view.Sender;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ArrayList;

/**
 * Class that implements the LoadingController of the GUI of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class LoadingController {


    private Sender sender;

    //FXML Nickname

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label nameRecv;
    @FXML
    private TextField nameField;
    @FXML
    private Button nameButton;

    private String nickname;

    /**
     * Sends the chosen nickname to the server.
     */
    public void sendName() {
        nickname = nameField.getCharacters().toString();
        sender.sendInput(nickname);
    }

    /**
     *Shows the name message received from the server.
     * @param str Variable that represents the message form the server.
     */
    public void showNameMsg(String str) {
        nameRecv.setText(str);
    }

    /**
     *Gets the current nickname set for the player with the GUI.
     * @return Variable that represents the player's nickname.
     */
    public String getNickname() {
        return nickname;
    }



    //FXML Lobby

    @FXML
    private Label lobbyRecv;
    @FXML
    private Label waiting;
    @FXML
    private ChoiceBox<String> playerNumberSelect;
    @FXML
    private Button lobbyButton;

    /**
     *Creates a clickable choice box for the player who has the GUI.
     */
    public void setChoiceBox() {
        ArrayList<String> list = new ArrayList<>();
        list.add("2 players");
        list.add("3 players");
        playerNumberSelect.setItems(FXCollections.observableArrayList(list));
        playerNumberSelect.setStyle("-fx-font: 14px \"Bell MT\";");
    }

    /**
     *Sends the choice of the type of lobby to the server.
     * @param event Variable that represents the event the player did: click on the choice button of the lobby.
     */
    public void sendLobby(ActionEvent event) {
        if(playerNumberSelect.getValue().equals("3 players")) {
            sender.sendInput("3");
        } else {
            sender.sendInput("2");
        }
    }

    /**
     *Shows the player that he is in a lobby and is waiting for the other players to join.
     * @param str Variable that is the string that needs to be shown.
     */
    public void inLobby(String str) {
        lobbyRecv.setText(str);
        playerNumberSelect.setDisable(true);
        playerNumberSelect.setVisible(false);
        lobbyButton.setDisable(true);
        lobbyButton.setVisible(false);
        waiting.setText("Waiting for other players");
    }

    /**
     *Sets the Sender of the GUI messages.
     * @param sender Variable that is an <code>Sender</code> used for the client-GUI and server interactions.
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }


}

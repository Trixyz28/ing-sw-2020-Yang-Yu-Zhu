package it.polimi.ingsw.view.gui.controllers;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ArrayList;


public class LoadingController extends Commuter {


    @FXML
    private Label nameRecv;

    @FXML
    private TextField nameField;

    @FXML
    private Button nameButton;

    private String nickname;


    public void sendName(ActionEvent event) {
        nickname = nameField.getCharacters().toString();
        super.getGuiLauncher().getClient().sendInput(nickname);
    }

    public void showNameMsg(String str) {
        nameRecv.setText(str);
    }

    public String getNickname() {
        return nickname;
    }




    @FXML
    private Label lobbyRecv;

    @FXML
    private Label waiting;

    @FXML
    private ChoiceBox<String> playerNumberSelect;

    @FXML
    private Button lobbyButton;


    public void setChoiceBox() {
        ArrayList<String> list = new ArrayList<>();
        list.add("2 players");
        list.add("3 players");
        playerNumberSelect.setItems(FXCollections.observableArrayList(list));
        playerNumberSelect.setStyle("-fx-font: 14px \"Bell MT\";");
    }

    public void sendLobby(ActionEvent event) {
        if(playerNumberSelect.getValue().equals("3 players")) {
            super.getGuiLauncher().getClient().sendInput("3");
        } else {
            super.getGuiLauncher().getClient().sendInput("2");
        }
    }


    public void createdLobby(int number) {
        inLobby();
        lobbyRecv.setText("Create the lobby n." + number);
    }

    public void joinedLobby(int number) {
        inLobby();
        lobbyRecv.setText("Successfully join the lobby n." + number);
    }

    public void inLobby() {
        playerNumberSelect.setDisable(true);
        playerNumberSelect.setVisible(false);
        lobbyButton.setDisable(true);
        lobbyButton.setVisible(false);
        waiting.setText("Waiting for other players");
    }














}

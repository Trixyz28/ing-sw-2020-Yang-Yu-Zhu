package it.polimi.ingsw.view.gui.controllers;


import it.polimi.ingsw.view.Sender;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.gui.GuiLauncher;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class LoadingController {


    private Sender sender;

    private GUI gui;


    //FXML Nickname
    
    @FXML
    private Label nameRecv;

    @FXML
    private TextField nameField;

    @FXML
    private Button nameButton;

    private String nickname;


    public void sendName() {
        nickname = nameField.getCharacters().toString();
        sender.sendInput(nickname);
    }

    public void showNameMsg(String str) {
        nameRecv.setText(str);
    }

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


    public void setChoiceBox() {
        ArrayList<String> list = new ArrayList<>();
        list.add("2 players");
        list.add("3 players");
        playerNumberSelect.setItems(FXCollections.observableArrayList(list));
        playerNumberSelect.setStyle("-fx-font: 14px \"Bell MT\";");
    }

    public void sendLobby(ActionEvent event) {
        if(playerNumberSelect.getValue().equals("3 players")) {
            sender.sendInput("3");
        } else {
            sender.sendInput("2");
        }
    }



    public void inLobby(String str) {
        lobbyRecv.setText(str);
        playerNumberSelect.setDisable(true);
        playerNumberSelect.setVisible(false);
        lobbyButton.setDisable(true);
        lobbyButton.setVisible(false);
        waiting.setText("Waiting for other players");
    }



    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

}

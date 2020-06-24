package it.polimi.ingsw.view.gui.controllers;


import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.gui.GuiLauncher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class LoadingController {

    private GuiLauncher guiLauncher;

    //FXML Start

    @FXML
    private Pane startPane;

    @FXML
    public void loadingScene(MouseEvent event) {
        guiLauncher.changeScene(1);
    }


    //FXML Server

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Label serverMsg;

    @FXML
    private Button serverButton;

    public void serverInfo(ActionEvent event) {
        guiLauncher.setIp(ipField.getCharacters().toString());
        guiLauncher.setPort(portField.getCharacters().toString());
        System.out.println("Server set to " + ipField.getCharacters().toString() + ", port " + portField.getCharacters().toString());
        guiLauncher.setServer();

    }

    public void showMessage(String str) {
        serverMsg.setText(str);
    }



    //FXML Nickname
    
    @FXML
    private Label nameRecv;

    @FXML
    private TextField nameField;

    @FXML
    private Button nameButton;

    private String nickname;


    public void sendName(ActionEvent event) {
        nickname = nameField.getCharacters().toString();
        guiLauncher.getClient().sendInput(nickname);
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
            guiLauncher.getClient().sendInput("3");
        } else {
            guiLauncher.getClient().sendInput("2");
        }
    }


    public void createdLobby(String number) {
        inLobby();
        lobbyRecv.setText("Create the lobby n." + number);
    }

    public void joinedLobby(String number) {
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

    public void setGuiLauncher(GuiLauncher guiLauncher) {
        this.guiLauncher = guiLauncher;
    }



}

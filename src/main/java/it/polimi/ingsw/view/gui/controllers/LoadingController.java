package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class LoadingController extends Commuter {


    @FXML
    private Label nameRecv;

    @FXML
    private TextField nameField;

    @FXML
    private Button nameButton;

    public void sendName(ActionEvent event) {
        System.out.println(nameField.getCharacters().toString());
        super.getGuiLauncher().getClient().sendInput(nameField.getCharacters().toString());
    }

    public void showNameMsg(String str) {
        nameRecv.setText(str);
    }




    @FXML
    private Label lobbyRecv;

    @FXML
    private ChoiceBox<String> playerNumberSelect;

    @FXML
    private Button lobbyButton;

    public void setChoiceBox() {
        ArrayList<String> list = new ArrayList<>();
        list.add("2 players");
        list.add("3 players");
        playerNumberSelect.setItems(FXCollections.observableArrayList(list));
    }

    public void sendLobby(ActionEvent event) {
        if(playerNumberSelect.getValue()==null || playerNumberSelect.getValue().equals("2 players")) {
            super.getGuiLauncher().getClient().sendInput("2");
        } else {
            super.getGuiLauncher().getClient().sendInput("3");
        }
        playerNumberSelect.setDisable(true);
        playerNumberSelect.setVisible(false);
        lobbyButton.setDisable(true);
        lobbyButton.setVisible(false);
        lobbyRecv.setText("Waiting for other players");
    }














}

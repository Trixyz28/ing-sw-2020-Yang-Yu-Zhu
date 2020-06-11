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
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Button serverButton;

    public void serverInfo(ActionEvent event) throws Exception {
        super.getGuiLauncher().setIp(ipField.getCharacters().toString());
        super.getGuiLauncher().setPort(portField.getCharacters().toString());
        System.out.println("Server set to " + ipField.getCharacters().toString() + ", port " + portField.getCharacters().toString());
        super.getGuiLauncher().changeScene(2);
    }


    @FXML
    private Label nameRecv;

    @FXML
    private TextField nameField;

    @FXML
    private Button nameButton;

    public void sendName(ActionEvent event) {
        System.out.println(nameField.getCharacters().toString());
    }

    public void getCommand(String str) {
        System.out.println(str);
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
            System.out.println(2);
        } else {
            System.out.println(3);
        }
    }














}

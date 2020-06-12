package it.polimi.ingsw.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ServerController extends Commuter {

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Label serverMsg;

    @FXML
    private Button serverButton;

    public void serverInfo(ActionEvent event) {
        super.getGuiLauncher().setIp(ipField.getCharacters().toString());
        super.getGuiLauncher().setPort(portField.getCharacters().toString());
        System.out.println("Server set to " + ipField.getCharacters().toString() + ", port " + portField.getCharacters().toString());
        super.getGuiLauncher().setServer();

    }

    public void showMessage(String str) {
        serverMsg.setText(str);
    }

}

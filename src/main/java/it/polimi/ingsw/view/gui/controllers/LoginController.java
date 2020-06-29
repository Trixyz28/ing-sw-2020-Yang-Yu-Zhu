package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    private GUI gui;

    //FXML Server

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Label serverMsg;

    @FXML
    private Button serverButton;

    public void serverInfo() {
        gui.setIp(ipField.getCharacters().toString());
        gui.setPort(portField.getCharacters().toString());
        System.out.println("Server set to " + ipField.getCharacters().toString() + ", port " + portField.getCharacters().toString());
        gui.setServer();
    }

    public void showMessage(String str) {
        serverMsg.setText(str);
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

}

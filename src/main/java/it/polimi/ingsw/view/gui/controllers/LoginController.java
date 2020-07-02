package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class LoginController {

    private GUI gui;

    //FXML Server

    @FXML
    private Pane serverScrollImg;
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private Label serverMsg;
    @FXML
    private Button serverButton;


    public void serverInfo() {
        serverMsg.setText("Connecting...");

        gui.setIp(ipField.getCharacters().toString());
        gui.setPort(portField.getCharacters().toString());

        // System.out.println("Server set to " + ipField.getCharacters().toString() + ", port " + portField.getCharacters().toString());
        gui.setServer();
    }

    public void setEffects() {
        FadeTransition transition = new FadeTransition();
        transition.setNode(serverScrollImg);
        transition.setDuration(Duration.millis(700));
        transition.setFromValue(0.2);
        transition.setToValue(1);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();
    }

    //Reset server
    public void resetServer(String ip,String port) {
        ipField.setText(ip);
        portField.setText(port);
    }


    public void showMessage(String str) {
        serverMsg.setText(str);
    }


    public void setGui(GUI gui) {
        this.gui = gui;
    }

}

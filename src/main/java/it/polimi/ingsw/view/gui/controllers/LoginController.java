package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
/**
 * Class that implements the Login Controller of the GUI of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
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

    /**
     * Handles the log-in infos at the connection to the server of the client that hosts the GUI.
     */
    public void serverInfo() {
        serverMsg.setText("Connecting...");

        gui.setIp(ipField.getCharacters().toString());
        gui.setPort(portField.getCharacters().toString());

        // System.out.println("Server set to " + ipField.getCharacters().toString() + ", port " + portField.getCharacters().toString());
        gui.setServer();
    }

    /**
     * Sets up the effects at the log-in to the server of the client that hosts the GUI.
     */
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

    /**
     * Resets the server connection.
     * @param ip Variable that represents the IP of the server.
     * @param port Variable that represents the port of the server.
     */
    public void resetServer(String ip,String port) {
        ipField.setText(ip);
        portField.setText(port);
    }

    /**
     * Shows a connection message on the GUI during the login phase.
     * @param str Variable that represents the message received.
     */
    public void showMessage(String str) {
        serverMsg.setText(str);
    }

    /**
     * Handles the GUI association to the LoginController.
     * @param gui Variable that represents the GUI that needs a Login Handler.
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

}

package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class LoadingController {

    private static GUI gui;

    @FXML
    private Label commandRecv;

    @FXML
    private TextField nameField;

    @FXML
    private Button nameButton;


    public void sendName(ActionEvent event) {
        System.out.println(nameField.getCharacters().toString());
        gui.setInput(nameField.getCharacters().toString());
    }

    public void getCommand(String str) {
        System.out.println(str);
        commandRecv.setText(str);
    }







    public static void setGUI(GUI gui) {
        LoadingController.gui = gui;
    }

}

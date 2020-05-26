package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GuiController {

    @FXML
    private Button nameButton;
    @FXML
    private TextField nameField;

    @FXML
    public void buttonAction(ActionEvent event) {
        if(event.getSource() == nameButton) {
            System.out.println(nameField.textProperty().get());
        }

    }

}

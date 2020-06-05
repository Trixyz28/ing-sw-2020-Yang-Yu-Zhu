package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class LoadingController {

    @FXML
    private Pane startPane;

    @FXML
    public void loadingScene(MouseEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Loading.fxml"));
            startPane.getScene().setRoot(root);

    }


}

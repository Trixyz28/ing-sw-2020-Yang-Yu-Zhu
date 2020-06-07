package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class New extends Application {


    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Loading.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.setTitle("Santorini");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

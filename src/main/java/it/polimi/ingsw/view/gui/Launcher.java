package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class Launcher extends Application implements Runnable {

    private ArrayList<FXMLLoader> allScenes;
    private Scene scene;


    public Launcher() {
        allScenes = new ArrayList<>();
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Start.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Loading.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/GodSelection.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Board.fxml")));
    }

    @Override
    public void run() {
        launch("");
    }


    @Override
    public void start(Stage stage) throws Exception {

        stage.setResizable(false);
        stage.setTitle("Santorini");

        Parent root = allScenes.get(0).load();
        Scene scene = new Scene(root);
        this.scene = scene;

        stage.setScene(scene);
        stage.show();
    }


    public void changeScene(int index) throws IOException {
        Parent root = allScenes.get(index).load();
        scene.setRoot(root);
    }

}

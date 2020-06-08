package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class Launcher extends Application implements Runnable  {


    private ArrayList<FXMLLoader> allScenes;
    private static Stage stage;
    private Scene scene;
    private int sceneIndex;



    public Launcher() {
        allScenes = new ArrayList<>();
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Start.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Loading.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/GodSelection.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Board.fxml")));
    }




    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        stage.setResizable(false);
        stage.setTitle("Santorini");

        Parent root = allScenes.get(0).load();
        Scene scene = new Scene(root);
        this.scene = scene;
        sceneIndex = 0;

        stage.setScene(scene);
        stage.show();
    }


    public void changeScene(int index) throws IOException {
        Parent parent = loadScene(index).load();
        sceneIndex = index;
        scene = new Scene(parent);
        stage.setScene(scene);
    }

    public void showMessage(String str) {
    }


    public ArrayList<FXMLLoader> getAllScenes() {
        return allScenes;
    }

    public int getSceneIndex() {
        return sceneIndex;
    }

    public FXMLLoader loadScene(int index) {
        if(index==0) {
            return new FXMLLoader(getClass().getResource("/fxml/Start.fxml"));
        }
        if(index==1) {
            return new FXMLLoader(getClass().getResource("/fxml/Loading.fxml"));
        }

        return new FXMLLoader(getClass().getResource("/fxml/Board.fxml"));
    }

    @Override
    public void run() {
        launch();
    }
}

package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.view.gui.controllers.Commuter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class GuiLauncher extends Application {


    private ArrayList<FXMLLoader> allScenes;
    private Stage stage;
    private Scene scene;
    private int sceneIndex;

    private String ip;
    private String port;

    private Client client;
    private Commuter commuter;
    private GUI gui;



    public GuiLauncher() {
        allScenes = new ArrayList<>();
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Start.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/ServerSetting.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Nickname.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Lobby.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/GodSelection.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Board.fxml")));
        this.gui = new GUI();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        this.stage.setResizable(false);
        this.stage.setTitle("Santorini");

        Parent root = loadScene(0).load();
        this.commuter = loadScene(0).getController();
        this.commuter.setGuiLauncher(this);

        Scene scene = new Scene(root);
        this.scene = scene;
        sceneIndex = 0;

        stage.setScene(scene);
        stage.show();
    }


    public void changeScene(int index) throws Exception {
        Parent parent = loadScene(index).load();
        this.commuter = loadScene(index).getController();
        this.commuter.setGuiLauncher(this);
        this.sceneIndex = index;
        scene.setRoot(parent);
        stage.setScene(scene);

        if(index==2) {
            createClient();
            new Thread(client).start();
        }
    }



    public ArrayList<FXMLLoader> getAllScenes() {
        return allScenes;
    }

    public int getSceneIndex() {
        return sceneIndex;
    }

    public FXMLLoader loadScene(int index) {
        return allScenes.get(index);
    }

    public GUI getGui() {
        return gui;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void createClient() throws Exception {
        this.client = new Client();
        client.setUi(this.gui);
        client.startClient("gui",ip,port);
        System.out.println("client created");
    }

}

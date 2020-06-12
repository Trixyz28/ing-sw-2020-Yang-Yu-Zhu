package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.view.gui.controllers.Commuter;
import it.polimi.ingsw.view.gui.controllers.GodController;
import it.polimi.ingsw.view.gui.controllers.LoadingController;
import it.polimi.ingsw.view.gui.controllers.ServerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class GuiLauncher extends Application implements Observer {

    private ArrayList<FXMLLoader> allScenes;
    private Stage stage;
    private Scene scene;

    private String ip;
    private String port;

    private Thread thread;
    private Client client;
    private Commuter commuter;
    private GUI gui;

    private boolean playerNameSet = false;
    private ArrayList<String> playerList;
    private ArrayList<String> godList;



    public GuiLauncher() {
        allScenes = new ArrayList<>();
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Start.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/ServerSetting.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Nickname.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Lobby.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/GodSelection.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Board.fxml")));
        this.gui = new GUI();
        this.playerList = new ArrayList<>();
        this.godList = new ArrayList<>();
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

        stage.setScene(scene);
        stage.show();

    }


    public void changeScene(int index)  {

        try {
            Parent parent = loadScene(index).load();
            this.commuter = loadScene(index).getController();
            this.commuter.setGuiLauncher(this);
            scene.setRoot(parent);

            if(index==1) {
                createClient();
            }

            if(index==2) {
                thread = new Thread(client);
                thread.start();
            }

            if(index==3) {
                ((LoadingController)commuter).setChoiceBox();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            stage.close();
        }

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


    public void createClient() {
        this.client = new Client();
        client.setUi(this.gui);
        gui.addObservers(this);
    }

    public void setServer() {

        try {
            Socket socket = new Socket(ip, Integer.parseInt(port));
            client.startClient("gui",socket);
            Platform.runLater(() -> changeScene(2));
        } catch (Exception e) {
           ((ServerController) commuter).showMessage(e.getMessage());
        }

    }

    public Client getClient() {
        return client;
    }


    @Override
    public void update(Object message) {

        if(message.equals(Messages.nicknameAvailable)) {
            Platform.runLater(() -> changeScene(3));
        }

        if(message.equals(Messages.nicknameInUse)) {
            Platform.runLater(() -> ((LoadingController)commuter).showNameMsg((String) message));
        }

        if(message.equals(Messages.matchStarting)) {
            Platform.runLater(() -> changeScene(4));
        }

        if(message.equals(Messages.challengerChosen)) {
            Platform.runLater(() -> ((GodController)commuter).setChosen(true));
        }

/*
        if(message instanceof ArrayList) {
            if(!playerNameSet) {
                playerList = (ArrayList<String>) message;
                playerNameSet = true;
            } else {
                godList = (ArrayList<String>) message;
            }
        }
*/

    }



}

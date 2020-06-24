package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.gui.controllers.*;
import javafx.application.Application;
import javafx.application.Platform;
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

    private Thread clientThread;
    private Client client;
    private GUI gui;

    private boolean challengerSet;
    private boolean chooseWorker;

    private String nickname;
    private String challengerName;
    private ArrayList<String> playerList;
    private String[] godList;
    private BoardView lastView;

    private LoadingController loadingController;
    private GodController godController;
    private BoardController boardController;


    public GuiLauncher() {
        allScenes = new ArrayList<>();
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Start.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/ServerSetting.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Nickname.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Lobby.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/GodSelection.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Board.fxml")));
        this.gui = new GUI();

        this.challengerSet = false;
        this.chooseWorker = false;
        this.playerList = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        this.stage = primaryStage;
        this.stage.setResizable(false);
        this.stage.setTitle("Santorini");

        Parent root = loadScene(0).load();
        this.loadingController = loadScene(0).getController();
        this.loadingController.setGuiLauncher(this);

        Scene scene = new Scene(root);
        this.scene = scene;

        stage.setOnCloseRequest(e-> {
            if(clientThread !=null) {
                if(clientThread.isAlive()) {
                    clientThread.interrupt();
                }
            }
            Platform.exit();
            System.exit(0);
        });

        stage.setScene(scene);
        stage.show();
    }


    public void changeScene(int index)  {

        Platform.runLater(() -> {
            try {
                Parent parent = loadScene(index).load();
                scene.setRoot(parent);

                if(index==1) {
                    loadingController = loadScene(1).getController();
                    loadingController.setGuiLauncher(this);
                    createClient();
                }

                if(index==2) {
                    loadingController = loadScene(2).getController();
                    loadingController.setGuiLauncher(this);
                    clientThread = new Thread(client);
                    clientThread.start();
                }

                if(index==3) {
                    loadingController = loadScene(3).getController();
                    loadingController.setGuiLauncher(this);
                    loadingController.setChoiceBox();
                }

                if(index==4) {
                    godController = loadScene(4).getController();
                    godController.setGuiLauncher(this);
                    this.godList = new String[playerList.size()];
                    godController.setName(playerList);
                    godController.setChallenger(challengerName);
                }

                if(index==5) {
                    boardController = loadScene(5).getController();
                    boardController.setGuiLauncher(this);
                    boardController.setCoin(godList);
                    boardController.setNickname(this.nickname);
                    boardController.setName(playerList);
                    boardController.setGod(godList);
                    boardController.resetButtons();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                stage.close();
            }

        });

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
            client.setupClient("gui",socket);
            changeScene(2);
        } catch (Exception e) {
           loadingController.showMessage(e.getMessage());
        }

    }

    public Client getClient() {
        return client;
    }


    @Override
    public void update(Object message) {

        if(message.equals(Messages.connectionClosed)) {
            Platform.runLater(() -> {
                Stage popup = new Stage();
                popup.setResizable(false);
                popup.setTitle("Game over");
                try {
                    Parent root = new FXMLLoader(getClass().getResource("/fxml/Popup.fxml")).load();
                    Scene scene = new Scene(root);
                    popup.setScene(scene);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                popup.show();
            });
        }

        if(message instanceof String) {

            if(message.equals(Messages.boardStarting)) {
                changeScene(5);
            }

            if(message.equals(Messages.chooseStartPlayer)) {
                Platform.runLater(() -> godController.showMessage((String) message));
            }

            if(message.equals(Messages.Worker)) {
                this.chooseWorker = true;
            }

            if(message.equals(Messages.workerChose)) {
                this.chooseWorker = false;
            }

            if(message.equals(Messages.Place) || message.equals(Messages.Move) || message.equals(Messages.Build)) {
                Platform.runLater(() -> boardController.setRecvMsg((String) message));
            }

            if(message.equals(Messages.endTurn)) {
                Platform.runLater(()-> boardController.hideRecvMsg());
            }

        }


        if(message instanceof BoardView) {
            this.lastView = (BoardView)message;
            Platform.runLater(()-> {
                boardController.showBoard((BoardView) message);
            });
        }


        if(message instanceof GameMessage) {

            if(((GameMessage) message).getMessage().equals(Messages.Worker)) {
                this.chooseWorker = true;
                Platform.runLater(() -> boardController.setRecvMsg((String) ((GameMessage) message).getMessage()));
            } else {
                GodPowerMessage god = GodPowerMessage.valueOf(lastView.getCurrentGod());
                System.out.println("current god: " + lastView.getCurrentGod());

                Platform.runLater(() -> {
                    boardController.setButtons(god.getAnswer1(),god.getAnswer2());
                    boardController.setRecvMsg(god.getMessage());
                });

            }
        }

        if(message instanceof Obj) {
            Obj obj = (Obj)message;

            if(obj.getClassifier().equals("nameMsg")) {
                if(obj.getMessage().equals(Messages.nicknameAvailable)) {
                    this.nickname = loadingController.getNickname();
                    System.out.println("Nickname set: " + this.nickname);
                    changeScene(3);
                }
                if(obj.getMessage().equals(Messages.nicknameInUse) || message.equals(Messages.invalidNickname)) {
                    Platform.runLater(() -> loadingController.showNameMsg((String) message));
                }
            }

            if(obj.getClassifier().equals("createLobby")) {
                Platform.runLater(()-> loadingController.createdLobby(obj.getMessage()));
            }
            if(obj.getClassifier().equals("joinLobby")) {
                Platform.runLater(()-> loadingController.joinedLobby(obj.getMessage()));
            }
            if(obj.getClassifier().equals("playerList")) {
                playerList = obj.getList();
                changeScene(4);
            }
            if(obj.getClassifier().equals("turn")) {
                if(!challengerSet) {
                    this.challengerName = obj.getMessage();
                    challengerSet = true;
                }
                Platform.runLater(()-> godController.setTurn(obj.getMessage()));
            }

            if(obj.getClassifier().equals("defineGod")) {
                Platform.runLater(() -> godController.changeImage(obj.getMessage()));
            }

            if(obj.getClassifier().equals("chooseGod")) {
                for(String name : playerList) {
                    if(name.equals(obj.getPlayer())) {
                        godList[playerList.indexOf(name)] = obj.getMessage();
                    }
                }
                Platform.runLater(() -> godController.setChosenGod(obj));
            }


        }

    }

    public boolean isChooseWorker() {
        return chooseWorker;
    }


}

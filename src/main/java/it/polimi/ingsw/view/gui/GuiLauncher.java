package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.gui.controllers.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

    private boolean playerNameSet;
    private boolean challengerSet;
    private String challengerName;
    private ArrayList<String> playerList;
    private String[] godList;

    private boolean chooseWorker;
    private BoardView lastView;

    public Image[] getCoins() {
        return coins;
    }

    private Image[] coins;


    public GuiLauncher() {
        allScenes = new ArrayList<>();
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Start.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/ServerSetting.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Nickname.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Lobby.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/GodSelection.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Board.fxml")));
        this.gui = new GUI();

        this.playerNameSet = false;
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
        this.commuter = loadScene(0).getController();
        this.commuter.setGuiLauncher(this);

        Scene scene = new Scene(root);
        this.scene = scene;

        stage.setOnCloseRequest(e-> {
            if(thread!=null) {
                thread.interrupt();
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

                if(index==4) {
                    this.godList = new String[playerList.size()];
                    this.coins = new Image[playerList.size()];
                    ((GodController)commuter).setName(playerList);
                    ((GodController)commuter).setChallenger(challengerName);
                }

                if(index==5) {
                    setCoin(coins,godList);
                    ((BoardController)commuter).setName(playerList);
                    ((BoardController)commuter).setGod(godList);
                    ((BoardController)commuter).resetButtons();
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
            if(message.equals(Messages.nicknameAvailable)) {
                changeScene(3);
            }

            if(message.equals(Messages.nicknameInUse)) {
                Platform.runLater(() -> ((LoadingController)commuter).showNameMsg((String) message));
            }

            if(message.equals(Messages.matchStarting)) {
                changeScene(4);
            }

            if(message.equals(Messages.boardStarting)) {
                changeScene(5);
            }

            if(message.equals(Messages.chooseStartPlayer)) {
                Platform.runLater(()-> ((GodController)commuter).showMessage((String) message));
            }

            if(message.equals(Messages.Worker)) {
                this.chooseWorker = true;
            }

            if(message.equals(Messages.workerChose)) {
                this.chooseWorker = false;
            }

            if(message.equals(Messages.Place) || message.equals(Messages.Move) || message.equals(Messages.Build) || message.equals(Messages.tryAgain)) {
                Platform.runLater(() ->((BoardController)commuter).setRecvMsg((String) message));
            }

        }


        if(message instanceof GodChosenMessage) {
            if(((GodChosenMessage) message).getCommand().equals("define")) {
                Platform.runLater(() -> ((GodController)commuter).changeImage(((GodChosenMessage) message).getGod()));
            }
            if(((GodChosenMessage) message).getCommand().equals("choose")) {
                for(String name : playerList) {
                    if(name.equals(((GodChosenMessage) message).getPlayer())) {
                        godList[playerList.indexOf(name)] = ((GodChosenMessage) message).getGod();
                    }
                }

                Platform.runLater(() -> ((GodController)commuter).setChosenGod((GodChosenMessage) message));
            }
        }

        if(message instanceof ArrayList) {
            if(!playerNameSet) {
                playerNameSet = true;
                playerList = (ArrayList<String>) ((ArrayList) message).clone();
            }
        }

        if(message instanceof LobbyMessage) {

            if(((LobbyMessage) message).getCommand().equals("create")) {
                Platform.runLater(()-> ((LoadingController)commuter).createdLobby(((LobbyMessage) message).getNumber()));
            }
            if(((LobbyMessage) message).getCommand().equals("join")) {
                Platform.runLater(()-> ((LoadingController)commuter).joinedLobby(((LobbyMessage) message).getNumber()));
            }
        }

        if(message instanceof TurnMessage) {
            if(!challengerSet) {
                this.challengerName = ((TurnMessage) message).getName();
                challengerSet = true;
            }

            if(((TurnMessage) message).getSource().equals("god")) {
                Platform.runLater(()-> ((GodController)commuter).setTurn(((TurnMessage) message).getName()));
            }
        }

        if(message instanceof BoardView) {
            this.lastView = (BoardView)message;
            Platform.runLater(()-> ((BoardController)commuter).showBoard((BoardView) message));
        }


        if(message instanceof GameMessage) {

            if(((GameMessage) message).getMessage().equals(Messages.Worker)) {
                this.chooseWorker = true;
            } else {
                GodPowerMessage god = GodPowerMessage.valueOf(lastView.getCurrentGod());
                System.out.println("current god: " + lastView.getCurrentGod());

                Platform.runLater(() -> {
                    ((BoardController)commuter).setButtons(god.getAnswer1(),god.getAnswer2());
                    ((BoardController)commuter).setRecvMsg(god.getMessage());
                });

            }
        }

    }

    public boolean isChooseWorker() {
        return chooseWorker;
    }

    public BoardView getLastView() {
        return lastView;
    }

    public void setCoin(Image[] images,String[] godList ) {

        for (int i = 0; i < images.length; i++) {

            switch (godList[i].toUpperCase()) {

                case "APOLLO" -> images[i] = new Image("/coins/ApolloCoin.png");

                case "ARTEMIS" -> images[i] = new Image("/coins/ArtemisCoin.png");

                case "ATHENA" -> images[i] = new Image("/coins/AthenaCoin.png");

                case "ATLAS" -> images[i] = new Image("/coins/AtlasCoin.png");

                case "DEMETER" -> images[i] = new Image("/coins/DemeterCoin.png");

                case "HEPHAESTUS" -> images[i] = new Image("/coins/HephaestusCoin.png");

                case "HERA" -> images[i] = new Image("/coins/HeraCoin.png");

                case "HESTIA" -> images[i] = new Image("/coins/HestiaCoin.png");

                case "LIMUS" -> images[i] = new Image("/coins/LimusCoin.png");

                case "MINOTAUR" -> images[i] = new Image("/coins/MinotaurCoin.png");

                case "PAN" -> images[i] = new Image("/coins/PanCoin.png");

                case "POSEIDON" -> images[i] = new Image("/coins/PoseidonCoin.png");

                case "PROMETHEUS" -> images[i] = new Image("/coins/PrometheusCoin.png");

                case "TRITON" -> images[i] = new Image("/coins/TritonCoin.png");

                case "ZEUS" -> images[i] = new Image("/coins/ZeusCoin.png");

            }
        }
    }

}

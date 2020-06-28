package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.observers.Observer;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.Sender;
import it.polimi.ingsw.view.gui.controllers.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
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

    private Thread clientThread;
    private Client client;
    private final Sender sender;
    private final GUI gui;

    private boolean challengerSet;
    private boolean chooseWorker;
    private boolean boardReceived;

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
        this.sender = new Sender();

        this.challengerSet = false;
        this.chooseWorker = false;
        this.boardReceived = false;
        this.playerList = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        this.stage = primaryStage;
        this.stage.setResizable(false);
        this.stage.setTitle("Santorini");

        Parent root = loadScene(0).load();
        StartController startController = loadScene(0).getController();
        startController.setGuiLauncher(this);
        startController.setGlow();

        Scene scene = new Scene(root);
        this.scene = scene;
        scene.setCursor(new ImageCursor(new Image("components/Cursor.png")));

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
                    loadingController.setSender(sender);
                    createClient();
                }

                if(index==2) {
                    loadingController = loadScene(2).getController();
                    loadingController.setGuiLauncher(this);
                    loadingController.setSender(sender);
                    clientThread = new Thread(client);
                    clientThread.start();
                }

                if(index==3) {
                    loadingController = loadScene(3).getController();
                    loadingController.setGuiLauncher(this);
                    loadingController.setSender(sender);
                    loadingController.setChoiceBox();
                }

                if(index==4) {
                    godController = loadScene(4).getController();
                    godController.setSender(sender);
                    this.godList = new String[playerList.size()];
                    godController.setName(playerList);
                    godController.setChallenger(challengerName);
                    godController.closeInstruction();
                }

                if(index==5) {
                    boardController = loadScene(5).getController();
                    boardController.setGuiLauncher(this);
                    boardController.setSender(sender);
                    boardController.initializeGods(godList);
                    boardController.setNickname(this.nickname);
                    boardController.setName(playerList);
                    boardController.closeRule();
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
            sender.addObservers(client);
            changeScene(2);
        } catch (Exception e) {
           loadingController.showMessage(e.getMessage());
        }

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

        if(message instanceof Obj) {
            Obj obj = (Obj)message;


            if(obj.getTag().equals(Tags.nameMsg)) {
                if(obj.getMessage().equals(Messages.nicknameAvailable)) {
                    this.nickname = loadingController.getNickname();
                    changeScene(3);
                }
                if(obj.getMessage().equals(Messages.nicknameInUse) || message.equals(Messages.invalidNickname)) {
                    Platform.runLater(() -> loadingController.showNameMsg((String) message));
                }
            }

            else if(obj.getTag().equals(Tags.lobbyOk)) {
                Platform.runLater(()-> loadingController.inLobby(obj.getMessage()));
            }

            else if(obj.getTag().equals(Tags.playerList)) {
                playerList = obj.getList();
                changeScene(4);
            }
            else if(obj.getTag().equals(Tags.turn)) {
                if(!challengerSet) {
                    this.challengerName = obj.getMessage();
                    challengerSet = true;
                }
                Platform.runLater(()-> godController.setTurn(obj.getMessage()));
            }

            else if(obj.getTag().equals(Tags.defineGod)) {
                Platform.runLater(() -> godController.changeImage(obj.getMessage()));
            }

            else if(obj.getTag().equals(Tags.chooseGod)) {
                for(String name : playerList) {
                    if(name.equals(obj.getPlayer())) {
                        godList[playerList.indexOf(name)] = obj.getMessage();
                    }
                }
                Platform.runLater(() -> godController.setChosenGod(obj));
            }
            else if(obj.getTag().equals(Tags.board)) {
                this.lastView = obj.getBoardView();
                if(!boardReceived) {
                    changeScene(5);
                    this.boardReceived = true;
                }
                Platform.runLater(()-> {
                    boardController.showBoard(lastView);
                });

            } else if(obj.getTag().equals(Tags.boardMsg)) {

                if(obj.getMessage().equals(Messages.workerChose)) {
                    this.chooseWorker = false;
                } else if(obj.getMessage().equals(Messages.endTurn)) {
                    Platform.runLater(()-> boardController.hideRecvMsg());
                } else {
                    showBoardMsg(obj.getMessage());
                }

            } else if(obj.getTag().equals(Tags.gMsg)) {
                if(obj.getGameMessage().getMessage().equals(Messages.worker)) {
                    this.chooseWorker = true;
                    showBoardMsg(obj.getGameMessage().getMessage());
                } else {
                    GodPowerMessage god = GodPowerMessage.valueOf(lastView.getCurrentGod());
                    System.out.println("current god: " + lastView.getCurrentGod());

                    Platform.runLater(() -> boardController.setButtons(god.getAnswer1(),god.getAnswer2()));
                    showBoardMsg(god.getMessage());
                }

            } else if(obj.getTag().equals(Tags.godMsg)) {
                showGodMsg(obj.getMessage());

            } else if(obj.getTag().equals(Tags.end)) {
                if(obj.getMessage().equals("win")) {
                    if(obj.getPlayer().equals(nickname)) {
                        showBoardMsg("You win!");
                    } else {
                        showBoardMsg("The winner is " + obj.getPlayer() + "!");
                    }
                } else {
                    if(obj.getPlayer().equals(nickname)) {
                        showBoardMsg("You lose!");
                    } else {
                        showBoardMsg("The player " + obj.getPlayer() + " loses!");
                    }
                }
            }
        }

    }

    public boolean isChooseWorker() {
        return chooseWorker;
    }


    public void showGodMsg(String str) {
        Platform.runLater(() -> godController.setCommand(str));
    }

    public void showBoardMsg(String str) {
        Platform.runLater(() -> boardController.setRecvMsg(str));
    }


}

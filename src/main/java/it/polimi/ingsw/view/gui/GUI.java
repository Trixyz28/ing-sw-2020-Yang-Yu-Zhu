package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.observers.Observable;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.Sender;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.gui.controllers.BoardController;
import it.polimi.ingsw.view.gui.controllers.GodController;
import it.polimi.ingsw.view.gui.controllers.LoadingController;
import it.polimi.ingsw.view.gui.controllers.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class GUI implements Ui {

    private Stage stage;

    private String ip;
    private String port;

    private Thread clientThread;
    private Client client;
    private Sender sender;


    private boolean challengerSet;
    private boolean chooseWorker;
    private boolean boardReceived;

    private String nickname;
    private String challengerName;
    private ArrayList<String> playerList;
    private String[] godList;
    private BoardView lastView;

    private LoginController loginController;
    private LoadingController loadingController;
    private GodController godController;
    private BoardController boardController;

    private ArrayList<FXMLLoader> allScenes;


    public GUI() {
        this.challengerSet = false;
        this.chooseWorker = false;
        this.boardReceived = false;

    }

    public void initialize() {
        allScenes = new ArrayList<>();
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Nickname.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Lobby.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/GodSelection.fxml")));
        allScenes.add(new FXMLLoader(getClass().getResource("/fxml/Board.fxml")));
        this.playerList = new ArrayList<>();

        this.client = new Client();
        client.setUi(this);

        stage.setOnCloseRequest(e-> {
            if(clientThread !=null) {
                if(clientThread.isAlive()) {
                    clientThread.interrupt();
                }
            }
            Platform.exit();
            System.exit(0);
        });
    }


    public void changeScene(int index)  {

        Platform.runLater(() -> {
            try {
                Parent parent = loadScene(index).load();
                stage.getScene().setRoot(parent);

                if(index==0) {
                    loadingController = loadScene(0).getController();
                    loadingController.setGui(this);
                    loadingController.setSender(sender);
                    clientThread = new Thread(client);
                    clientThread.start();
                }

                if(index==1) {
                    loadingController = loadScene(1).getController();
                    loadingController.setGui(this);
                    loadingController.setSender(sender);
                    loadingController.setChoiceBox();
                }

                if(index==2) {
                    godController = loadScene(2).getController();
                    godController.setSender(sender);
                    this.godList = new String[playerList.size()];
                    godController.setName(playerList);
                    godController.setChallenger(challengerName);
                    godController.closeInstruction();
                }

                if(index==3) {
                    boardController = loadScene(3).getController();
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


    @Override
    public void showObj(Obj obj) {

        switch (obj.getTag()) {
            case Tags.nameMsg -> handleNameMsg(obj.getMessage());
            case Tags.lobbyOk -> Platform.runLater(() -> loadingController.inLobby(obj.getMessage()));
            case Tags.godMsg -> showGodMsg(obj.getMessage());
        }
    }

    public void setServer() {
        try {
            Socket socket = new Socket(ip, Integer.parseInt(port));
            client.setupClient("gui",socket);
            sender.addObservers(client);
            changeScene(0);
        } catch (Exception e) {
            loginController.showMessage(e.getMessage());
        }
    }

    public void handleNameMsg(String message) {
        if(message.equals(Messages.nicknameAvailable)) {
            this.nickname = loadingController.getNickname();
            changeScene(1);
        }
        if(message.equals(Messages.nicknameInUse) || message.equals(Messages.invalidNickname)) {
            showNameMsg(message);
        }
    }

    @Override
    public void handlePlayerList(ArrayList<String> list) {
        playerList = list;
        changeScene(2);
    }

    @Override
    public void handleDefineGod(String message) {
        Platform.runLater(() -> godController.changeImage(message));
    }

    @Override
    public void handleChooseGod(Obj obj) {
        for (String name : playerList) {
            if (name.equals(obj.getPlayer())) {
                godList[playerList.indexOf(name)] = obj.getMessage();
            }
        }
        Platform.runLater(() -> godController.setChosenGod(obj));
    }

    @Override
    public void handleTurn(String message) {
        if (!challengerSet) {
            this.challengerName = message;
            challengerSet = true;
        }
        Platform.runLater(() -> godController.setTurn(message));
    }


    @Override
    public void handleGameMsg(String message) {
        if(message.equals(Messages.worker)) {
            this.chooseWorker = true;
            showBoardMsg(message);
        } else if(message.equals(Messages.confirmWorker)) {
            showBoardMsg(message);
            Platform.runLater(() -> boardController.setButtons("YES","NO"));
        } else {
            GodPowerMessage god = GodPowerMessage.valueOf(lastView.getCurrentGod());
            Platform.runLater(() -> boardController.setButtons(god.getAnswer1(),god.getAnswer2()));
            showBoardMsg(god.getMessage());
        }
    }

    @Override
    public void handleBoardMsg(String message) {
        if(message.equals(Messages.workerChose)) {
            this.chooseWorker = false;
        } else if(message.equals(Messages.endTurn)) {
            Platform.runLater(()-> boardController.hideRecvMsg());
        } else {
            showBoardMsg(message);
        }
    }

    @Override
    public void updateBoard(BoardView boardView) {
        this.lastView = boardView;
        if(!boardReceived) {
            changeScene(3);
            this.boardReceived = true;
        }
        Platform.runLater(()-> {
            boardController.showBoard(lastView);
        });
    }

    @Override
    public void endGame(Obj obj) {
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

    @Override
    public void showMessage(String str) {
        System.out.println("Received in gui: " + str);
        if(str.equals(Messages.connectionClosed)) {
            finalWindow();
        }
    }

    public void finalWindow() {
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



    public void showNameMsg(String str) {
        Platform.runLater(() -> loadingController.showNameMsg(str));
    }

    public void showGodMsg(String str) {
        Platform.runLater(() -> godController.setCommand(str));
    }

    public void showBoardMsg(String str) {
        Platform.runLater(() -> boardController.setRecvMsg(str));
    }




    public FXMLLoader loadScene(int index) {
        return allScenes.get(index);
    }


    public boolean isChooseWorker() {
        return chooseWorker;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setStage(Stage stage) { this.stage = stage; }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}

package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.Sender;
import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.gui.controllers.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements the GUI of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class GUI implements Ui {

    private Stage stage;
    private Stage popup;

    private String ip;
    private String port;

    private Thread clientThread;
    private Client client;
    private Sender sender;

    private boolean challengerSet;
    private boolean boardReceived;

    private String nickname;
    private String challengerName;
    private List<String> playerList;
    private String[] godList;
    private BoardView lastView;

    private LoginController loginController;
    private LoadingController loadingController;
    private GodController godController;
    private BoardController boardController;

    private ArrayList<FXMLLoader> allScenes;

    /**
     *Creates a <code>GUI</code> with the specified attributes.
     */
    public GUI() {
        this.challengerSet = false;
        this.boardReceived = false;
    }

    /**
     *Initialize the GUI for the client at issue.
     */
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

    /**
     *Updates the scene for the GUI.
     * @param index Variable that represents which type of scene needs to be updated to.
     */
    public void changeScene(int index)  {

        Platform.runLater(() -> {
            try {
                Parent parent = loadScene(index).load();
                stage.getScene().setRoot(parent);

                switch (index) {
                    case 0 -> {
                        loadingController = loadScene(0).getController();
                        loadingController.setSender(sender);
                        clientThread = new Thread(client);
                        clientThread.start();
                    }
                    case 1 -> {
                        loadingController = loadScene(1).getController();
                        loadingController.setSender(sender);
                        loadingController.setChoiceBox();
                    }
                    case 2 -> {
                        godController = loadScene(2).getController();
                        godController.setSender(sender);
                        this.godList = new String[playerList.size()];
                        godController.initialize(playerList, nickname, challengerName);
                    }
                    case 3 -> {
                        boardController = loadScene(3).getController();
                        boardController.setSender(sender);
                        boardController.initialize(playerList, nickname, godList);
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                stage.close();
            }

        });

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void showObj(Obj obj) {

        switch (obj.getTag()) {
            case Tags.NAME_MSG -> handleNameMsg(obj.getMessage());
            case Tags.LOBBY_OK -> Platform.runLater(() -> loadingController.inLobby(obj.getMessage()));
            case Tags.GOD_MSG -> handleGodMsg(obj.getMessage());
        }
    }

    /**
     *Sets the server for the GUI.
     */
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


    /**
     *Shows a parameter that is used for Model updating the nickname of the player at issue.
     * @param message Variable that represents the nickname chosen.
     */
    public void handleNameMsg(String message) {
        if(message.equals(Messages.nicknameAvailable)) {
            this.nickname = loadingController.getNickname();
            changeScene(1);
        }
        if(message.equals(Messages.nicknameInUse) || message.equals(Messages.invalidNickname)) {
            showNameMsg(message);
        }
    }

    /**
     *Shows a parameter that is used for Model updating after the first player has been chosen.
     * @param message Variable that represents the message that was shown to the user.
     */
    public void handleGodMsg(String message) {

        if(message.equals(Messages.chooseStartPlayer)) {
            Platform.runLater(() -> godController.chooseStartPlayer(playerList.size()));
        }

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void handlePlayerList(List<String> list) {
        playerList = list;
        changeScene(2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleDefineGod(String message) {
        Platform.runLater(() -> godController.setDefinedGod(message));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleChooseGod(Obj obj) {
        for (String name : playerList) {
            if (name.equals(obj.getPlayer())) {
                godList[playerList.indexOf(name)] = obj.getMessage();
            }
        }
        Platform.runLater(() -> godController.setChosenGod(obj));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleTurn(String message) {
        if (!challengerSet) {
            this.challengerName = message;
            challengerSet = true;
        }
        Platform.runLater(() -> godController.setTurn(message));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleGameMsg(String message) {
        if(message.equals(Messages.worker)) {
            sender.setChooseWorker(true);
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
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleBoardMsg(String message) {
        if(message.equals(Messages.workerChose)) {
            sender.setChooseWorker(false);
        } else if(message.equals(Messages.endTurn)) {
            Platform.runLater(()-> boardController.hideRecvMsg());
        } else {
            showBoardMsg(message);
        }
    }
    /**
     * {@inheritDoc}
     */
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
    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame(Obj obj) {
        showEndMsg(obj);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void showMessage(String str) {
        System.out.println("Received in gui: " + str);
        if(str.equals(Messages.connectionClosed)) {
            finalWindow();
        }
    }

    /**
     *Prints for the GUI the final window as the game has terminated.
     */
    public void finalWindow() {
        Platform.runLater(() -> {
            this.popup = new Stage();
            popup.setResizable(false);
            popup.setTitle("Game over");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Popup.fxml"));
                Parent root = loader.load();
                ReloadController reload = loader.getController();
                reload.setGui(this);
                Scene scene = new Scene(root);
                popup.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
            popup.show();
        });
    }


    /**
     *Shows on the GUI a name of a player.
     * @param str Variable that is the name of the player at issue.
     */
    public void showNameMsg(String str) {
        Platform.runLater(() -> loadingController.showNameMsg(str));
    }

    /**
     *Show on the GUI a message from the board.
     * @param str Variable that is the message from the board.
     */
    public void showBoardMsg(String str) {
        Platform.runLater(() -> boardController.setRecvMsg(str));
    }

    /**
     *Shows on the GUI a message when the game ends.
     * @param obj Variable that is encapsulating the messages that needs to be shown.
     */
    public void showEndMsg(Obj obj) {
        Platform.runLater( () -> boardController.setEndMsg(obj));
    }

    /**
     *Restart the GUI.
     */
    public void restart() {
        popup.close();

        this.sender = new Sender();
        this.challengerSet = false;
        this.boardReceived = false;
        initialize();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ServerSetting.fxml"));
        try {
            stage.getScene().setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginController login = loader.getController();
        login.setGui(this);
        login.resetServer(ip,port);
    }

    /**
     *Loads the scene for the GUI based on JavaFX.
     * @param index Variable that indicates which scene needs to be shown
     * @return A FXMLLoader of JavaFX of the scene at issue.
     */
    public FXMLLoader loadScene(int index) {
        return allScenes.get(index);
    }

    /**
     *Sets the IP for the client that hosts the GUI.
     * @param ip Variable that represents the IP at issue.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     *Sets the Port for the client that hosts the GUI.
     * @param port Variable that represents the Port at issue.
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     *Sets the stage for the client that hosts the GUI.
     * @param stage Variable that represents the stage at issue.
     */
    public void setStage(Stage stage) { this.stage = stage; }

    /**
     *Sets the sender for the client that hosts the GUI.
     * @param sender Variable that represents the sender at issue.
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    /**
     *Sets the LoginController for the client that hosts the GUI.
     * @param loginController Variable that represents the LoginController at issue.
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}

package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.Sender;
import it.polimi.ingsw.view.WorkerView;
import it.polimi.ingsw.view.gui.GUI;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;


public class BoardController {

    private String nickname;
    private BoardView lastView;
    private Image[] coins;

    private GUI gui;
    private Sender sender;
    private boolean lost = false;


    @FXML
    private VBox leftVBox;

    @FXML
    private Label turnMsg;

    @FXML
    private ImageView mailImg;
    @FXML
    private Label commandRecv;

    @FXML
    private Label endMsg;
    @FXML
    private Pane godButtonPane;
    @FXML
    private Button godPowerButton1;
    @FXML
    private Button godPowerButton2;


    @FXML
    private Pane cloud0;
    @FXML
    private ImageView base0;
    @FXML
    private ImageView god0;
    @FXML
    private Label playerName0;

    @FXML
    private Pane cloud1;
    @FXML
    private ImageView base1;
    @FXML
    private ImageView god1;
    @FXML
    private Label playerName1;

    @FXML
    private Pane cloud2;
    @FXML
    private ImageView base2;
    @FXML
    private ImageView god2;
    @FXML
    private Label playerName2;


    @FXML
    private GridPane boardGrid;

    @FXML
    private ImageView bookIcon;
    @FXML
    private Pane rulePane;
    @FXML
    private ImageView ruleImage;
    @FXML
    private ImageView ruleCross;
    @FXML
    private ImageView rightArrow;
    @FXML
    private ImageView leftArrow;


    public void boardClicked(MouseEvent event) {

        if(event.getSource().equals(boardGrid)) {
            for(Node node : boardGrid.getChildren()) {
                if(node instanceof Pane) {
                    if (node.getBoundsInParent().contains(event.getX(), event.getY())) {

                        // System.out.println("Clicked at the pane at row " + GridPane.getRowIndex(node) + ", column " + GridPane.getColumnIndex(node));
                        if(gui.isChooseWorker()) {
                            for(WorkerView workerView : lastView.getWorkerList()) {
                                if(workerView.getBelongToPlayer() == lastView.getCurrentID()) {
                                    if(workerView.getPosition().equals(lastView.getTile(GridPane.getRowIndex(node),GridPane.getColumnIndex(node)))) {
                                        sender.sendInput(String.valueOf(workerView.getWorkerID()));
                                    }
                                }
                            }
                        } else {
                            String command = GridPane.getRowIndex(node) + "," + GridPane.getColumnIndex(node);
                            sender.sendInput(command);
                        }

                    }
                }
            }
        }

    }

    public void initialize(ArrayList<String> playerList,String nickname,String[] godList) {
        initializeGods(godList);
        setNickname(nickname);
        setName(playerList);
        closeRule();
        hideRecvMsg();
        resetButtons();
    }



    public void setName(ArrayList<String> nameList) {

        playerName0.setText(nameList.get(0));
        if(nickname.equals(playerName0.getText())) {
            base0.setImage(new Image("/clouds/CloudMe.png"));
        }
        playerName1.setText(nameList.get(1));
        if(nickname.equals(playerName1.getText())) {
            base1.setImage(new Image("/clouds/CloudMe.png"));
        }

        if(nameList.size()==3) {
            playerName2.setText(nameList.get(2));
            if(nickname.equals(playerName2.getText())) {
                base2.setImage(new Image("/clouds/CloudMe.png"));
            }
        } else {
            cloud2.setDisable(true);
            cloud2.setVisible(false);
        }
    }

    public void initializeGods(String[] godList) {
        setCoin(godList);
        setGod(godList);
    }


    private void setGod(String[] godList) {

        setCloud(god0,godList[0]);
        setCloud(god1,godList[1]);

        if(godList.length==3) {
            setCloud(god2,godList[2]);
        }
    }


    private void setCloud(ImageView cloud,String god) {
        Image image = switch (god) {

            case "APOLLO" -> new Image("/clouds/ApolloCloud.png");
            case "ARTEMIS" -> new Image("/clouds/ArtemisCloud.png");
            case "ATHENA" -> new Image("/clouds/AthenaCloud.png");
            case "ATLAS" -> new Image("/clouds/AtlasCloud.png");
            case "DEMETER" -> new Image("/clouds/DemeterCloud.png");
            case "HEPHAESTUS" -> new Image("/clouds/HephaestusCloud.png");
            case "HERA" -> new Image("/clouds/HeraCloud.png");
            case "HESTIA" -> new Image("/clouds/HestiaCloud.png");
            case "LIMUS" -> new Image("/clouds/LimusCloud.png");
            case "MINOTAUR" -> new Image("/clouds/MinotaurCloud.png");
            case "PAN" -> new Image("/clouds/PanCloud.png");
            case "POSEIDON" -> new Image("/clouds/PoseidonCloud.png");
            case "PROMETHEUS" -> new Image("/clouds/PrometheusCloud.png");
            case "TRITON" -> new Image("/clouds/TritonCloud.png");
            case "ZEUS" -> new Image("/clouds/ZeusCloud.png");

            default -> throw new IllegalStateException("Unexpected value: " + god);
        };

        cloud.setImage(image);
    }


    public void showBoard(BoardView boardView) {

        this.lastView = boardView;

        for(Node node : boardGrid.getChildren()) {
            if(node instanceof Pane) {
                Tile tile = boardView.getTile(GridPane.getRowIndex(node),GridPane.getColumnIndex(node));

                if(tile.isOccupiedByWorker()) {
                    printWorker((ImageView) ((Pane) node).getChildren().get(1),boardView,tile);
                } else if(tile.isDomePresence()) {
                    printDome((ImageView) ((Pane) node).getChildren().get(1));
                } else {
                    printEmpty((ImageView) ((Pane) node).getChildren().get(1));
                }

                printBlock((ImageView) ((Pane) node).getChildren().get(0),tile.getBlockLevel());
                printCanOp((ImageView) ((Pane) node).getChildren().get(2),checkCanOp(boardView,tile));
            }
        }

        setTurn(boardView.getCurrentName());
    }


    private void printWorker(ImageView imageView,BoardView boardView,Tile t) {

        for (int i = 0; i < boardView.getWorkerList().length; i++) {

            if (boardView.getWorkerList()[i].isPositionSet()) {
                if (t.equals(boardView.getWorkerList()[i].getPosition())) {

                    imageView.setImage(coins[boardView.getWorkerList()[i].getBelongToPlayer()]);

                }
            }
        }
    }


    private void printCanOp(ImageView imageView,int number) {

        if(!(nickname.equals(lastView.getCurrentName())) || number==0) {
            printEmpty(imageView);
        } else {
            Image image;
            if(number==1) {
                image = new Image("/buildings/canMove.png");
            } else {
                image = new Image("/buildings/canBuild.png");
            }
            imageView.setImage(image);
        }
    }

    private void printEmpty(ImageView imageView) {
        Image image = new Image("/buildings/empty.png");
        imageView.setImage(image);
    }


    private int checkCanOp(BoardView boardView,Tile t) {
        if(boardView.getChosenWorkerID()!=-1) {
            WorkerView chosen = boardView.getWorkerList()[boardView.getChosenWorkerID()];

            if(chosen.getState()==1) {
                if(chosen.getMovableList().contains(t)) {
                    return 1;
                }
            }
            if(chosen.getState()==2 || chosen.getState()==3) {
                if(chosen.getBuildableList().contains(t)) {
                    return 2;
                }
            }
        }
        return 0;
    }


    private void printDome(ImageView dome) {
        Image image = new Image("/buildings/dome.png");
        dome.setImage(image);
    }


    private void printBlock(ImageView block,int level) {
        Image image = switch (level) {
            case 1 -> new Image("/buildings/level1.png");
            case 2 -> new Image("/buildings/level2.png");
            case 3 -> new Image("/buildings/level3.png");
            default -> new Image("buildings/empty.png");
        };

        block.setImage(image);
    }


    private void setTurn(String player) {
        boardGrid.setDisable(!player.equals(nickname));

        if(player.equals(playerName0.getText())) {
            cloud0.setOpacity(1);
            cloud1.setOpacity(0.5);
            cloud2.setOpacity(0.5);
        }
        if(player.equals(playerName1.getText())) {
            cloud0.setOpacity(0.5);
            cloud1.setOpacity(1);
            cloud2.setOpacity(0.5);
        }
        if(!playerName2.isDisable()) {
            if(player.equals(playerName2.getText())) {
                cloud0.setOpacity(0.5);
                cloud1.setOpacity(0.5);
                cloud2.setOpacity(1);
            }
        }

        showTurnMsg(player);
    }


    public void showTurnMsg(String str) {
        turnMsg.setText(str);
    }


    public void resetButtons() {
        godButtonPane.setVisible(false);
        godButtonPane.setDisable(true);
    }


    public void setButtons(String str1,String str2) {
        godButtonPane.setDisable(false);
        godButtonPane.setVisible(true);
        godPowerButton1.setText(str1);
        godPowerButton2.setText(str2);
    }


    public void hideRecvMsg() {
        mailImg.setVisible(false);
        commandRecv.setText("");
    }


    public void setRecvMsg(String str) {
        mailImg.setVisible(true);
        commandRecv.setText(str);
    }


    public void sendAnswer1() {
        sender.sendInput(godPowerButton1.getText());
        resetButtons();
    }

    public void sendAnswer2() {
        sender.sendInput(godPowerButton2.getText());
        resetButtons();
    }


    private void setCoin(String[] godList) {

        coins = new Image[godList.length];

        for (int i = 0; i < coins.length; i++) {
            switch (godList[i].toUpperCase()) {
                case "APOLLO" -> coins[i] = new Image("/coins/ApolloCoin.png");
                case "ARTEMIS" -> coins[i] = new Image("/coins/ArtemisCoin.png");
                case "ATHENA" -> coins[i] = new Image("/coins/AthenaCoin.png");
                case "ATLAS" -> coins[i] = new Image("/coins/AtlasCoin.png");
                case "DEMETER" -> coins[i] = new Image("/coins/DemeterCoin.png");
                case "HEPHAESTUS" -> coins[i] = new Image("/coins/HephaestusCoin.png");
                case "HERA" -> coins[i] = new Image("/coins/HeraCoin.png");
                case "HESTIA" -> coins[i] = new Image("/coins/HestiaCoin.png");
                case "LIMUS" -> coins[i] = new Image("/coins/LimusCoin.png");
                case "MINOTAUR" -> coins[i] = new Image("/coins/MinotaurCoin.png");
                case "PAN" -> coins[i] = new Image("/coins/PanCoin.png");
                case "POSEIDON" -> coins[i] = new Image("/coins/PoseidonCoin.png");
                case "PROMETHEUS" -> coins[i] = new Image("/coins/PrometheusCoin.png");
                case "TRITON" -> coins[i] = new Image("/coins/TritonCoin.png");
                case "ZEUS" -> coins[i] = new Image("/coins/ZeusCoin.png");
            }
        }
    }


    public void setNickname(String str) {
        this.nickname = str;
    }

    public void setGuiLauncher(GUI gui) {
        this.gui = gui;
    }

    public void openRule() {
        leftVBox.setDisable(true);
        boardGrid.setDisable(true);
        rulePane.setDisable(false);
        rulePane.setVisible(true);
        loadFirstPage();
    }


    public void closeRule() {
        rulePane.setVisible(false);
        rulePane.setDisable(true);
        leftVBox.setDisable(false);
        boardGrid.setDisable(false);
    }

    public void loadFirstPage() {
        ruleImage.setImage(new Image("/components/Rules1.png"));
        leftArrow.setDisable(true);
        leftArrow.setVisible(false);
        rightArrow.setDisable(false);
        rightArrow.setVisible(true);
    }

    public void loadSecondPage() {
        ruleImage.setImage(new Image("/components/Rules2.png"));
        leftArrow.setDisable(false);
        leftArrow.setVisible(true);
        rightArrow.setVisible(false);
        rightArrow.setDisable(true);
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public void setEndMsg(Obj obj) {
        if(obj.getMessage().equals("win")) {
            if(obj.getPlayer().equals(nickname)) {
                endMsg.setText("You win!");
            } else {
                endMsg.setText("The winner is " + obj.getPlayer() + "!");
            }
        } else {
            if(obj.getPlayer().equals(nickname)) {
                endMsg.setText("You lose!");
                this.lost = true;
            } else {
                endMsg.setText("The player " + obj.getPlayer() + " loses!");
            }

            if(obj.getPlayer().equals(playerName0.getText())) {
                base0.setImage(new Image("/clouds/CloudLose.png"));
            }
            if(obj.getPlayer().equals(playerName1.getText())) {
                base1.setImage(new Image("/clouds/CloudLose.png"));
            }
            if(obj.getPlayer().equals(playerName2.getText())) {
                base2.setImage(new Image("/clouds/CloudLose.png"));
            }
        }
    }

    public void resetEndMsg() {
        endMsg.setText("");
    }

}

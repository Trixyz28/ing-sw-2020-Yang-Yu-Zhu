package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.view.BoardView;
import it.polimi.ingsw.view.WorkerView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class BoardController extends Commuter {

    @FXML
    private Label commandRecv;
    @FXML
    private Label turnMsg;
    @FXML
    private Pane godButtonPane;
    @FXML
    private Button godPowerButton1;
    @FXML
    private Button godPowerButton2;


    @FXML
    private Pane cloud0;
    @FXML
    private ImageView god0;
    @FXML
    private Label playerName0;

    @FXML
    private Pane cloud1;
    @FXML
    private ImageView god1;
    @FXML
    private Label playerName1;

    @FXML
    private Pane cloud2;
    @FXML
    private ImageView god2;
    @FXML
    private Label playerName2;



    @FXML
    private GridPane boardGrid;

    @FXML
    private Pane tile00;
    @FXML
    private ImageView buildLayer00;
    @FXML
    private ImageView workerLayer00;
    @FXML
    private ImageView opLayer00;

    @FXML
    private Pane tile01;
    @FXML
    private ImageView buildLayer01;
    @FXML
    private ImageView workerLayer01;
    @FXML
    private ImageView opLayer01;

    @FXML
    private Pane tile02;
    @FXML
    private ImageView buildLayer02;
    @FXML
    private ImageView workerLayer02;
    @FXML
    private ImageView opLayer02;

    @FXML
    private Pane tile03;
    @FXML
    private ImageView buildLayer03;
    @FXML
    private ImageView workerLayer03;
    @FXML
    private ImageView opLayer03;

    @FXML
    private Pane tile04;
    @FXML
    private ImageView buildLayer04;
    @FXML
    private ImageView workerLayer04;
    @FXML
    private ImageView opLayer04;

    @FXML
    private Pane tile10;
    @FXML
    private ImageView buildLayer10;
    @FXML
    private ImageView workerLayer10;
    @FXML
    private ImageView opLayer10;

    @FXML
    private Pane tile11;
    @FXML
    private ImageView buildLayer11;
    @FXML
    private ImageView workerLayer11;
    @FXML
    private ImageView opLayer11;

    @FXML
    private Pane tile12;
    @FXML
    private ImageView buildLayer12;
    @FXML
    private ImageView workerLayer12;
    @FXML
    private ImageView opLayer12;

    @FXML
    private Pane tile13;
    @FXML
    private ImageView buildLayer13;
    @FXML
    private ImageView workerLayer13;
    @FXML
    private ImageView opLayer13;

    @FXML
    private Pane tile14;
    @FXML
    private ImageView buildLayer14;
    @FXML
    private ImageView workerLayer14;
    @FXML
    private ImageView opLayer14;

    @FXML
    private Pane tile20;
    @FXML
    private ImageView buildLayer20;
    @FXML
    private ImageView workerLayer20;
    @FXML
    private ImageView opLayer20;

    @FXML
    private Pane tile21;
    @FXML
    private ImageView buildLayer21;
    @FXML
    private ImageView workerLayer21;
    @FXML
    private ImageView opLayer21;

    @FXML
    private Pane tile22;
    @FXML
    private ImageView buildLayer22;
    @FXML
    private ImageView workerLayer22;
    @FXML
    private ImageView opLayer22;

    @FXML
    private Pane tile23;
    @FXML
    private ImageView buildLayer23;
    @FXML
    private ImageView workerLayer23;
    @FXML
    private ImageView opLayer23;

    @FXML
    private Pane tile24;
    @FXML
    private ImageView buildLayer24;
    @FXML
    private ImageView workerLayer24;
    @FXML
    private ImageView opLayer24;

    @FXML
    private Pane tile30;
    @FXML
    private ImageView buildLayer30;
    @FXML
    private ImageView workerLayer30;
    @FXML
    private ImageView opLayer30;

    @FXML
    private Pane tile31;
    @FXML
    private ImageView buildLayer31;
    @FXML
    private ImageView workerLayer31;
    @FXML
    private ImageView opLayer31;

    @FXML
    private Pane tile32;
    @FXML
    private ImageView buildLayer32;
    @FXML
    private ImageView workerLayer32;
    @FXML
    private ImageView opLayer32;

    @FXML
    private Pane tile33;
    @FXML
    private ImageView buildLayer33;
    @FXML
    private ImageView workerLayer33;
    @FXML
    private ImageView opLayer33;

    @FXML
    private Pane tile34;
    @FXML
    private ImageView buildLayer34;
    @FXML
    private ImageView workerLayer34;
    @FXML
    private ImageView opLayer34;

    @FXML
    private Pane tile40;
    @FXML
    private ImageView buildLayer40;
    @FXML
    private ImageView workerLayer40;
    @FXML
    private ImageView opLayer40;

    @FXML
    private Pane tile41;
    @FXML
    private ImageView buildLayer41;
    @FXML
    private ImageView workerLayer41;
    @FXML
    private ImageView opLayer41;

    @FXML
    private Pane tile42;
    @FXML
    private ImageView buildLayer42;
    @FXML
    private ImageView workerLayer42;
    @FXML
    private ImageView opLayer42;

    @FXML
    private Pane tile43;
    @FXML
    private ImageView buildLayer43;
    @FXML
    private ImageView workerLayer43;
    @FXML
    private ImageView opLayer43;

    @FXML
    private Pane tile44;
    @FXML
    private ImageView buildLayer44;
    @FXML
    private ImageView workerLayer44;
    @FXML
    private ImageView opLayer44;




    public void boardClicked(MouseEvent event) {

        if(event.getSource().equals(boardGrid)) {
            for(Node node : boardGrid.getChildren()) {
                if(node instanceof Pane) {
                    if (node.getBoundsInParent().contains(event.getX(), event.getY())) {

                        System.out.println("Clicked at the pane at row " + GridPane.getRowIndex(node) + ", column " + GridPane.getColumnIndex(node));

                        if(super.getGuiLauncher().isChooseWorker()) {
                            BoardView boardView = super.getGuiLauncher().getLastView();
                            for(WorkerView workerView : boardView.getWorkerList()) {
                                if(workerView.getPosition().equals(boardView.getTile(GridPane.getRowIndex(node),GridPane.getColumnIndex(node)))) {
                                    super.getGuiLauncher().getClient().sendInput(String.valueOf(workerView.getWorkerID()));
                                }
                            }

                        } else {
                            String command = GridPane.getRowIndex(node) + "," + GridPane.getColumnIndex(node);
                            super.getGuiLauncher().getClient().sendInput(command);
                        }

                    }
                }
            }
        }

    }


    public void setName(ArrayList<String> nameList) {
        playerName0.setText(nameList.get(0));
        playerName1.setText(nameList.get(1));
        if(nameList.size()==3) {
            playerName2.setText(nameList.get(2));
        } else {
            cloud2.setDisable(true);
            cloud2.setVisible(false);
        }
    }

    public void setGod(String[] godList) {
        setCloud(god0,godList[0]);
        setCloud(god1,godList[1]);

        if(godList.length==3) {
            setCloud(god2,godList[2]);
        }
    }



    public void setCloud(ImageView cloud,String god) {
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


        for(Node node : boardGrid.getChildren()) {
            if(node instanceof Pane) {
                int row = GridPane.getRowIndex(node);
                int column = GridPane.getColumnIndex(node);

                Tile tile = boardView.getTile(row,column);


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


    public void printWorker(ImageView imageView,BoardView boardView,Tile t) {


        for (int i = 0; i < boardView.getWorkerList().length; i++) {

            if (boardView.getWorkerList()[i].isPositionSet()) {
                if (t.equals(boardView.getWorkerList()[i].getPosition())) {

                    if(boardView.getWorkerList()[i].getBelongToPlayer()==0) {
                        imageView.setImage(getGuiLauncher().getCoins()[0]);
                    }
                    if(boardView.getWorkerList()[i].getBelongToPlayer()==1) {
                        imageView.setImage(getGuiLauncher().getCoins()[1]);
                    }

                    if(getGuiLauncher().getCoins().length==3) {
                        if(boardView.getWorkerList()[i].getBelongToPlayer()==2) {
                            imageView.setImage(getGuiLauncher().getCoins()[2]);
                        }
                    }

                }
            }
        }

    }


    public void printCanOp(ImageView imageView,int number) {

        if(number==0) {
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

    public void printEmpty(ImageView imageView) {
        Image image = new Image("/buildings/empty.png");
        imageView.setImage(image);
    }


    public int checkCanOp(BoardView boardView,Tile t) {
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

    public void printDome(ImageView dome) {
        Image image = new Image("/buildings/dome.png");
        dome.setImage(image);
    }

    public void printBlock(ImageView block,int level) {
        Image image = switch (level) {
            case 1 -> new Image("/buildings/level1.png");
            case 2 -> new Image("/buildings/level2.png");
            case 3 -> new Image("/buildings/level3.png");
            default -> new Image("buildings/empty.png");
        };

        block.setImage(image);
    }

    public void setTurn(String player) {
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

    public void setRecvMsg(String str) {
        commandRecv.setText(str);
    }

    public void sendAnswer1() {
        super.getGuiLauncher().getClient().sendInput(godPowerButton1.getText());
        resetButtons();
    }

    public void sendAnswer2() {
        super.getGuiLauncher().getClient().sendInput(godPowerButton2.getText());
        resetButtons();
    }

}

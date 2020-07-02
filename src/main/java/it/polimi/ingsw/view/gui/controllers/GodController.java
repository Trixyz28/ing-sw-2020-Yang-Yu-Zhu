package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.messages.Obj;
import it.polimi.ingsw.view.Sender;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GodController {

    private boolean canChoosePlayer = false;
    private int counter = 0;

    private Sender sender;
    private List<String> definedGods = new ArrayList<>();

    @FXML
    private HBox mainHBox;

    @FXML
    private GridPane cardGrid;

    @FXML
    private Label currentPlayer;

    @FXML
    private ImageView title;


    @FXML
    private Pane cloud0;
    @FXML
    private ImageView backCloud0;
    @FXML
    private ImageView godCloud0;
    @FXML
    private Label playerName0;
    @FXML
    private Label challenger0;


    @FXML
    private Pane cloud1;
    @FXML
    private ImageView backCloud1;
    @FXML
    private ImageView godCloud1;
    @FXML
    private Label playerName1;
    @FXML
    private Label challenger1;


    @FXML
    private Pane cloud2;
    @FXML
    private ImageView backCloud2;
    @FXML
    private ImageView godCloud2;
    @FXML
    private Label playerName2;
    @FXML
    private Label challenger2;

    @FXML
    private ImageView bookIcon;
    @FXML
    private Pane instructionPane;
    @FXML
    private ImageView instructionPage;
    @FXML
    private ImageView instructionCross;
    @FXML
    private ImageView leftArrow;
    @FXML
    private ImageView rightArrow;


    public void initialize(ArrayList<String>playerList, String nickname, String challengerName) {
        setName(playerList,nickname);
        setChallenger(challengerName);
        closeInstruction();
    }


    public void gridClicked(MouseEvent event) {

        // int row;
        // int column;
        ImageView imageView = null;

        if(event.getSource().equals(cardGrid)) {
            for(Node node : cardGrid.getChildren()) {
                if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                    // row = GridPane.getRowIndex(node);
                    // column = GridPane.getColumnIndex(node);
                    // System.out.println("Clicked at row " + row + ", column " + column);
                    imageView = (ImageView) node;
                    break;
                }
            }
        }

        if(imageView!=null && !imageView.isDisable()) {
            sender.sendInput(imageView.getId());
        }
    }



    public void setDefinedGod(String god) {

        definedGods.add(god);

        for(Node node : cardGrid.getChildren()) {
            if(node instanceof ImageView) {
                if(node.getId().toUpperCase().equals(god)) {
                    Image image = switch (node.getId()) {

                        case "apollo" -> new Image("/godcards/chosen/ApolloChosen.png");
                        case "artemis" -> new Image("/godcards/chosen/ArtemisChosen.png");
                        case "athena" -> new Image("/godcards/chosen/AthenaChosen.png");
                        case "atlas" -> new Image("/godcards/chosen/AtlasChosen.png");
                        case "demeter" -> new Image("/godcards/chosen/DemeterChosen.png");
                        case "hephaestus" -> new Image("/godcards/chosen/HephaestusChosen.png");
                        case "hera" -> new Image("/godcards/chosen/HeraChosen.png");
                        case "hestia" -> new Image("/godcards/chosen/HestiaChosen.png");
                        case "limus" -> new Image("/godcards/chosen/LimusChosen.png");
                        case "minotaur" -> new Image("/godcards/chosen/MinotaurChosen.png");
                        case "pan" -> new Image("/godcards/chosen/PanChosen0.png");
                        case "poseidon" -> new Image("/godcards/chosen/PoseidonChosen.png");
                        case "prometheus" -> new Image("/godcards/chosen/PrometheusChosen.png");
                        case "triton" -> new Image("/godcards/chosen/TritonChosen.png");
                        case "zeus" -> new Image("/godcards/chosen/ZeusChosen.png");

                        default -> ((ImageView) node).getImage();
                    };

                    ((ImageView) node).setImage(image);
                }
            }
        }

        if(checkIfDefined()) {
            hideUnchosenGods();
            setChooseTitle();

        }
    }

    private boolean checkIfDefined() {
        return (definedGods.size() == 2 && cloud2.isDisable()) || (definedGods.size() == 3);
    }
    

    private void setName(ArrayList<String> nameList,String nickname) {
        playerName0.setText(nameList.get(0));
        if(nickname.equals(playerName0.getText())) {
            backCloud0.setImage(new Image("/clouds/CloudMe.png"));
        }

        playerName1.setText(nameList.get(1));
        if(nickname.equals(playerName1.getText())) {
            backCloud1.setImage(new Image("/clouds/CloudMe.png"));
        }

        if(nameList.size()==3) {
            playerName2.setText(nameList.get(2));
            if(nickname.equals(playerName2.getText())) {
                backCloud2.setImage(new Image("/clouds/CloudMe.png"));
            }
        } else {
            cloud2.setDisable(true);
            cloud2.setVisible(false);
        }
    }

    private void setChallenger(String challenger) {
        if(challenger.equals(playerName0.getText())) {
            challenger0.setText("Challenger");
        }
        if(challenger.equals(playerName1.getText())) {
            challenger1.setText("Challenger");
        }
        if(challenger.equals(playerName2.getText())) {
            challenger2.setText("Challenger");
        }
    }

    public void select0() {
        if(canChoosePlayer) {
            sender.sendInput(playerName0.getText());
        }
    }

    public void select1() {
        if(canChoosePlayer) {
            sender.sendInput(playerName1.getText());
        }
    }

    public void select2() {
        if(canChoosePlayer) {
            sender.sendInput(playerName2.getText());
        }
    }

    public void setChosenGod(Obj message) {

        for(Node node : cardGrid.getChildren()) {
            if (node.getId().equalsIgnoreCase(message.getMessage())) {
                node.setOpacity(0.7);
                node.setDisable(true);
                break;
            }
        }

        if(message.getPlayer().equals(playerName0.getText())) {
            setCloud(godCloud0,message.getMessage());
        }
        if(message.getPlayer().equals(playerName1.getText())) {
            setCloud(godCloud1,message.getMessage());
        }
        if(cloud2.isVisible()) {
           if(message.getPlayer().equals(playerName2.getText())) {
               setCloud(godCloud2,message.getMessage());
           }
        }

        counter++;

        if((counter==2 && cloud2.isDisable()) || counter==3) {
            title.setImage(new Image("/components/startPlayer.png"));
        }
    }


    public void setCloud(ImageView imageView,String god) {

        Image image = switch (god.toLowerCase()) {

            case "apollo" -> new Image("/clouds/ApolloCloud.png");
            case "artemis" -> new Image("/clouds/ArtemisCloud.png");
            case "athena" -> new Image("/clouds/AthenaCloud.png");
            case "atlas" -> new Image("/clouds/AtlasCloud.png");
            case "demeter" -> new Image("/clouds/DemeterCloud.png");
            case "hephaestus" -> new Image("/clouds/HephaestusCloud.png");
            case "hera" -> new Image("/clouds/HeraCloud.png");
            case "hestia" -> new Image("/clouds/HestiaCloud.png");
            case "limus" -> new Image("/clouds/LimusCloud.png");
            case "minotaur" -> new Image("/clouds/MinotaurCloud.png");
            case "pan" -> new Image("/clouds/PanCloud.png");
            case "poseidon" -> new Image("/clouds/PoseidonCloud.png");
            case "prometheus" -> new Image("/clouds/PrometheusCloud.png");
            case "triton" -> new Image("/clouds/TritonCloud.png");
            case "zeus" -> new Image("/clouds/ZeusCloud.png");

            default -> imageView.getImage();
        };

        imageView.setImage(image);
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
        if(player.equals(playerName2.getText())) {
            cloud0.setOpacity(0.5);
            cloud1.setOpacity(0.5);
            cloud2.setOpacity(1);
        }

        setCurrentPlayer(player);
    }

    public void setCurrentPlayer(String str) {
        currentPlayer.setText(str);
    }


    public void openInstruction() {
        mainHBox.setDisable(true);
        instructionPane.setDisable(false);
        instructionPane.setVisible(true);
        loadFirstPage();
    }

    public void closeInstruction() {
        mainHBox.setDisable(false);
        instructionPane.setDisable(true);
        instructionPane.setVisible(false);
    }


    public void loadFirstPage() {
        leftArrow.setVisible(false);
        leftArrow.setDisable(true);
        rightArrow.setDisable(false);
        rightArrow.setVisible(true);
        instructionPage.setImage(new Image("/components/Rules1.png"));
    }

    public void loadSecondPage() {
        leftArrow.setDisable(false);
        leftArrow.setVisible(true);
        rightArrow.setVisible(false);
        rightArrow.setDisable(true);
        instructionPage.setImage(new Image("/components/Rules2.png"));

    }


    private void hideUnchosenGods() {

        for(Node node : cardGrid.getChildren()) {
            if(node instanceof ImageView) {
                boolean checked = false;
                for(String name : definedGods) {
                    if(node.getId().equalsIgnoreCase(name)) {
                        checked = true;
                    }
                }
                if(!checked) {
                    disappearGod(node);
                    node.setDisable(true);
                }
            }
        }
    }

    private void setChooseTitle() {
        title.setImage(new Image("/components/choosingGod.png"));
    }



    public void chooseStartPlayer(int size) {
        this.canChoosePlayer = true;

        cloud0.setOpacity(1);
        setCloudEffects(cloud0);

        cloud1.setOpacity(1);
        setCloudEffects(cloud1);
        if(size==3) {
            cloud2.setOpacity(1);
            setCloudEffects(cloud2);
        }
    }

    private void setCloudEffects(Node node) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setFromValue(0.3);
        fade.setToValue(1);
        fade.setCycleCount(-1);
        fade.setAutoReverse(true);
        fade.setNode(node);
        fade.play();
    }

    private void disappearGod(Node node) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setToValue(0);
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(node);
        fade.play();
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

}

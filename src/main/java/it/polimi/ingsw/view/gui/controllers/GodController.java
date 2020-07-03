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
/**
 * Class that implements the God Controller of the GUI of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class GodController {

    private boolean canChoosePlayer = false;
    private int counter = 0;

    private Sender sender;
    private int playerNumber;
    private final List<String> definedGods = new ArrayList<>();

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

    /**
     * Initialize the playerlist and the challenger to setup the Gods.
     * @param playerList Variable that represents the list of players in the game.
     * @param nickname Variable that represents the nickname of the current player.
     * @param challengerName Variable that represents the nickname of the challenger.
     */
    public void initialize(List<String>playerList, String nickname, String challengerName) {
        this.playerNumber = playerList.size();
        setName(playerList,nickname);
        setChallenger(challengerName);
        closeInstruction();
    }

    /**
     * Checks if there's a click of the mouse.
     * @param event Variable that represents an event of the GUI.
     */
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


    /**
     * Sets the JavaFX GUI of the gods that are chosen.
     * @param god Variable that represents the god at issue.
     */
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

    /**
     * Checks if the God List has been defined by the Challenger.
     * @return A boolean: <code>true</code> if the choice is made, otherwise <code>false</code>.
     */
    private boolean checkIfDefined() {
        return (definedGods.size() == playerNumber);
    }

    /**
     * Sets the bubble with the name of the players in the GUI.
     * @param nameList Variable that represents the list of the names of the players in the game.
     * @param nickname Variable that represents the nickname of the current player of the client with the GUI.
     */
    private void setName(List<String> nameList,String nickname) {
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

    /**
     * Sets the challenger in the GUI of the client of the current player.
     * @param challenger Variable that represents the nickname of the challenger.
     */
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

    /**
     *Activates an input if the player 0 is chosen on the GUI.
     */
    public void select0() {
        if(canChoosePlayer) {
            sender.sendInput(playerName0.getText());
        }
    }

    /**
     *Activates an input if the player 1 is chosen on the GUI.
     */
    public void select1() {
        if(canChoosePlayer) {
            sender.sendInput(playerName1.getText());
        }
    }

    /**
     *Activates an input if the player 2 is chosen on the GUI.
     */
    public void select2() {
        if(canChoosePlayer) {
            sender.sendInput(playerName2.getText());
        }
    }

    /**
     * Sets the chosen God in the GUI.
     * @param message Variable that encapsulate the information about the God at issue.
     */
    public void setChosenGod(Obj message) {

        for(Node node : cardGrid.getChildren()) {
            if (node.getId().equalsIgnoreCase(message.getMessage())) {
                node.setOpacity(0.6);
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

        if(counter==playerNumber) {
            title.setImage(new Image("/components/startPlayer.png"));
        }
    }

    /**
     * Sets the bubble of the God in the GUI of the current client.
     * @param imageView Variable that represents the assigned image of the God in JavaFX.
     * @param god Variable that represents the name of the God.
     */
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

    /**
     * Sets the bubble of the player to indicate who is the current player in the GUI.
     * @param player Variable that represents the current player of the turn.
     */
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

    /**
     *Sets the current player of the turn in the GUI.
     * @param str Variable that represents the string of the name of the current player.
     */
    public void setCurrentPlayer(String str) {
        currentPlayer.setText(str);
    }

    /**
     * Opens the instructions of the Gods in the GUI.
     */
    public void openInstruction() {
        mainHBox.setDisable(true);
        instructionPane.setDisable(false);
        instructionPane.setVisible(true);
        loadFirstPage();
    }

    /**
     * Closes the instruction of the Gods in the GUI.
     */
    public void closeInstruction() {
        mainHBox.setDisable(false);
        instructionPane.setDisable(true);
        instructionPane.setVisible(false);
    }

    /**
     * Loads the first page of the instruction of the Gods in the GUI.
     */
    public void loadFirstPage() {
        leftArrow.setVisible(false);
        leftArrow.setDisable(true);
        rightArrow.setDisable(false);
        rightArrow.setVisible(true);
        instructionPage.setImage(new Image("/components/Rules1.png"));
    }

    /**
     * Loads the second page of the instruction of the Gods in the GUI.
     */
    public void loadSecondPage() {
        leftArrow.setDisable(false);
        leftArrow.setVisible(true);
        rightArrow.setVisible(false);
        rightArrow.setDisable(true);
        instructionPage.setImage(new Image("/components/Rules2.png"));

    }

    /**
     * Hides the Unchosen Gods in the GUI during the God Choice for the players.
     */
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

    /**
     * Shows the "choosing god" phase in the GUI.
     */
    private void setChooseTitle() {
        title.setImage(new Image("/components/choosingGod.png"));
    }


    /**
     * Handles the choice of the starting player in the GUI.
     * @param size Variable that changes the JavaFX of the starting player in the GUI.
     */
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

    /**
     * Sets a blinking cloud effect in the GUI of the player bubble.
     * @param node Variable that is used by JavaFX.
     */
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

    /**
     * Handles the fade-out transition for unchosen Gods.
     * @param node Variable that is used by JavaFX.
     */
    private void disappearGod(Node node) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setToValue(0);
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setNode(node);
        fade.play();
    }

    /**
     * Sets the Sender of the GUI messages.
     * @param sender Variable that is an <code>Sender</code> used for the client-GUI interactions.
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }

}

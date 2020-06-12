package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.messages.GodChosenMessage;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GodController extends Commuter {

    private boolean chosen = false;
    private int counter = 0;

    @FXML
    private GridPane cardGrid;

    @FXML
    private ImageView apollo;

    @FXML
    private ImageView artemis;

    @FXML
    private ImageView athena;

    @FXML
    private ImageView atlas;

    @FXML
    private ImageView demeter;

    @FXML
    private ImageView hephaestus;

    @FXML
    private ImageView hera;

    @FXML
    private ImageView hestia;

    @FXML
    private ImageView limus;

    @FXML
    private ImageView minotaur;

    @FXML
    private ImageView pan;

    @FXML
    private ImageView poseidon;

    @FXML
    private ImageView prometheus;

    @FXML
    private ImageView triton;

    @FXML
    private ImageView zeus;



    public void gridClicked(MouseEvent event) {

        int row = 0;
        int column = 0;
        ImageView imageView = null;

        if(event.getSource().equals(cardGrid)) {
            for(Node node : cardGrid.getChildren()) {
                if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                    row = GridPane.getRowIndex(node);
                    column = GridPane.getColumnIndex(node);
                    System.out.println("Clicked at row " + GridPane.getRowIndex(node) + ", column " + GridPane.getColumnIndex(node));
                    imageView = (ImageView) node;
                    break;
                }
            }
        }

        System.out.println(imageView.getId());
        super.getGuiLauncher().getClient().sendInput(imageView.getId().toUpperCase());
    }



    public void changeImage(String god) {

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

    }

    @FXML
    private Pane cloud0;

    @FXML
    private ImageView godCloud0;
    @FXML
    private Label playerName0;
    @FXML
    private Label challenger0;


    @FXML
    private Pane cloud1;

    @FXML
    private ImageView godCloud1;
    @FXML
    private Label playerName1;
    @FXML
    private Label challenger1;


    @FXML
    private Pane cloud2;

    @FXML
    private ImageView godCloud2;
    @FXML
    private Label playerName2;
    @FXML
    private Label challenger2;



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

    public void select0(MouseEvent event) {
        super.getGuiLauncher().getClient().sendInput(playerName0.getText());
    }

    public void select1(MouseEvent event) {
        super.getGuiLauncher().getClient().sendInput(playerName1.getText());
    }

    public void select2(MouseEvent event) {
        super.getGuiLauncher().getClient().sendInput(playerName2.getText());
    }

    public void setChosenGod(GodChosenMessage message) {

        for(Node node : cardGrid.getChildren()) {
            if (node instanceof ImageView) {
                if (node.getId().toUpperCase().equals(message.getGod())) {
                    node.setOpacity(0.5);
                    break;
                }
            }
        }

        if(message.getPlayer().equals(playerName0.getText())) {
            setCloud(godCloud0,message.getGod());
        }
        if(message.getPlayer().equals(playerName1.getText())) {
            setCloud(godCloud1,message.getGod());
        }
        if(cloud2.isVisible()) {
           if(message.getPlayer().equals(playerName2.getText())) {
               setCloud(godCloud2,message.getGod());
           }
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



}

package it.polimi.ingsw.view.gui.controllers;

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
    private ImageView Apollo;

    @FXML
    private ImageView Artemis;

    @FXML
    private ImageView Athena;

    @FXML
    private ImageView Atlas;

    @FXML
    private ImageView Demeter;

    @FXML
    private ImageView Hephaestus;

    @FXML
    private ImageView Hera;

    @FXML
    private ImageView Hestia;

    @FXML
    private ImageView Limus;

    @FXML
    private ImageView Minotaur;

    @FXML
    private ImageView Pan;

    @FXML
    private ImageView Poseidon;

    @FXML
    private ImageView Prometheus;

    @FXML
    private ImageView Triton;

    @FXML
    private ImageView Zeus;



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
                    Image image;

                    switch(node.getId()) {
                        case "Apollo":
                            image = new Image("/godcards/chosen/ApolloChosen.png");
                            break;

                        case "Artemis":
                            image = new Image("/godcards/chosen/ArtemisChosen.png");
                            break;

                        case" Athena":
                            image = new Image("/godcards/chosen/AthenaChosen.png");
                            break;

                        case "Atlas":
                            image = new Image("/godcards/chosen/AtlasChosen.png");
                            break;

                        case "Demeter":
                            image = new Image("/godcards/chosen/DemeterChosen.png");
                            break;

                        case "Hephaestus":
                            image = new Image("/godcards/chosen/HephaestusChosen.png");
                            break;

                        case "Hera":
                            image = new Image("/godcards/chosen/HeraChosen.png");
                            break;

                        case "Hestia":
                            image = new Image("/godcards/chosen/HestiaChosen.png");
                            break;

                        case "Limus":
                            image = new Image("/godcards/chosen/LimusChosen.png");
                            break;

                        case "Minotaur":
                            image = new Image("/godcards/chosen/MinotaurChosen.png");
                            break;

                        case "Pan":
                            image = new Image("/godcards/chosen/PanChosen0.png");
                            break;

                        case "Poseidon":
                            image = new Image("/godcards/chosen/PoseidonChosen.png");
                            break;

                        case "Prometheus":
                            image = new Image("/godcards/chosen/PrometheusChosen.png");
                            break;

                        case "Triton":
                            image = new Image("/godcards/chosen/TritonChosen.png");
                            break;

                        case "Zeus":
                            image = new Image("/godcards/chosen/ZeusChosen.png");
                            break;

                        default:
                            image = ((ImageView) node).getImage();
                            break;
                    }

                    ((ImageView) node).setImage(image);
                }
            }

        }


        if(!chosen) {

        }

        /*else {
            imageView.setDisable(true);
            imageView.setVisible(false);
        }

         */


    }

    @FXML
    private Pane cloud0;

    @FXML
    private Label playerName0;

    @FXML
    private Label god0;


    @FXML
    private Pane cloud1;

    @FXML
    private Label playerName1;

    @FXML
    private Label god1;


    @FXML
    private Pane cloud2;

    @FXML
    private Label playerName2;

    @FXML
    private Label god2;



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



}

package it.polimi.ingsw.view.gui.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GodController {

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
        changeImage(imageView);
        counter++;
        checkCounter(imageView);
    }

    public void checkCounter(ImageView imageView) {
        if(counter==2) {
            setChosen(true);
        }
    }



    public void changeImage(ImageView imageView) {
        if(!chosen) {
            if(imageView.getId().equals("Apollo")) {
                Image image = new Image("/godcards/chosen/ApolloChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Artemis")) {
                Image image = new Image("/godcards/chosen/ArtemisChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Athena")) {
                Image image = new Image("/godcards/chosen/AthenaChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Atlas")) {
                Image image = new Image("/godcards/chosen/AtlasChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Demeter")) {
                Image image = new Image("/godcards/chosen/DemeterChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Hephaestus")) {
                Image image = new Image("/godcards/chosen/HephaestusChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Hera")) {
                Image image = new Image("/godcards/chosen/HeraChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Hestia")) {
                Image image = new Image("/godcards/chosen/HestiaChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Limus")) {
                Image image = new Image("/godcards/chosen/LimusChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Minotaur")) {
                Image image = new Image("/godcards/chosen/MinotaurChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Pan")) {
                Image image = new Image("/godcards/chosen/PanChosen0.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Poseidon")) {
                Image image = new Image("/godcards/chosen/PoseidonChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Prometheus")) {
                Image image = new Image("/godcards/chosen/PrometheusChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Triton")) {
                Image image = new Image("/godcards/chosen/TritonChosen.png");
                imageView.setImage(image);
            }
            if(imageView.getId().equals("Zeus")) {
                Image image = new Image("/godcards/chosen/ZeusChosen.png");
                imageView.setImage(image);
            }
        } else {
            imageView.setDisable(true);
            imageView.setVisible(false);
        }


    }

    public void setChosen(Boolean bool) {
        this.chosen = bool;
    }





}

package it.polimi.ingsw.view.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GodController {

    @FXML
    private GridPane cardGrid;

    @FXML
    private ImageView apolloCard;

    @FXML
    private ImageView artemisCard;

    @FXML
    private ImageView athenaCard;

    @FXML
    private ImageView atlasCard;

    @FXML
    private ImageView demeterCard;

    @FXML
    private ImageView hephaestusCard;

    @FXML
    private ImageView heraCard;

    @FXML
    private ImageView hestiaCard;

    @FXML
    private ImageView limusCard;

    @FXML
    private ImageView minotaurCard;

    @FXML
    private ImageView panCard;

    @FXML
    private ImageView poseidonCard;

    @FXML
    private ImageView prometheusCard;

    @FXML
    private ImageView tritonCard;

    @FXML
    private ImageView zeusCard;



    public void gridClicked(MouseEvent event) {

        for(Node node : cardGrid.getChildren()) {
            if (node.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())) {
                System.out.println("Clicked at row " + GridPane.getRowIndex(node) + ", column " + GridPane.getColumnIndex(node));
            }
        }

    }


    public void changeImage(ImageView imageView) {

    }





}

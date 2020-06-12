package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BoardController extends Commuter {

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
                    }
                }
            }
        }



    }

}

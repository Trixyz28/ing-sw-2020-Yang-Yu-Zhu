package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.cli.BoardView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI implements Ui {

    private String playerName;
    private String input;
    private Launcher launcher;

    public GUI(Launcher launcher) {
        this.launcher = launcher;
    }


    @Override
    public void showMessage(String str) {


    }


    @Override
    public void showBoard(BoardView boardView) {

    }

    @Override
    public String getInput() {
        return null;
    }


    public void receiveInput(String str) {
        input = str;
    }


    public Launcher getLauncher() {
        return launcher;
    }

}

package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.cli.BoardView;
import it.polimi.ingsw.view.gui.controllers.LoadingController;
import it.polimi.ingsw.view.gui.controllers.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI implements Ui {

    private String playerName;
    private String input;
    private boolean waitingMsg = false;
    private Launcher launcher;

    public GUI(Launcher launcher) {
        this.launcher = launcher;
    }


    @Override
    public void showMessage(String str) {
        System.out.println(str);

        if(launcher.getSceneIndex()==0) {
            StartController startController = launcher.getAllScenes().get(0).getController();
            startController.getCommand(str);
        }


        if(launcher.getSceneIndex()==1) {
            LoadingController loadingController = launcher.getAllScenes().get(1).getController();
            loadingController.getCommand(str);
        }
    }


    @Override
    public void showBoard(BoardView boardView) {

    }

    @Override
    public String getInput() {
        waitingMsg = false;
        return input;
    }


    public void setInput(String str) {
        input = str;
        waitingMsg = true;
    }


    public Launcher getLauncher() {
        return launcher;
    }

    public void print() {
        System.out.println("print");
    }

}

package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.controllers.LoadingController;
import it.polimi.ingsw.view.gui.controllers.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;


public class GuiHandler {

    private GUI gui;
    private Scene scene;
    private boolean active = false;

    private StartController startController;
    private LoadingController loadingController;



    public GuiHandler(GUI gui) {
        this.gui = gui;
    }

    public void initControllers() {
        startController = gui.getLauncher().getAllScenes().get(0).getController();
        StartController.setGui(gui);
        loadingController = gui.getLauncher().getAllScenes().get(1).getController();
        LoadingController.setGUI(gui);


    }





}

package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.controllers.LoadingController;
import it.polimi.ingsw.view.gui.controllers.StartController;
import javafx.scene.Scene;


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
        startController = gui.getGuiLauncher().getAllScenes().get(0).getController();
        StartController.setGui(gui);
        loadingController = gui.getGuiLauncher().getAllScenes().get(1).getController();
        LoadingController.setGUI(gui);


    }





}

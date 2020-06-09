package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.Ui;
import it.polimi.ingsw.view.cli.BoardView;
import it.polimi.ingsw.view.gui.controllers.LoadingController;
import it.polimi.ingsw.view.gui.controllers.StartController;

public class GUI implements Ui {

    private String playerName;
    private String input;
    private boolean waitingMsg = false;
    private GuiLauncher guiLauncher;

    public GUI(GuiLauncher guiLauncher) {
        this.guiLauncher = guiLauncher;
    }


    @Override
    public void showMessage(String str) {
        System.out.println(str);

        if(guiLauncher.getSceneIndex()==0) {
            StartController startController = guiLauncher.getAllScenes().get(0).getController();
            startController.getCommand(str);
        }


        if(guiLauncher.getSceneIndex()==1) {
            LoadingController loadingController = guiLauncher.getAllScenes().get(1).getController();
            loadingController.getCommand(str);
        }
    }


    @Override
    public void showBoard(BoardView boardView) {

    }

    @Override
    public String getInput() {
        if(waitingMsg) {
            return input;
        }
        return null;
    }


    public void setInput(String str) {
        input = str;
        waitingMsg = true;
    }


    public GuiLauncher getGuiLauncher() {
        return guiLauncher;
    }

    public void print() {
        System.out.println("print");
    }

}

package it.polimi.ingsw;

import it.polimi.ingsw.view.cli.CliLauncher;
import it.polimi.ingsw.view.gui.GuiLauncher;

import javafx.application.Application;


public class ClientApp {

    public static void main(String[] args) {

        if(args.length==0) {
            Application.launch(GuiLauncher.class);
        } else {
            CliLauncher cliLauncher = new CliLauncher();
            cliLauncher.start();
        }
    }

}

package it.polimi.ingsw;


import it.polimi.ingsw.view.cli.CliLauncher;
import it.polimi.ingsw.view.gui.GuiLauncher;
import javafx.application.Application;


public class ClientApp {

    public static void main(String[] args) {

        CliLauncher cliLauncher;


        if(args.length==0) {
            Application.launch(GuiLauncher.class);
        } else {
            cliLauncher = new CliLauncher();
            cliLauncher.start();
        }

    }

}

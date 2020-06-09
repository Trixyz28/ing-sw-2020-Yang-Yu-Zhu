package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.view.cli.CliLauncher;
import it.polimi.ingsw.view.gui.GuiLauncher;
import javafx.application.Application;

import java.io.IOException;


public class ClientApp {

    public static void main(String[] args) {

        CliLauncher cliLauncher;
        GuiLauncher guiLauncher;

        try {
            if(args.length==0) {
                cliLauncher = new CliLauncher();
                cliLauncher.start();
            } else {
                guiLauncher = new GuiLauncher();
                Application.launch(GuiLauncher.class);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}

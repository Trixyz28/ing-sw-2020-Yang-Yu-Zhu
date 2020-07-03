package it.polimi.ingsw;

import it.polimi.ingsw.view.cli.CliLauncher;
import it.polimi.ingsw.view.gui.GuiLauncher;

import javafx.application.Application;

/**
 * Main class for the client application.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */
public class ClientApp {

    /**
     * Main class which starts the client application.
     * @param args Variable read from command line, if equal to <code>null</code> the GUI will be launched, otherwise it will active the CLI.
     */
    public static void main(String[] args) {

        if(args.length==0) {
            Application.launch(GuiLauncher.class);
        } else {
            CliLauncher cliLauncher = new CliLauncher();
            cliLauncher.start();
        }
    }

}

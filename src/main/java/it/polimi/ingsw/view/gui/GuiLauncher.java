package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.Sender;
import it.polimi.ingsw.view.gui.controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Class that helps the start and implementation of the GUI of the game.
 * @author GC44
 * @version 1.0
 * @since 1.0
 */

public class GuiLauncher extends Application {

    private Stage stage;
    private Scene scene;
    private final GUI gui;
    private final Sender sender;

    /**
     * Creates a <code>GuiLauncher</code> with the specified attributes.
     */
    public GuiLauncher() {
        this.gui = new GUI();
        this.sender = new Sender();
    }

    /**
     * Creates and sets the start window.
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        this.stage.setResizable(false);
        this.stage.setTitle("Santorini");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Start.fxml"));

        Parent root = loader.load();
        StartController startController = loader.getController();
        startController.setGuiLauncher(this);
        startController.setEffects();

        this.scene = new Scene(root);
        gui.setStage(stage);
        scene.setCursor(new ImageCursor(new Image("components/Cursor.png")));

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the GUI at the start of the application.
     */
    public void appStarted() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ServerSetting.fxml"));
        try {
            scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginController loginController = loader.getController();
        loginController.setEffects();
        gui.setLoginController(loginController);
        loginController.setGui(this.gui);
        setupGUI();
    }

    /**
     * Sets up the GUI for the game.
     */
    public void setupGUI() {
        gui.setSender(sender);
        gui.setStage(stage);
        gui.initialize();
    }

}

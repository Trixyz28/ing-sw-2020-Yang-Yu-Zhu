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


public class GuiLauncher extends Application {

    private Stage stage;
    private Scene scene;
    private final GUI gui;
    private final Sender sender;


    public GuiLauncher() {
        this.gui = new GUI();
        this.sender = new Sender();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        this.stage.setResizable(false);
        this.stage.setTitle("Santorini");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Start.fxml"));

        Parent root = loader.load();
        StartController startController = loader.getController();
        startController.setGuiLauncher(this);
        startController.setGlow();

        this.scene = new Scene(root);
        gui.setStage(stage);
        scene.setCursor(new ImageCursor(new Image("components/Cursor.png")));

        stage.setScene(scene);
        stage.show();
    }

    public void appStarted() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ServerSetting.fxml"));
        try {
            scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginController loginController = loader.getController();
        gui.setLoginController(loginController);
        loginController.setGui(this.gui);
        setupGUI();
    }


    public void setupGUI() {
        gui.setSender(sender);
        gui.setStage(stage);
        gui.initialize();
    }

}

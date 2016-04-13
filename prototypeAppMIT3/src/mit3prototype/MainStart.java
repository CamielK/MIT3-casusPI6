/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mit3prototype;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class MainStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("GUI/GUI.fxml"));


        Scene scene = new Scene(root, 1100, 650);

        stage.setTitle("Movie predictor");
        stage.setScene(scene);
        stage.show();
    }
}

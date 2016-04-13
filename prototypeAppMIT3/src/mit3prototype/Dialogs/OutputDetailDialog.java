package mit3prototype.Dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by Camiel on 13-Apr-16.
 */
public class OutputDetailDialog {

    public OutputDetailDialog (Stage parentStage, String[][] detailsArray) {

        //main box to add items to
        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.CENTER_LEFT);

        //create dialog header
        Text dialogHeader = new Text("Multiple linear regression details:");
        dialogHeader.setStyle("-fx-font-size: 24px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058;");
        mainBox.getChildren().add(dialogHeader);

        //add details to main box
        for (String[] row : detailsArray) {
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.TOP_LEFT);

            Text textHeader = new Text(row[0]);
            textHeader.setWrappingWidth(210);
            textHeader.setStyle("-fx-font-size: 18px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058;");

            Text textBody = new Text(row[1]);

            hbox.getChildren().addAll(textHeader, textBody);
            mainBox.getChildren().addAll(hbox);
        }


        //create scrollpane
        ScrollPane scrollView = new ScrollPane();
        scrollView.setPrefSize(500,500);
        scrollView.setContent(mainBox);

        //button to close dialog
        final Button okay = new Button("Okay, got it");
        okay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) okay.getScene().getWindow();
                stage.close();
            }
        });


        //create and show new dialog with error messages
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("MLR details");
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(scrollView, okay).
                alignment(Pos.CENTER).padding(new Insets(5)).build()));
        dialogStage.showAndWait();
    }


}
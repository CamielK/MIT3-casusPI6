package inputControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camiel on 06-Apr-16.
 */
public class RemoveDialog {
    private static boolean remove;

    public boolean getRemoveDialog(String theme, Stage parentStage) {
        //create confirmation text
        Text msg = new Text("Are you sure you want to remove the selected " + theme + "?");
        msg.setStyle("-fx-fill: FIREBRICK; -fx-font-weight: bold; -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        //separate text from buttons
        Separator sep = new Separator();
        sep.setPrefHeight(30);
        sep.setVisible(false);

        //create confirm button
        final Button confirm = new Button("Yes, remove " + theme + "");
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) confirm.getScene().getWindow();
                stage.close();
                RemoveDialog.remove = true;
            }
        });

        //create button separator
        Separator btnSep = new Separator();
        btnSep.setPrefWidth(20);
        btnSep.setVisible(false);

        //create cancel button
        final Button cancel = new Button("No, keep " + theme + "");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) cancel.getScene().getWindow();
                stage.close();
                RemoveDialog.remove = false;
            }
        });

        //create button box using the created buttons
        HBox buttons = new HBox( );
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(confirm,btnSep,cancel);

        List<Node> nodes = new ArrayList<Node>();
        nodes.add(msg);
        nodes.add(sep);
        nodes.add(buttons);

        //create a new stage with the created elements to show a dialog. waits for user input
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL); //block input events from being delivered to parent
        dialogStage.initStyle(StageStyle.UNDECORATED); //remove default border
        dialogStage.initOwner(parentStage); //set focus on dialog
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(nodes).
                alignment(Pos.CENTER).padding(new Insets(50)).build()));
        dialogStage.showAndWait();

        return remove;
    }
}

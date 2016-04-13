package mit3prototype.inputControllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by user on 06-Apr-16.
 */
public class Continent {

    private static String addedContinent;
    private static Stage parentStage;

    public String addContinent(ComboBox<String> comboBox, final String theme) {
        final ObservableList<String> selectionModel = comboBox.getItems();
        this.parentStage = (Stage) comboBox.getScene().getWindow();

        //explanation message
        Text msg = new Text("Select the "+theme+" you wish to add:");

        //error message
        final Text errorComponent = new Text();
        errorComponent.setStyle("-fx-fill: FIREBRICK; -fx-font-weight: bold; -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        //continent select combo box
        final ComboBox<String> selectBox = new ComboBox<String>();
        selectBox.getItems().addAll(
                "English",
                "Spanish",
                "European",
                "Arabic",
                "Asian",
                "Other"
        );

        //separate combo box from buttons
        Separator sep = new Separator();
        sep.setPrefHeight(30);
        sep.setVisible(false);

        //add button
        final Button add = new Button("Add " + theme);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (selectBox.getSelectionModel().isEmpty()) {
                    errorComponent.setText("Please select a "+theme+" or cancel..");
                }
                else {
                    boolean continentAlreadyAdded = false;
                    for (String item : selectionModel) {
                        if (item.equals(selectBox.getSelectionModel().getSelectedItem())) {
                            continentAlreadyAdded = true;
                        }
                    }
                    if (continentAlreadyAdded) {
                        errorComponent.setText(theme+" is already added..");
                    }
                    else {
                        Continent.addedContinent = selectBox.getSelectionModel().getSelectedItem();
                        Stage stage = (Stage) add.getScene().getWindow();
                        stage.close();
                    }
                }
            }
        });

        //create button separator
        Separator btnSep = new Separator();
        btnSep.setPrefWidth(20);
        btnSep.setVisible(false);

        //cancel button
        final Button cancel = new Button("Cancel");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Continent.addedContinent = "null";
                Stage stage = (Stage) cancel.getScene().getWindow();
                stage.close();
            }
        });

        //create button box using the created buttons
        HBox buttons = new HBox( );
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(add,btnSep,cancel);

        //create new dialog window and show components. waits for input.
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL); //block input events from being delivered to parent
        dialogStage.initStyle(StageStyle.UNDECORATED); //remove default border
        dialogStage.initOwner(parentStage); //set focus on dialog
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(msg,errorComponent,selectBox,sep,buttons).
                alignment(Pos.CENTER).padding(new Insets(25)).build()));
        dialogStage.showAndWait();

        return addedContinent;
    }
}

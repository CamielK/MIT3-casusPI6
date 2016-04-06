package inputControllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//this program uses the csvjdbc library to work directly with csv files. get JAR at: http://csvjdbc.sourceforge.net/
import org.relique.jdbc.csv.CsvDriver;

/**
 * Created by Camiel on 06-Apr-16.
 */
public class DirectorController {

    private static String addedDirector;
    private static Stage parentStage;
    private static boolean searching;
    private static ComboBox<String> searchBox;
    private static ProgressIndicator indicator;
    private static Text errorComponent;

    public String addDirector(ComboBox<String> comboBox) {
        final ObservableList<String> selectionModel = comboBox.getItems();
        this.parentStage = (Stage) comboBox.getScene().getWindow();

        //explanation message
        Text msg = new Text("Search the director you would like to add");

        //error message
        errorComponent = new Text();
        errorComponent.setStyle("-fx-fill: FIREBRICK; -fx-font-weight: bold; -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        //search area
        final HBox searchArea = new HBox( );
        searchArea.setAlignment(Pos.CENTER);
        searchArea.setPrefHeight(50);
        searchArea.setMinHeight(50);
        searchArea.setMaxHeight(50);

        //search progress indicator
        indicator = new ProgressIndicator();
        indicator.setVisible(false);


        //search box
        searchBox = new ComboBox<String>();
        searchBox.setPrefWidth(300);
        searchBox.setEditable(true);
        searchBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                searchBox.show();
            }
        });
        searchBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                //System.out.println(searchBox.getEditor().getText());
                if (searchBox.getEditor().getText().length()>3) { //start query when keys are pressed and search string is bigger then 3 chars
                    //errorComponent.setText("");
                    indicator.setProgress(-1.0);
                    indicator.setVisible(true); //display search indicator
                    DirectorController.searching = true;
                    //searchBox.setDisable(true); //disable while searching
                    searchBox.getItems().clear(); //clear combo box
                    String searchString = searchBox.getEditor().getText();
                    new DirectorQuery(searchString).start();
                    //findDirector(searchBox.getEditor().getText()); //start query

                }
            }
        });

        searchArea.getChildren().addAll(indicator);

        //separate combo box from buttons
        Separator sep = new Separator();
        sep.setPrefHeight(30);
        sep.setVisible(false);

        //add button
        final Button add = new Button("Add director");
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                if (searchBox.getSelectionModel().isEmpty()) {
                    errorComponent.setText("Please select a director or cancel..");
                }
                else {
                    boolean directorAlreadyAdded = false;
                    for (String item : selectionModel) {
                        if (item.equals(searchBox.getSelectionModel().getSelectedItem())) {
                            directorAlreadyAdded = true;
                        }
                    }
                    if (directorAlreadyAdded) {
                        errorComponent.setText("Director is already added..");
                    }
                    else {
                        DirectorController.addedDirector = searchBox.getSelectionModel().getSelectedItem();
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
                DirectorController.addedDirector = "null";
                Stage stage = (Stage) cancel.getScene().getWindow();
                stage.close();
            }
        });

        //create button box using the created buttons
        final HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(add,btnSep,cancel);

        //create new dialog window and show components. waits for input.
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL); //block input events from being delivered to parent
        dialogStage.initStyle(StageStyle.UNDECORATED); //remove default border
        dialogStage.initOwner(parentStage); //set focus on dialog
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(msg,errorComponent,searchArea,searchBox,sep,buttons).
                alignment(Pos.CENTER).padding(new Insets(50)).build()));
        dialogStage.showAndWait();

        return addedDirector;
    }


    //this sub class does a query in a new thread to find any directors that contain the search string.
    public class DirectorQuery extends Thread {

        private String searchString;

        public DirectorQuery (String searchString) {
            this.searchString = searchString.toLowerCase();
        }

        public void run() {
            ObservableList<String> queryResults = FXCollections.observableArrayList();

            //query
            try {
                //establish static connection for all threads
                Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + "src/data");

                //do the query
                conn.setAutoCommit(false);
                PreparedStatement stmt = conn.prepareStatement("SELECT Director FROM ratingCalcDatabase");
                ResultSet directorFields = stmt.executeQuery();

                //check results
                while(directorFields.next()) {
                    //get next field
                    String directorField = directorFields.getString("Director");

                    if (directorField.toLowerCase().contains(searchString)) {
                        //split multiple directors
                        List<String> directors = new ArrayList<String>();
                        directorField = directorField.replaceAll("'", "''");
                        while (directorField.contains(",")) {
                            //get last name in field
                            int lastDirectorIndex = directorField.lastIndexOf(',');
                            String director = directorField.substring(lastDirectorIndex+2);
                            directorField = directorField.substring(0,lastDirectorIndex);

                            directors.add(director);
                        }
                        directors.add(directorField);

                        for (String director : directors) {
                            if (director.toLowerCase().contains(searchString)) {
                                boolean alreadyAdded = false;
                                for (String result : queryResults) {
                                    if (result.equals(director)) {
                                        alreadyAdded = true;
                                    }
                                }
                                if (!alreadyAdded) {
                                    queryResults.add(director);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {e.printStackTrace();}

            //finish searching
            showSearchResults(queryResults, searchString);
        }
    }

    public void showSearchResults(final ObservableList<String> queryResults, final String searchString) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                searching = false;
                indicator.setVisible(false);
                searchBox.setDisable(false);
                if (queryResults.size() > 0) {
                    searchBox.setItems(queryResults);
                    searchBox.show();
                } else {
                    errorComponent.setText("No directors found for: '" + searchString + "'. Please try again");
                }
            }
        });

    }
}

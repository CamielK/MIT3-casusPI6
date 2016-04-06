package GUI;

import inputControllers.ContinentController;
import inputControllers.GenreController;
import inputControllers.RemoveDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;
import java.util.List;


/**
 * Created by Camiel on 02-Apr-16.
 */
public class GUIcontroller implements Initializable {

    //main grid
    @FXML private GridPane grid;

    // >>> checkbox components <<<
    @FXML private CheckBox releaseCbx;
    @FXML private CheckBox runtimeCbx;
    @FXML private CheckBox mpaaCbx;
    @FXML private CheckBox budgetCbx;
    @FXML private CheckBox genreCbx;
    @FXML private CheckBox directorCbx;
    @FXML private CheckBox writerCbx;
    @FXML private CheckBox castCbx;
    @FXML private CheckBox languageCbx;
    @FXML private CheckBox countryCbx;

    // >>> label components <<<
    @FXML private Label releaseLbl;
    @FXML private Label runtimeLbl;
    @FXML private Label mpaaLbl;
    @FXML private Label budgetLbl;
    @FXML private Label genreLbl;
    @FXML private Label directorLbl;
    @FXML private Label writerLbl;
    @FXML private Label castLbl;
    @FXML private Label languageLbl;
    @FXML private Label countryLbl;


    // >>> input components <<<
    @FXML private TextField releaseInput;
    @FXML private TextField runtimeInput;
    @FXML private ComboBox<String> mpaaInput;
    @FXML private TextField budgetInput;
    @FXML private ComboBox<String> genreCombo; @FXML private HBox genreBox;
    @FXML private ListView<String> directorView; @FXML private VBox directorBox;
    @FXML private ListView<String> writerView; @FXML private VBox writerBox;
    @FXML private ListView<String> castView; @FXML private VBox castBox;
    @FXML private ComboBox<String> languageCombo; @FXML private HBox languageBox;
    @FXML private ComboBox<String> countryCombo; @FXML private HBox countryBox;

    // >>> input controllers <<<
    private GenreController genreController = new GenreController();
    private RemoveDialog removeDialog = new RemoveDialog();
    private ContinentController continentController = new ContinentController();

    // >>> output components <<<
    @FXML private ProgressBar progressBar;


    // init method is run when fxml is finished loading
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        setOutputComponentsVisible(false);
    }




    // >>> checkbox actions <<<
    // when checkboxes are disabled, the input fields and label are disabled and vice versa
    @FXML protected void releaseCbxAction() {
        if (releaseCbx.isSelected()) {releaseLbl.setDisable(false); releaseInput.setDisable(false);}
        else {releaseLbl.setDisable(true); releaseInput.setDisable(true);}
    }
    @FXML protected void runtimeCbxAction() {
        if (runtimeCbx.isSelected()) {runtimeLbl.setDisable(false); runtimeInput.setDisable(false);}
        else {runtimeLbl.setDisable(true); runtimeInput.setDisable(true);}
    }
    @FXML protected void mpaaCbxAction() {
        if (mpaaCbx.isSelected()) {mpaaLbl.setDisable(false); mpaaInput.setDisable(false);}
        else {mpaaLbl.setDisable(true); mpaaInput.setDisable(true);}
    }
    @FXML protected void budgetCbxAction() {
        if (budgetCbx.isSelected()) {budgetLbl.setDisable(false); budgetInput.setDisable(false);}
        else {budgetLbl.setDisable(true); budgetInput.setDisable(true);}
    }
    @FXML protected void genreCbxAction() {
        if (genreCbx.isSelected()) {genreLbl.setDisable(false); genreCombo.setDisable(false); genreBox.setDisable(false);}
        else {genreLbl.setDisable(true); genreCombo.setDisable(true); genreBox.setDisable(true);}
    }
    @FXML protected void directorCbxAction() {
        if (directorCbx.isSelected()) {directorLbl.setDisable(false); directorView.setDisable(false); directorBox.setDisable(false);}
        else {directorLbl.setDisable(true); directorView.setDisable(true); directorBox.setDisable(true);}
    }
    @FXML protected void writerCbxAction() {
        if (writerCbx.isSelected()) {writerLbl.setDisable(false); writerView.setDisable(false); writerBox.setDisable(false);}
        else {writerLbl.setDisable(true); writerView.setDisable(true); writerBox.setDisable(true);}
    }
    @FXML protected void castCbxAction() {
        if (castCbx.isSelected()) {castLbl.setDisable(false); castView.setDisable(false); castBox.setDisable(false);}
        else {castLbl.setDisable(true); castView.setDisable(true); castBox.setDisable(true);}
    }
    @FXML protected void languageCbxAction() {
        if (languageCbx.isSelected()) {languageLbl.setDisable(false); languageCombo.setDisable(false); languageBox.setDisable(false);}
        else {languageLbl.setDisable(true); languageCombo.setDisable(true); languageBox.setDisable(true);}
    }
    @FXML protected void countryCbxAction() {
        if (countryCbx.isSelected()) {countryLbl.setDisable(false); countryCombo.setDisable(false); countryBox.setDisable(false);}
        else {countryLbl.setDisable(true); countryCombo.setDisable(true); countryBox.setDisable(true);}
    }



    // >>> add and remove button handlers for combo boxes <<<
    @FXML protected void addGenre(ActionEvent event) {
        String addedGenre = genreController.addGenre(genreCombo);
        if (!addedGenre.equals("null") && addedGenre!=null) {
            genreCombo.getItems().add(addedGenre);
            genreCombo.getSelectionModel().select(0);
        }
    }
    @FXML protected void removeGenre(ActionEvent event) {
        if (genreCombo.getItems().size()>0 && removeDialog.getRemoveDialog("genre", (Stage) genreCombo.getScene().getWindow())) {
            genreCombo.getItems().remove(genreCombo.getSelectionModel().getSelectedIndex());
            genreCombo.getSelectionModel().select(0);
        }
    }
    @FXML protected void addDirector(ActionEvent event) {

    }
    @FXML protected void removeDirector(ActionEvent event) {

    }
    @FXML protected void addWriter(ActionEvent event) {

    }
    @FXML protected void removeWriter(ActionEvent event) {

    }
    @FXML protected void addCast(ActionEvent event) {

    }
    @FXML protected void removeCast(ActionEvent event) {

    }
    @FXML protected void addLanguage(ActionEvent event) {
        String addedLanguage = continentController.addContinent(languageCombo,"language");
        if (!addedLanguage.equals("null") && addedLanguage!=null) {
            languageCombo.getItems().add(addedLanguage);
            languageCombo.getSelectionModel().select(0);
        }
    }
    @FXML protected void removeLanguage(ActionEvent event) {
        if (languageCombo.getItems().size()>0 && removeDialog.getRemoveDialog("language", (Stage) languageCombo.getScene().getWindow())) {
            languageCombo.getItems().remove(languageCombo.getSelectionModel().getSelectedIndex());
            languageCombo.getSelectionModel().select(0);
        }
    }
    @FXML protected void addCountry(ActionEvent event) {
        String addedCountry = continentController.addContinent(countryCombo,"country");
        if (!addedCountry.equals("null") && addedCountry!=null) {
            countryCombo.getItems().add(addedCountry);
            countryCombo.getSelectionModel().select(0);
        }
    }
    @FXML protected void removeCountry(ActionEvent event) {
        if (countryCombo.getItems().size()>0 && removeDialog.getRemoveDialog("country", (Stage) countryCombo.getScene().getWindow())) {
            countryCombo.getItems().remove(countryCombo.getSelectionModel().getSelectedIndex());
            countryCombo.getSelectionModel().select(0);
        }
    }




    // >>> submit button action <<<
    //checks if there are no invalid form entries and starts the calculation if there are no errors.
    @FXML protected void submitForm(ActionEvent event) {

        //reset label colors due to previous submits
        releaseLbl.setStyle("-fx-text-fill: #333333;");
        runtimeLbl.setStyle("-fx-text-fill: #333333;");
        mpaaLbl.setStyle("-fx-text-fill: #333333;");
        budgetLbl.setStyle("-fx-text-fill: #333333;");
        genreLbl.setStyle("-fx-text-fill: #333333;");
        directorLbl.setStyle("-fx-text-fill: #333333;");
        writerLbl.setStyle("-fx-text-fill: #333333;");
        castLbl.setStyle("-fx-text-fill: #333333;");
        languageLbl.setStyle("-fx-text-fill: #333333;");
        countryLbl.setStyle("-fx-text-fill: #333333;");

        //makes a list of all error messages that should be displayed
        java.util.List<String> errorMessages = new ArrayList<String>();

        //add input data to a list. if input is disabled "Unused predictor" is added to this list. format: (releaseYear, mpaaRating, runtime, budget, genre, driector, writer, cast, language, country)
        List<String> inputData = new ArrayList<String>();

        // >>> check form input <<<
        //check release year input
        if (releaseCbx.isSelected()) {
            try {
                int year = Integer.parseInt(releaseInput.getText());
                if (year < 1990 || year > 2020) { errorMessages.add("Invalid release year. Please enter a year between 1990 and 2020 or uncheck this predictor."); }
                else { inputData.add(releaseInput.getText()); }
            } catch (Exception e) { errorMessages.add("Invalid release year. Please enter a year using only integers."); }
        } else { inputData.add("Unused predictor"); }

        //check runtime
        if (runtimeCbx.isSelected()) {
            try {
                int runtime = Integer.parseInt(runtimeInput.getText());
                if (runtime < 45 || runtime > 240) { errorMessages.add("Invalid runtime. Please enter a runtime between 45 and 240 minutes or uncheck this predictor."); }
                else { inputData.add(runtimeInput.getText()); }
            } catch (Exception e) { errorMessages.add("Invalid runtime. Please enter runtime using only integers."); }
        }  else { inputData.add("Unused predictor"); }

        //check mpaa rating
        if (mpaaCbx.isSelected() &&  mpaaInput.getSelectionModel().isEmpty()) {
            errorMessages.add("Invalid MPAA rating. please select an MPAA rating or uncheck this predictor.");
        }
        else if (mpaaCbx.isSelected()) {
            inputData.add(mpaaInput.getSelectionModel().selectedItemProperty().toString());
        } else { inputData.add("Unused predictor"); }

        //check budget
        if (budgetCbx.isSelected()) {
            try {
                long budget = Long.parseLong(budgetInput.getText());
                if (budget < 0) { errorMessages.add("Invalid budget. Please enter a budget bigger then 0 or uncheck this predictor."); }
                else { inputData.add(budgetInput.getText()); }
            } catch (Exception e) { errorMessages.add("Invalid budget. Please enter a budget using only integers."); }
        }  else { inputData.add("Unused predictor"); }

//        //check genre
//        if (genreCbx.isSelected()) {
//            if (true) { errorMessages.add("Please add at least 1 genre or uncheck this predictor"); }
//            //if (genres.getSelectedItem().toString().contains("Add a genre")) { errorMessages.add("Please add at least 1 genre or uncheck this predictor"); }
//            else {
//                //genresList.
//            }
//        }  else { inputData.add("Unused predictor"); }
//
//        //check director
//        if (directorCbx.isSelected() && directors.getSelectedItem().toString().contains("Add a genre")) {
//            errorMessages.add("Please add at least 1 genre or uncheck this predictor");
//        }  else { inputData.add("Unused predictor"); }
//
//        //check writer
//        if (writerCbx.isSelected() && writers.getSelectedItem().toString().contains("Add a genre")) {
//            errorMessages.add("Please add at least 1 genre or uncheck this predictor");
//        }  else { inputData.add("Unused predictor"); }
//
//        //check cast
//        if (castCbx.isSelected() && cast.getSelectedItem().toString().contains("Add a genre")) {
//            errorMessages.add("Please add at least 1 genre or uncheck this predictor");
//        }  else { inputData.add("Unused predictor"); }
//
//        //check language
//        if (languageCbx.isSelected() && languages.getSelectedItem().toString().contains("Add a genre")) {
//            errorMessages.add("Please add at least 1 genre or uncheck this predictor");
//        }  else { inputData.add("Unused predictor"); }
//
//        //check country
//        if (countryCbx.isSelected() && countrys.getSelectedItem().toString().contains("Add a country")) {
//            errorMessages.add("Please add at least 1 genre or uncheck this predictor");
//        }  else { inputData.add("Unused predictor"); }


        //display error messages if there are errors
        if (errorMessages.size() > 0) {


            //create error text and add it to a new text component. set style to look like error message
            String errorText = "";
            for (String error : errorMessages) {
                errorText += "- " + error + "\n"; //add each error message to a new line of the final text component


                //change style of labels to show incorrect variables
                if (error.contains("release")) { releaseLbl.setStyle("-fx-text-fill: FIREBRICK;"); }
                if (error.contains("runtime")) { runtimeLbl.setStyle("-fx-text-fill: FIREBRICK;"); }
                if (error.contains("MPAA")) { mpaaLbl.setStyle("-fx-text-fill: FIREBRICK;"); }
                if (error.contains("budget")) { budgetLbl.setStyle("-fx-text-fill: FIREBRICK;"); }
                if (error.contains("genre")) { genreLbl.setStyle("-fx-text-fill: FIREBRICK;"); }
                if (error.contains("director")) { directorLbl.setStyle("-fx-text-fill: FIREBRICK;"); }
                if (error.contains("writer")) { writerLbl.setStyle("-fx-text-fill: FIREBRICK;"); }
                if (error.contains("cast")) { castLbl.setStyle("-fx-text-fill: FIREBRICK;"); }
                if (error.contains("language")) { languageLbl.setStyle("-fx-text-fill: FIREBRICK;"); }
                if (error.contains("country")) { countryLbl.setStyle("-fx-text-fill: FIREBRICK;"); }




            }
            Text errorComponent = new Text(errorText);
            errorComponent.setStyle("-fx-fill: FIREBRICK; -fx-font-weight: bold; -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

            //button to close dialog
            final Button test = new Button("Okay, got it");
            test.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Stage stage = (Stage) test.getScene().getWindow();
                    stage.close();
                }
            });

            //create and show new dialog with error messages
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(VBoxBuilder.create().
                    children(errorComponent, test).
                    alignment(Pos.CENTER).padding(new Insets(5)).build()));
            dialogStage.show();
        }
        else {
            //submit form


//            ObservableList<String> items = FXCollections.observableArrayList("Aa","B","C","F","G");
//            genreView.setItems(items);
//
//            setOutputComponentsVisible(true);
        }
    }



    public void setOutputComponentsVisible(boolean outputVisible) {
        int componentsCount = grid.getChildren().size();
        for (int i = componentsCount-10; i < componentsCount; i++) {
            grid.getChildren().get(i).setVisible(outputVisible);
        }

    }

}

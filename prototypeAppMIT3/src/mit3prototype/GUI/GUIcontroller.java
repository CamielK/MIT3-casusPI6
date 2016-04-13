package mit3prototype.GUI;

import javafx.application.Platform;
import javafx.scene.text.Text;
import mit3prototype.Dialogs.HelpDialog;
import mit3prototype.Dialogs.OutputDetailDialog;
import mit3prototype.Dialogs.RemoveDialog;
import mit3prototype.calculators.*;
import mit3prototype.inputControllers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.*;
import java.util.List;


public class GUIcontroller implements Initializable {

    //class variables
    private static String[][] ratingDetailsArray;
    private static String[][] revenueDetailsArray;

    //main grid
    @FXML private static GridPane grid;

    // >>> checkbox components <<<
    @FXML private static CheckBox releaseCbx;
    @FXML private static CheckBox runtimeCbx;
    @FXML private static CheckBox mpaaCbx;
    @FXML private static CheckBox budgetCbx;
    @FXML private static CheckBox genreCbx;
    @FXML private static CheckBox directorCbx;
    @FXML private static CheckBox writerCbx;
    @FXML private static CheckBox castCbx;
    @FXML private static CheckBox languageCbx;
    @FXML private static CheckBox countryCbx;

    // >>> label components <<<
    @FXML private static Label releaseLbl;
    @FXML private static Label runtimeLbl;
    @FXML private static Label mpaaLbl;
    @FXML private static Label budgetLbl;
    @FXML private static Label genreLbl;
    @FXML private static Label directorLbl;
    @FXML private static Label writerLbl;
    @FXML private static Label castLbl;
    @FXML private static Label languageLbl;
    @FXML private static Label countryLbl;


    // >>> input components <<<
    @FXML private static TextField releaseInput;
    @FXML private static TextField runtimeInput;
    @FXML private static ComboBox<String> mpaaInput;
    @FXML private static TextField budgetInput;
    @FXML private static ComboBox<String> genreCombo; @FXML private HBox genreBox;
    @FXML private static ComboBox<String> directorCombo; @FXML private HBox directorBox;
    @FXML private static ComboBox<String> writerCombo; @FXML private HBox writerBox;
    @FXML private static ComboBox<String> castCombo; @FXML private HBox castBox;
    @FXML private static ComboBox<String> languageCombo; @FXML private HBox languageBox;
    @FXML private static ComboBox<String> countryCombo; @FXML private HBox countryBox;
    @FXML private static Button submitBtn;
    @FXML private static Button helpBtn;

    // >>> input controllers <<<
    private static Genre genreController = new Genre();
    private static RemoveDialog removeDialog = new RemoveDialog();
    private static Continent continentController = new Continent();
    private static Director directorController = new Director();
    private static Writer writerController = new Writer();
    private static Cast castController = new Cast();
    private static AverageRating avgRatingCalculator = new AverageRating();

    // >>> output components <<<
    @FXML private static Text progressStatus;
    @FXML private static ProgressIndicator progressIndicator;
    @FXML private static Label avgDirectorOutput;
    @FXML private static Label avgWriterOutput;
    @FXML private static Label avgCastOutput;
    @FXML private static Text ratingOutput;
    @FXML private static Text revenueOutput;


    // init method is run when fxml is finished loading
    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
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
        if (directorCbx.isSelected()) {directorLbl.setDisable(false); directorCombo.setDisable(false); directorBox.setDisable(false);}
        else {directorLbl.setDisable(true); directorCombo.setDisable(true); directorBox.setDisable(true);}
    }
    @FXML protected void writerCbxAction() {
        if (writerCbx.isSelected()) {writerLbl.setDisable(false); writerCombo.setDisable(false); writerBox.setDisable(false);}
        else {writerLbl.setDisable(true); writerCombo.setDisable(true); writerBox.setDisable(true);}
    }
    @FXML protected void castCbxAction() {
        if (castCbx.isSelected()) {castLbl.setDisable(false); castCombo.setDisable(false); castBox.setDisable(false);}
        else {castLbl.setDisable(true); castCombo.setDisable(true); castBox.setDisable(true);}
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
            genreCombo.show();
        }
    }
    @FXML protected void removeGenre(ActionEvent event) {
        if (genreCombo.getItems().size()>0 && removeDialog.getRemoveDialog("genre", (Stage) genreCombo.getScene().getWindow())) {
            genreCombo.getItems().remove(genreCombo.getSelectionModel().getSelectedIndex());
            genreCombo.getSelectionModel().select(0);
        }
    }
    @FXML protected void addDirector(ActionEvent event) {
        String addedDirector = directorController.addDirector(directorCombo);
        if (!addedDirector.equals("null") && addedDirector!=null) {
            directorCombo.getItems().add(addedDirector);
            directorCombo.getSelectionModel().select(0);
            directorCombo.show();
        }
    }
    @FXML protected void removeDirector(ActionEvent event) {
        if (directorCombo.getItems().size()>0 && removeDialog.getRemoveDialog("director", (Stage) directorCombo.getScene().getWindow())) {
            directorCombo.getItems().remove(directorCombo.getSelectionModel().getSelectedIndex());
            directorCombo.getSelectionModel().select(0);
        }
    }
    @FXML protected void addWriter(ActionEvent event) {
        String addedWriter = writerController.addWriter(writerCombo);
        if (!addedWriter.equals("null") && addedWriter!=null) {
            writerCombo.getItems().add(addedWriter);
            writerCombo.getSelectionModel().select(0);
            writerCombo.show();
        }
    }
    @FXML protected void removeWriter(ActionEvent event) {
        if (writerCombo.getItems().size()>0 && removeDialog.getRemoveDialog("writer", (Stage) writerCombo.getScene().getWindow())) {
            writerCombo.getItems().remove(writerCombo.getSelectionModel().getSelectedIndex());
            writerCombo.getSelectionModel().select(0);
        }
    }
    @FXML protected void addCast(ActionEvent event) {
        String addedCast = castController.addCast(castCombo);
        if (!addedCast.equals("null") && addedCast!=null) {
            castCombo.getItems().add(addedCast);
            castCombo.getSelectionModel().select(0);
            castCombo.show();
        }
    }
    @FXML protected void removeCast(ActionEvent event) {
        if (castCombo.getItems().size()>0 && removeDialog.getRemoveDialog("cast", (Stage) castCombo.getScene().getWindow())) {
            castCombo.getItems().remove(castCombo.getSelectionModel().getSelectedIndex());
            castCombo.getSelectionModel().select(0);
        }
    }
    @FXML protected void addLanguage(ActionEvent event) {
        String addedLanguage = continentController.addContinent(languageCombo,"language");
        if (!addedLanguage.equals("null") && addedLanguage!=null) {
            languageCombo.getItems().add(addedLanguage);
            languageCombo.getSelectionModel().select(0);
            languageCombo.show();
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
            countryCombo.show();
        }
    }
    @FXML protected void removeCountry(ActionEvent event) {
        if (countryCombo.getItems().size()>0 && removeDialog.getRemoveDialog("country", (Stage) countryCombo.getScene().getWindow())) {
            countryCombo.getItems().remove(countryCombo.getSelectionModel().getSelectedIndex());
            countryCombo.getSelectionModel().select(0);
        }
    }


    // >>> help button <<<
    //show help dialog with information about prototype app
    @FXML protected void help(ActionEvent event) {
        new HelpDialog((Stage) helpBtn.getScene().getWindow());
    }


    //Multiple linear regression output details buttons
    @FXML protected void ratingDetails(ActionEvent event) {
        if (ratingDetailsArray!=null) new OutputDetailDialog((Stage) helpBtn.getScene().getWindow(), ratingDetailsArray);
    }
    @FXML protected void revenueDetails(ActionEvent event) {
        if (revenueDetailsArray!=null) new OutputDetailDialog((Stage) helpBtn.getScene().getWindow(), revenueDetailsArray);
    }


    // >>> submit button action <<<
    //checks if there are no invalid form entries and starts the calculation if there are no errors.
    @FXML protected void submitForm(ActionEvent event) throws Exception {

        //reset label colors and other elements due to previous submits
        setOutputComponentsVisible(false);
        progressStatus.setText("Awaiting input...");
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
        ratingOutput.setText("...");
        revenueOutput.setText("...");
        avgDirectorOutput.setText("...");
        avgWriterOutput.setText("...");
        avgCastOutput.setText("...");


        //makes a list of all error messages that should be displayed
        List<String> errorMessages = new ArrayList<>();

        //add input data to a list. if input is disabled "Unused predictor" is added to this list. format: (releaseYear, runtime, mpaaRating, budget, genre, director, writer, cast, language, country)
        List<String> inputData = new ArrayList<>();
        List<Float> inputDataFloat = new ArrayList<>();

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
            inputData.add(mpaaInput.getSelectionModel().selectedItemProperty().getValue());
        } else { inputData.add("Unused predictor"); }

        //check budget
        if (budgetCbx.isSelected()) {
            try {
                long budget = Long.parseLong(budgetInput.getText());
                if (budget < 0 || budget > 1000000000) { errorMessages.add("Invalid budget. Please enter a budget between 0 and 1.000.000.000 or uncheck this predictor."); }
                else { inputData.add(budgetInput.getText()); }
            } catch (Exception e) { errorMessages.add("Invalid budget. Please enter a budget using only integers."); }
        }  else { inputData.add("Unused predictor"); }

        //check genre
        if (genreCbx.isSelected()) {
            if (genreCombo.getSelectionModel().isEmpty()) { errorMessages.add("Please add at least 1 genre or uncheck this predictor"); }
            else {
                String genreString = "";
                for (String genre : genreCombo.getItems()) { genreString = genreString + "," + genre; }
                inputData.add(genreString);
            }
        }  else { inputData.add("Unused predictor"); }

        //check director
        if (directorCbx.isSelected()) {
            if (directorCombo.getSelectionModel().isEmpty()) { errorMessages.add("Please add at least 1 director or uncheck this predictor"); }
            else {
                String directorString = "";
                for (String director : directorCombo.getItems()) { directorString = directorString + "," + director; }
                inputData.add(directorString);
            }
        }  else { inputData.add("Unused predictor"); }

        //check writer
        if (writerCbx.isSelected()) {
            if (writerCombo.getSelectionModel().isEmpty()) { errorMessages.add("Please add at least 1 writer or uncheck this predictor"); }
            else {
                String writerString = "";
                for (String writer : writerCombo.getItems()) { writerString = writerString + "," + writer; }
                inputData.add(writerString);
            }
        }  else { inputData.add("Unused predictor"); }

        //check cast
        if (castCbx.isSelected()) {
            if (castCombo.getSelectionModel().isEmpty()) { errorMessages.add("Please add at least 1 actor to the cast or uncheck this predictor"); }
            else {
                String castString = "";
                for (String actor : castCombo.getItems()) { castString = castString + "," + actor; }
                inputData.add(castString);
            }
        }  else { inputData.add("Unused predictor"); }

        //check language
        if (languageCbx.isSelected()) {
            if (languageCombo.getSelectionModel().isEmpty()) { errorMessages.add("Please add at least 1 language or uncheck this predictor"); }
            else {
                String languageString = "";
                for (String language : languageCombo.getItems()) { languageString = languageString + "," + language; }
                inputData.add(languageString);
            }
        }  else { inputData.add("Unused predictor"); }

        //check country
        if (countryCbx.isSelected()) {
            if (countryCombo.getSelectionModel().isEmpty()) { errorMessages.add("Please add at least 1 country or uncheck this predictor"); }
            else {
                String countryString = "";
                for (String country : countryCombo.getItems()) { countryString = countryString + "," + country; }
                inputData.add(countryString);
            }
        }  else { inputData.add("Unused predictor"); }



        //check if there are enough predictors
        int unusedPredictors = 0;
        for (String input : inputData) {
            if (input.equals("Unused predictor")) { unusedPredictors++; }
        }
        if (unusedPredictors > 6) {
            errorMessages.add("You must select at least 4 predictor variables to continue..");
        }

        //display error messages if there are errors
        if (errorMessages.size() > 0) {
            //create error text and add it to a new text component. set style to look like error message
            String errorText = "Please fix the following errors before submitting the form: \n";
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

            //show error dialog
            new ErrorDialog(errorText, (Stage) submitBtn.getScene().getWindow());

        }
        else {
            setOutputComponentsVisible(true);

            progressIndicator.setVisible(true);

            //call calculators to get output values
            //average director rating
            progressStatus.setText("Calculating avg director rating..");
            String averageDirector = "Unused predictor";
            if(!inputData.get(5).equals("Unused predictor")) {
                float avgDirectorRating = avgRatingCalculator.getAvgRating(directorCombo.getItems(), "director");
                inputDataFloat.add(avgDirectorRating);
                averageDirector = String.format("%.1f", (avgDirectorRating));
            }
            avgDirectorOutput.setText(averageDirector);


            //average writer rating
            progressStatus.setText("Calculating avg writer rating..");
            String averageWriter = "Unused predictor";
            if(!inputData.get(6).equals("Unused predictor")) {
                float avgWriterRating = avgRatingCalculator.getAvgRating(writerCombo.getItems(), "writer");
                inputDataFloat.add(avgWriterRating);
                averageWriter = String.format("%.1f", (avgWriterRating));
            }
            avgWriterOutput.setText(averageWriter);

            //average cast rating
            progressStatus.setText("Calculating avg cast rating..");
            String averageCast = "Unused predictor";
            if(!inputData.get(7).equals("Unused predictor")){
                float avgCastRating = avgRatingCalculator.getAvgRating(castCombo.getItems(), "cast");
                inputDataFloat.add(avgCastRating);
                averageCast = String.format("%.1f", (avgCastRating));
            }
            avgCastOutput.setText(averageCast);


            //call multiple linear regression method to get predicted imdb rating
            progressStatus.setText("Predicting imdb rating..");
            new MlrPrediction(inputData, inputDataFloat, true, "rating").start();

            //call multiple linear regression method to get predicted revenue
            new MlrPrediction(inputData, inputDataFloat, true, "revenue").start();

        }
    }


    //hides or shows the output components
    public void setOutputComponentsVisible(boolean outputVisible) {
        int componentsCount = grid.getChildren().size();
        for (int i = componentsCount-13; i < componentsCount-1; i++) {
            grid.getChildren().get(i).setVisible(outputVisible);
        }
    }

    //used by MLR method to return the predicted rating.
    public void setPredictedRating(final float predictedImdbRating) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ratingOutput.setText(String.format("%.1f", (predictedImdbRating)));

                if (progressStatus.getText().contains("rating")) {
                    progressStatus.setText("Predicting revenue..");
                }
                if (!revenueOutput.getText().contains("...")) {
                    progressIndicator.setVisible(false);
                    progressStatus.setText("Done.");
                }
            }
        });

    }

    //used by MLR method to return the predicted revenue.
    public void setPredictedRevenue(final float predictedRevenue) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                revenueOutput.setText(String.format("%.0f", (predictedRevenue*100000000)));

                if (progressStatus.getText().contains("revenue")) {
                    progressIndicator.setVisible(false);
                    progressStatus.setText("Done.");
                }
            }
        });

    }

    //fill the given output variables details array with the calculation details
    public void setDetailsArray(String[][] detailsArray, String outputVar) {
        if (outputVar.contains("rating")) {
            this.ratingDetailsArray = detailsArray;
        } else if (outputVar.contains("revenue")) {
            this.revenueDetailsArray = detailsArray;
        }
    }

}

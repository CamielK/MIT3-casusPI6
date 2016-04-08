package mit3prototype.inputControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


//show help dialog with information about prototype app
public class HelpDialog {

    public HelpDialog(Stage parentStage) {
        //create checkbox help
        HBox checkboxHelp = new HBox(); checkboxHelp.setStyle("-fx-padding: 10; -fx-spacing: 15;");
        checkboxHelp.setAlignment(Pos.TOP_LEFT);
        Text checkBoxLabel = new Text("Checkboxes");
        checkBoxLabel.setWrappingWidth(100);
        checkBoxLabel.setStyle("-fx-font-size: 18px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058;");
        Text checkBoxText = new Text(
                "Use the checkboxes to choose which predictors you would like to use.\n"+
                        "Disabled predictors are not used when calculating predicted imdbRating\n"+
                        "or predicted revenue. At least 4 predictors have to be selected to start.");
        checkboxHelp.getChildren().addAll(checkBoxLabel,checkBoxText);


        //create release year help
        HBox releaseYearHelp = new HBox(); releaseYearHelp.setStyle("-fx-padding: 10; -fx-spacing: 15;");
        checkboxHelp.setAlignment(Pos.TOP_LEFT);
        Text releaseLabel = new Text("Release year");
        releaseLabel.setWrappingWidth(100);
        releaseLabel.setStyle("-fx-font-size: 18px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058;");
        Text releaseText = new Text(
                        "Enter the release year of your movie. This has to be a year between 1990\n"+
                        "and 2016 due to the origins of the used data set. format: (yyyy).");
        releaseYearHelp.getChildren().addAll(releaseLabel,releaseText);

        //create runtime help
        HBox runtimeHelp = new HBox(); runtimeHelp.setStyle("-fx-padding: 10; -fx-spacing: 15;");
        checkboxHelp.setAlignment(Pos.TOP_LEFT);
        Text runtimeLabel = new Text("Runtime");
        runtimeLabel.setWrappingWidth(100);
        runtimeLabel.setStyle("-fx-font-size: 18px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058;");
        Text runtimeText = new Text(
                        "Enter the runtime of your movie. This has to be a time in minutes between\n"+
                        "45 and 240 minutes due to the origins of the used data set. format: (mmm).");
        runtimeHelp.getChildren().addAll(runtimeLabel,runtimeText);

        //create runtime help
        HBox mpaaHelp = new HBox(); mpaaHelp.setStyle("-fx-padding: 10; -fx-spacing: 15;");
        checkboxHelp.setAlignment(Pos.TOP_LEFT);
        Text mpaaLabel = new Text("MPAA rating");
        mpaaLabel.setWrappingWidth(100);
        mpaaLabel.setStyle("-fx-font-size: 18px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058;");
        Text mpaaText = new Text(
                        "Select the MPAA rating of your movie according to the age requirement.");
        mpaaHelp.getChildren().addAll(mpaaLabel,mpaaText);

        //create budget help
        HBox budgetHelp = new HBox(); budgetHelp.setStyle("-fx-padding: 10; -fx-spacing: 15;");
        checkboxHelp.setAlignment(Pos.TOP_LEFT);
        Text budgetLabel = new Text("Budget");
        budgetLabel.setWrappingWidth(100);
        budgetLabel.setStyle("-fx-font-size: 18px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058;");
        Text budgetText = new Text(
                        "Enter the budget of your movie in dollars.");
        budgetHelp.getChildren().addAll(budgetLabel,budgetText);

        //create genre help
        HBox genreHelp = new HBox(); genreHelp.setStyle("-fx-padding: 10; -fx-spacing: 15;");
        checkboxHelp.setAlignment(Pos.TOP_LEFT);
        Text genreLabel = new Text("Genre");
        genreLabel.setWrappingWidth(100);
        genreLabel.setStyle("-fx-font-size: 18px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058;");
        Text genreText = new Text(
                        "Use the add button to open a dialog in which you can select 1 of 20 genres.\n"+
                        "Use the remove button to remove the selected genre from the list. Add as\n"+
                        "many genres as you please.");
        genreHelp.getChildren().addAll(genreLabel,genreText);

        //create director, writer and cast help
        HBox personHelp = new HBox(); personHelp.setStyle("-fx-padding: 10; -fx-spacing: 15;");
        checkboxHelp.setAlignment(Pos.TOP_LEFT);
        Text personLabel = new Text("Director\nWriter\nCast");
        personLabel.setWrappingWidth(100);
        personLabel.setStyle("-fx-font-size: 18px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058;");
        Text personText = new Text(
                        "Use the add buttons to open a dialog in which you can search for new directors,\n"+
                        "writers or actors. Use the remove buttons to remove the selected person from the\n"+
                        "list. Add as many directors, writers or actors as you please.");
        personHelp.getChildren().addAll(personLabel,personText);

        //create language and country help
        HBox continentHelp = new HBox(); continentHelp.setStyle("-fx-padding: 10; -fx-spacing: 15;");
        checkboxHelp.setAlignment(Pos.TOP_LEFT);
        Text continentLabel = new Text("Language\nCountry");
        continentLabel.setWrappingWidth(100);
        continentLabel.setStyle("-fx-font-size: 18px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058;");
        Text continentText = new Text(
                        "Use the add buttons to open a dialog in which you can search for new continents.\n"+
                        "Use the remove buttons to remove the selected language or country from the list.\n"+
                        "Add as many languages or country's as you please.");
        continentHelp.getChildren().addAll(continentLabel,continentText);


        //create author text
        Text authors = new Text("This prototype was created by Maikel Geuns, Aydin Erdas, Cas Helwig and Camiel Kerkhofs");
        authors.setStyle("-fx-font-size: 14px; -fx-font-family: \"Calibri\"; -fx-font-weight: bold; -fx-fill: #2A5058; -fx-padding: 10;");


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
        dialogStage.setTitle("Help & info");
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(checkboxHelp, releaseYearHelp, runtimeHelp, mpaaHelp, budgetHelp, genreHelp, personHelp, continentHelp, authors, okay).
                alignment(Pos.CENTER).padding(new Insets(5)).build()));
        dialogStage.showAndWait();
    }


}

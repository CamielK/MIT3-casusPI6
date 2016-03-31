/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Camiel
 */
public class GUI extends JFrame {


    //input labels:
    private JLabel tooltip = new JLabel("Enter Movie information below. Use checkboxes to enable predictor variables.");
    private JLabel movieTitleLbl = new JLabel("Movie Title: ");
    private JLabel releaseYearLbl = new JLabel("Release year (yyyy): ");
    private JLabel mpaaRatingLbl = new JLabel("MPAA rating: ");
    private JLabel runtimeLbl = new JLabel("Runtime (mins): ");
    private JLabel genreLbl = new JLabel("Genre: ");
    private JLabel directorLbl = new JLabel("Director: ");
    private JLabel writerLbl = new JLabel("writer: ");
    private JLabel castLbl = new JLabel("Cast: ");
    private JLabel languageLbl = new JLabel("Language: ");
    private JLabel countryLbl = new JLabel("Country: ");
    private JLabel budgetLbl = new JLabel("Budget (Dollar): ");


    //input fields
    private JTextField movieTitleInput = new JTextField(30);
    private JTextField releaseYearInput = new JTextField(15);
    private JTextField runtimeInput = new JTextField(15);
    private JTextField budgetInput = new JTextField(15);
    private JComboBox mpaaRatingInput = new JComboBox(new String[] {
                    "mpaa rating",
                    "G",
                    "PG",
                    "PG-13",
                    "PG-17",
                    "R",
                    "UNRATED                             "
            });
    private JButton btnPredict = new JButton("Predict movie rating!");

    //input checkboxes
    private JCheckBox releaseYearCbx = new JCheckBox("");
    private JCheckBox mpaaRatingCbx = new JCheckBox("");
    private JCheckBox runtimeCbx = new JCheckBox("");
    private JCheckBox genreCbx = new JCheckBox("");
    private JCheckBox directorCbx = new JCheckBox("");
    private JCheckBox writerCbx = new JCheckBox("");
    private JCheckBox castCbx = new JCheckBox("");
    private JCheckBox languageCbx = new JCheckBox("");
    private JCheckBox countryCbx = new JCheckBox("");
    private JCheckBox budgetCbx = new JCheckBox("");


    //result components:
    private JLabel errors = new JLabel();
    private JLabel predictedRatingLbl = new JLabel("Predicted imdb rating: ");
    private JLabel predictedRevenueLbl = new JLabel("Predicted revenue: ");
    private JLabel predictedRatingOutput = new JLabel("..."); //can be changed later to display output rating
    private JLabel predictedRevenueOutput = new JLabel("..."); //can be changed later to display output revenue




    public String getMpaaRatingInput() {
        return mpaaRatingInput.getSelectedItem().toString();
    }



    public GUI() {
        super("MIT3 prototype applicatie");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        //create main jpanel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.anchor = GridBagConstraints.NORTH;
        mainConstraints.insets = new Insets(10, 10, 10, 10);
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 0;
        mainPanel.add(tooltip, mainConstraints);


            //create left input panel
            JPanel inputPanel = new JPanel(new GridBagLayout());
            GridBagConstraints inputConstraints = new GridBagConstraints();
            inputConstraints.anchor = GridBagConstraints.WEST;
            inputConstraints.insets = new Insets(1, 1, 1, 1);
            inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Enter movie info"));

                //movie title
                inputConstraints.gridx = 1;
                inputConstraints.gridy = 0;
                inputPanel.add(movieTitleLbl, inputConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(movieTitleInput, inputConstraints);
                //movieTitleInput.setText("Movie Title");

                //release year
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 1;
                inputPanel.add(releaseYearCbx, inputConstraints); releaseYearCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(releaseYearLbl, inputConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(releaseYearInput, inputConstraints);

                //mpaa rating
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 2;
                inputPanel.add(mpaaRatingCbx, inputConstraints); mpaaRatingCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(mpaaRatingLbl, inputConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(mpaaRatingInput, inputConstraints);

                //runtime
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 3;
                inputPanel.add(runtimeCbx, inputConstraints); runtimeCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(runtimeLbl, inputConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(runtimeInput, inputConstraints);

                //budget
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 4;
                inputPanel.add(budgetCbx, inputConstraints); budgetCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(budgetLbl, inputConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(budgetInput, inputConstraints);

                //genre
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 5;
                inputPanel.add(genreCbx, inputConstraints); genreCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(genreLbl, inputConstraints);

                //director
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 6;
                inputPanel.add(directorCbx, inputConstraints); directorCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(directorLbl, inputConstraints);

                //writer
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 7;
                inputPanel.add(writerCbx, inputConstraints); writerCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(writerLbl, inputConstraints);

                //cast
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 8;
                inputPanel.add(castCbx, inputConstraints); castCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(castLbl, inputConstraints);

                //language
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 9;
                inputPanel.add(languageCbx, inputConstraints); languageCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(languageLbl, inputConstraints);

                //country
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 10;
                inputPanel.add(countryCbx, inputConstraints); countryCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(countryLbl, inputConstraints);


                //submit button
                inputConstraints.gridx = 2;
                inputConstraints.gridy = 11;
                inputPanel.add(btnPredict, inputConstraints);

                //errors label
                inputConstraints.gridx = 2;
                inputConstraints.gridy = 12;
                inputPanel.add(errors, inputConstraints);


            //create right results panel
            JPanel resultPanel = new JPanel(new GridBagLayout());
            GridBagConstraints resultConstraints = new GridBagConstraints();
            resultConstraints.anchor = GridBagConstraints.WEST;
            resultConstraints.insets = new Insets(10, 10, 10, 10);
            resultPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Movie prediction results"));

                //predicted rating
                resultConstraints.gridx = 0;
                resultConstraints.gridy = 0;
                resultConstraints.ipady = 10;
                resultPanel.add(predictedRatingLbl, resultConstraints);
                resultConstraints.gridx = 1;
                resultPanel.add(predictedRatingOutput, resultConstraints);

                //predicted revenue
                resultConstraints.gridx = 0;
                resultConstraints.gridy = 1;
                resultPanel.add(predictedRevenueLbl, resultConstraints);
                resultConstraints.gridx = 1;
                resultPanel.add(predictedRevenueOutput, resultConstraints);


        //add left input panel to main panel
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 1;
        mainPanel.add(inputPanel, mainConstraints);


        //add right result panel to main panel
        mainConstraints.gridx = 1;
        mainPanel.add(resultPanel, mainConstraints);

        // add main panel to frame and configure frame settings
        add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //application exits when frame is closed
        this.setResizable(false); //cant be resized by user
        pack();
        setLocationRelativeTo(null); //centre of screen

        this.setVisible(true);



        //add action listeners for input fields
        btnPredict.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitForm(evt);
            }
        });
    }


    //checks form input and validates it. submits form if no errors
    private void submitForm (java.awt.event.ActionEvent evt) {
        System.out.println(releaseYearInput.getText());

        java.util.List<String> errors = new ArrayList<String>();
        java.util.List<String> inputData = new ArrayList<String>(); //inputs are added to this list. if predictor is not select an empty input will be added. format: (releaseYear, mpaaRating, runtime, budget, genre, driector, writer, cast, language, country)

        //>>> check correct form input <<<
        //check release year input
        if (releaseYearCbx.isSelected()) {
            try {
                int year = Integer.parseInt(releaseYearInput.getText());
                if (year < 1990 || year > 2020) { errors.add("Invalid release year. Please enter a year between 1990 and 2020 or uncheck this predictor."); }
                else { inputData.add(releaseYearInput.getText()); }
            } catch (Exception e) { errors.add("Invalid release year. Please enter a year using only integers."); }
        } else { inputData.add("Unused predictor"); }

        //check mpaa rating
        if (mpaaRatingCbx.isSelected() && mpaaRatingInput.getSelectedItem().toString().equals("mpaa rating")) {
            errors.add("Invalid MPAA rating. please select an MPAA rating or uncheck this predictor.");
        }
        else if (mpaaRatingCbx.isSelected()) {
            inputData.add(mpaaRatingInput.getSelectedItem().toString());
        } else { inputData.add("Unused predictor"); }

        //check runtime
        if (runtimeCbx.isSelected()) {
            try {
                int runtime = Integer.parseInt(runtimeInput.getText());
                if (runtime < 45 || runtime > 240) { errors.add("Invalid runtime. Please enter a runtime between 45 and 240 minutes or uncheck this predictor."); }
                else { inputData.add(runtimeInput.getText()); }
            } catch (Exception e) { errors.add("Invalid runtime. Please enter runtime using only integers."); }
        }  else { inputData.add("Unused predictor"); }

        //check budget
        if (budgetCbx.isSelected()) {
            try {
                long budget = Long.parseLong(budgetInput.getText());
                if (budget < 0) { errors.add("Invalid budget. Please enter a budget bigger then 0 or uncheck this predictor."); }
                else { inputData.add(budgetInput.getText()); }
            } catch (Exception e) { errors.add("Invalid budget. Please enter a budget using only integers."); }
        }  else { inputData.add("Unused predictor"); }

        //check genre
        if (genreCbx.isSelected()) {
            //TODO
        }  else { inputData.add("Unused predictor"); }

        //check director
        if (directorCbx.isSelected()) {
            //TODO
        }  else { inputData.add("Unused predictor"); }

        //check writer
        if (writerCbx.isSelected()) {
            //TODO
        }  else { inputData.add("Unused predictor"); }

        //check cast
        if (castCbx.isSelected()) {
            //TODO
        }  else { inputData.add("Unused predictor"); }

        //check language
        if (languageCbx.isSelected()) {
            //TODO
        }  else { inputData.add("Unused predictor"); }

        //check country
        if (countryCbx.isSelected()) {
            //TODO
        }  else { inputData.add("Unused predictor"); }


        //submit form
        if (errors.size() > 0) {
            //display errors by showing a dialog with the errors
            JPanel errorPanel = new JPanel(new GridBagLayout());
            GridBagConstraints errorConstraints = new GridBagConstraints();
            errorConstraints.anchor = GridBagConstraints.WEST;
            errorConstraints.insets = new Insets(10, 10, 10, 10);
            errorConstraints.gridx = 0;
            errorConstraints.gridy = 0;
            for (String error : errors) {
                errorConstraints.gridy++;
                JLabel errorLabel = new JLabel(error);
                errorLabel.setForeground(Color.red);
                errorPanel.add(errorLabel, errorConstraints);
            }
            JOptionPane.showMessageDialog(this, errorPanel);
        }
        else {
            //submit form

        }
    }

}
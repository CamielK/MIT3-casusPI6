/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.util.*;

/**
 *
 * @author Camiel
 */

@Deprecated
public class GUIswing extends JFrame {


    //input labels:
    private JLabel tooltip = new JLabel("Enter Movie information below. Use checkboxes to enable predictor variables.");
    private JLabel movieTitleLbl = new JLabel("Movie Title: ");
    private JLabel releaseYearLbl = new JLabel("Release year: ");
    private JLabel mpaaRatingLbl = new JLabel("MPAA rating: ");
    private JLabel runtimeLbl = new JLabel("Runtime (mins): ");
    private JLabel genreLbl = new JLabel("Genres: ");
    private JLabel directorLbl = new JLabel("Directors: ");
    private JLabel writerLbl = new JLabel("writers: ");
    private JLabel castLbl = new JLabel("Cast: ");
    private JLabel languageLbl = new JLabel("Languages: ");
    private JLabel countryLbl = new JLabel("Country: ");
    private JLabel budgetLbl = new JLabel("Budget (Dollar): ");


    //input fields
    private JTextField movieTitleInput = new JTextField(12);
    private JTextField releaseYearInput = new JTextField(12);
    private JTextField runtimeInput = new JTextField(12);
    private JTextField budgetInput = new JTextField(12);
    private JComboBox mpaaRatingInput = new JComboBox(new String[] {
                    "mpaa rating",
                    "G",
                    "PG",
                    "PG-13",
                    "PG-17",
                    "R",
                    "UNRATED                  "
            });
    private JButton btnPredict = new JButton("    Predict movie rating!    ");
    private JProgressBar progressBar = new JProgressBar();

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

    //input add buttons
    private JButton genreBtn = new JButton("Add");
    private JButton directorBtn = new JButton("Add");
    private JButton writerBtn = new JButton("Add");
    private JButton castBtn = new JButton("Add");
    private JButton languageBtn = new JButton("Add");
    private JButton countryBtn = new JButton("Add");

    //input remove selected buttons
    private JButton genreBtnX = new JButton("Remove");
    private JButton directorBtnX = new JButton("Remove");
    private JButton writerBtnX = new JButton("Remove");
    private JButton castBtnX = new JButton("Remove");
    private JButton languageBtnX = new JButton("Remove");
    private JButton countryBtnX = new JButton("Remove");

    //input combo boxes
    private String[] genresList = new String[] {"Add a genre or remove the selected genre"};
    private String[] directorsList = new String[] {"Add a director or remove the selected director"};
    private String[] writersList = new String[] {"Add a writer or remove the selected writer"};
    private String[] castList = new String[] {"Add a cast member or remove the selected member"};
    private String[] languagesList = new String[] {"Add a language or remove the selected language"};
    private String[] countrysList = new String[] {"Add a country or remove the selected country"};
    private JComboBox<String> genres = new JComboBox<String>(genresList);
    private JComboBox<String> directors = new JComboBox<String>(directorsList);
    private JComboBox<String> writers = new JComboBox<String>(writersList);
    private JComboBox<String> cast = new JComboBox<String>(castList);
    private JComboBox<String> languages = new JComboBox<String>(languagesList);
    private JComboBox<String> countrys = new JComboBox<String>(countrysList);


    //calculation output components:
    private JLabel avgDirectorRatingLbl = new JLabel("Average director rating: ");
    private JLabel avgDirectorRatingOutput = new JLabel("...");
    private JLabel avgWriterRatingLbl = new JLabel("Average writer rating: ");
    private JLabel avgWriterRatingOutput = new JLabel("...");
    private JLabel avgCastRatingLbl = new JLabel("Average cast rating: ");
    private JLabel avgCastRatingOutput = new JLabel("...");


    //result components:
    private JLabel predictedRatingLbl = new JLabel("Predicted imdb rating: ");
    private JLabel predictedRevenueLbl = new JLabel("Predicted revenue: ");
    private JLabel predictedRatingOutput = new JLabel("..."); //can be changed later to display output rating
    private JLabel predictedRevenueOutput = new JLabel("..."); //can be changed later to display output revenue



    //panels
    private JPanel mainPanel; private GridBagConstraints mainConstraints;
    private JPanel inputPanel; private GridBagConstraints inputConstraints;




    public String getMpaaRatingInput() {
        return mpaaRatingInput.getSelectedItem().toString();
    }



    public GUIswing() {
        super("MIT3 prototype applicatie");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        //create main jpanel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new java.awt.Dimension(1000, 600));
        mainConstraints = new GridBagConstraints();
        mainConstraints.anchor = GridBagConstraints.NORTH;
        mainConstraints.insets = new Insets(1, 1, 1, 1);
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 0;
        mainPanel.add(tooltip, mainConstraints);


            //create left input panel
            inputPanel = new JPanel(new GridBagLayout());
            inputConstraints = new GridBagConstraints();
            inputConstraints.anchor = GridBagConstraints.WEST;
            inputConstraints.insets = new Insets(1, 1, 1, 1);
            inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Enter new movie info"));

                //movie title
                inputConstraints.gridx = 1;
                inputConstraints.gridy = 0;
                inputConstraints.ipady = 5;
                inputPanel.add(movieTitleLbl, inputConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(movieTitleInput, inputConstraints);

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
                inputConstraints.ipady = 2;
                inputPanel.add(genreCbx, inputConstraints); genreCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(genreLbl, inputConstraints);
                JPanel genrePanel = new JPanel(new GridBagLayout()); GridBagConstraints genreConstraints = new GridBagConstraints();
                genreConstraints.anchor = GridBagConstraints.WEST; genreConstraints.insets = new Insets(1, 1, 1, 1);
                genreConstraints.gridx = 0; genreConstraints.gridy = 0; genrePanel.add(genreBtn, genreConstraints);
                genreConstraints.gridx = 1; genrePanel.add(genreBtnX, genreConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(genrePanel, inputConstraints);
                inputConstraints.gridx = 3;
                inputPanel.add(genres, inputConstraints);

                //director
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 6;
                inputPanel.add(directorCbx, inputConstraints); directorCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(directorLbl, inputConstraints);
                JPanel directorPanel = new JPanel(new GridBagLayout()); GridBagConstraints directorConstraints = new GridBagConstraints();
                directorConstraints.anchor = GridBagConstraints.WEST; directorConstraints.insets = new Insets(1, 1, 1, 1);
                directorConstraints.gridx = 0; directorConstraints.gridy = 0; directorPanel.add(directorBtn, directorConstraints);
                directorConstraints.gridx = 1; directorPanel.add(directorBtnX, directorConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(directorPanel, inputConstraints);
                inputConstraints.gridx = 3;
                inputPanel.add(directors, inputConstraints);

                //writer
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 7;
                inputPanel.add(writerCbx, inputConstraints); writerCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(writerLbl, inputConstraints);
                JPanel writerPanel = new JPanel(new GridBagLayout()); GridBagConstraints writerConstraints = new GridBagConstraints();
                writerConstraints.anchor = GridBagConstraints.WEST; writerConstraints.insets = new Insets(1, 1, 1, 1);
                writerConstraints.gridx = 0; writerConstraints.gridy = 0; writerPanel.add(writerBtn, writerConstraints);
                writerConstraints.gridx = 1; writerPanel.add(writerBtnX, writerConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(writerPanel, inputConstraints);
                inputConstraints.gridx = 3;
                inputPanel.add(writers, inputConstraints);

                //cast
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 8;
                inputPanel.add(castCbx, inputConstraints); castCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(castLbl, inputConstraints);
                JPanel castPanel = new JPanel(new GridBagLayout()); GridBagConstraints castConstraints = new GridBagConstraints();
                castConstraints.anchor = GridBagConstraints.WEST; castConstraints.insets = new Insets(1, 1, 1, 1);
                castConstraints.gridx = 0; castConstraints.gridy = 0; castPanel.add(castBtn, castConstraints);
                castConstraints.gridx = 1; castPanel.add(castBtnX, castConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(castPanel, inputConstraints);
                inputConstraints.gridx = 3;
                inputPanel.add(cast, inputConstraints);

                //language
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 9;
                inputPanel.add(languageCbx, inputConstraints); languageCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(languageLbl, inputConstraints);
                JPanel languagePanel = new JPanel(new GridBagLayout()); GridBagConstraints languageConstraints = new GridBagConstraints();
                languageConstraints.anchor = GridBagConstraints.WEST; languageConstraints.insets = new Insets(1, 1, 1, 1);
                languageConstraints.gridx = 0; languageConstraints.gridy = 0; languagePanel.add(languageBtn, languageConstraints);
                languageConstraints.gridx = 1; languagePanel.add(languageBtnX, languageConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(languagePanel, inputConstraints);
                inputConstraints.gridx = 3;
                inputPanel.add(languages, inputConstraints);


                //country
                inputConstraints.gridx = 0;
                inputConstraints.gridy = 10;
                inputPanel.add(countryCbx, inputConstraints); countryCbx.setSelected(true);
                inputConstraints.gridx = 1;
                inputPanel.add(countryLbl, inputConstraints);
                JPanel countryPanel = new JPanel(new GridBagLayout()); GridBagConstraints countryConstraints = new GridBagConstraints();
                countryConstraints.anchor = GridBagConstraints.WEST; countryConstraints.insets = new Insets(1, 1, 1, 1);
                countryConstraints.gridx = 0; countryConstraints.gridy = 0; countryPanel.add(countryBtn, countryConstraints);
                countryConstraints.gridx = 1; countryPanel.add(countryBtnX, countryConstraints);
                inputConstraints.gridx = 2;
                inputPanel.add(countryPanel, inputConstraints);
                inputConstraints.gridx = 3;
                inputPanel.add(countrys, inputConstraints);




            //create right input calculation results
            JPanel calcPanel = new JPanel(new GridBagLayout());
            GridBagConstraints calcConstraints = new GridBagConstraints();
            calcConstraints.anchor = GridBagConstraints.WEST;
            calcConstraints.insets = new Insets(10, 10, 10, 10);
            calcPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Calculated values"));

                //average director rating
                calcConstraints.gridx = 0;
                calcConstraints.gridy = 0;
                calcConstraints.ipady = 10;
                calcPanel.add(avgDirectorRatingLbl, calcConstraints);
                calcConstraints.gridx = 1;
                calcPanel.add(avgDirectorRatingOutput, calcConstraints);

                //average writer rating
                calcConstraints.gridx = 0;
                calcConstraints.gridy = 1;
                calcPanel.add(avgWriterRatingLbl, calcConstraints);
                calcConstraints.gridx = 1;
                calcPanel.add(avgWriterRatingOutput, calcConstraints);

                //average cast rating
                calcConstraints.gridx = 0;
                calcConstraints.gridy = 2;
                calcPanel.add(avgCastRatingLbl, calcConstraints);
                calcConstraints.gridx = 1;
                calcPanel.add(avgCastRatingOutput, calcConstraints);



            //create right results panel
            JPanel resultPanel = new JPanel(new GridBagLayout());
            GridBagConstraints resultConstraints = new GridBagConstraints();
            resultConstraints.anchor = GridBagConstraints.WEST;
            resultConstraints.insets = new Insets(10, 10, 10, 10);
            resultPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Movie prediction results"));

                //submit button
                resultConstraints.gridx = 0;
                resultConstraints.gridy = 0;
                btnPredict.setBackground(Color.GREEN);
                resultPanel.add(btnPredict, resultConstraints);


                //predicted rating
                resultConstraints.gridy = 1;
                resultConstraints.ipady = 12;
                resultPanel.add(predictedRatingLbl, resultConstraints);
                resultConstraints.gridx = 1;
                resultPanel.add(predictedRatingOutput, resultConstraints);

                //predicted revenue
                resultConstraints.gridx = 0;
                resultConstraints.gridy = 2;
                resultPanel.add(predictedRevenueLbl, resultConstraints);
                resultConstraints.gridx = 1;
                resultPanel.add(predictedRevenueOutput, resultConstraints);


        //add left input panel to main panel
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 1;
        mainPanel.add(inputPanel, mainConstraints);

        //add calculation result panel to main panel
        mainConstraints.gridx = 1;
        mainPanel.add(calcPanel, mainConstraints);

        //add right result panel to main panel
        //mainConstraints.gridy = 2;
        //mainPanel.add(resultPanel, mainConstraints);
        calcConstraints.gridx = 0;
        calcConstraints.gridy = 3;
        calcPanel.add(resultPanel, calcConstraints);

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

        genreBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //addGenreDialog(evt);
            }
        });

        directorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //addDirectorDialog(evt);
            }
        });

        writerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //addWriterDialog(evt);
            }
        });

        castBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //addCastDialog(evt);
            }
        });

        languageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //addLanguageDialog(evt);
            }
        });

        countryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //addCountryDialog(evt);
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
        if (mpaaRatingCbx.isSelected() && mpaaRatingInput.getSelectedItem().toString().equals("rating")) {
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
            if (true) { errors.add("Please add at least 1 genre or uncheck this predictor"); }
            //if (genres.getSelectedItem().toString().contains("Add a genre")) { errors.add("Please add at least 1 genre or uncheck this predictor"); }
            else {
                //genresList.
            }
        }  else { inputData.add("Unused predictor"); }

        //check director
        if (directorCbx.isSelected() && directors.getSelectedItem().toString().contains("Add a genre")) {
            errors.add("Please add at least 1 genre or uncheck this predictor");
        }  else { inputData.add("Unused predictor"); }

        //check writer
        if (writerCbx.isSelected() && writers.getSelectedItem().toString().contains("Add a genre")) {
            errors.add("Please add at least 1 genre or uncheck this predictor");
        }  else { inputData.add("Unused predictor"); }

        //check cast
        if (castCbx.isSelected() && cast.getSelectedItem().toString().contains("Add a genre")) {
            errors.add("Please add at least 1 genre or uncheck this predictor");
        }  else { inputData.add("Unused predictor"); }

        //check language
        if (languageCbx.isSelected() && languages.getSelectedItem().toString().contains("Add a genre")) {
            errors.add("Please add at least 1 genre or uncheck this predictor");
        }  else { inputData.add("Unused predictor"); }

        //check country
        if (countryCbx.isSelected() && countrys.getSelectedItem().toString().contains("Add a country")) {
            errors.add("Please add at least 1 genre or uncheck this predictor");
        }  else { inputData.add("Unused predictor"); }


        //submit form
        if (errors.size() > 0) {
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
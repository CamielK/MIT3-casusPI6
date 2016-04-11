package mit3prototype.calculators;

import matrix.Matrix;
import mit3prototype.GUI.GUIcontroller;
import mit3prototype.data.dataReaders.MainDbReader;
import regression.MultiLinear;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camiel on 11-4-2016.
 */
public class PredictedRating extends Thread {

    private final int trainingDataRows = 3693;

    //input data set
    //format: (releaseYear, runtime, mpaaRating, budget, genre, director, writer, cast, language, country) all are strings
    private static List<String> inputData = new ArrayList<>();
    private static List<Float> inputDataFloat = new ArrayList<>(); //avgDirectorRating, avgWriterRating, avgCastRating


    //MLR data set (only numerical variables)
    //format: YearSinceRelease,Runtime,AvgDirectorRating,AvgWriterRating,AvgCastRating,imdbRating,Budget,Revenue,budget_norm_E8,revenue_norm_E8,mpaaRating_G,mpaaRating_NC-17,mpaaRating_PG,mpaaRating_PG-13,mpaaRating_R,mpaaRating_UNRATED,Genre_Action,Genre_Adventure,Genre_Animation,Genre_Comedy,Genre_Biography,Genre_Crime,Genre_Drama,Genre_Documentary,Genre_Family,Genre_Fantasy,Genre_History,Genre_Horror,Genre_Mystery,Genre_Romance,Genre_Sci-Fi,Genre_Thriller,Genre_Western,Genre_Sport,Genre_Music,Genre_Musical,Genre_War,Language_English,Language_Spanish,Language_European,Language_Asian,Language_Arabic,Language_Other,Countrys_English,Countrys_Spanish,Countrys_European,Countrys_Asian,Countrys_Arabic,Countrys_Other
    //47 columns in total. initiated at null
    private static List<Double> MLRdata = new ArrayList<>();


    public PredictedRating (List<String> inputData, List<Float> inputDataFloat) {
        this.inputData = inputData;
        this.inputDataFloat = inputDataFloat;


        //init MLRdata list with 47 null columns
        for (int i = 0; i <=47; i++) {
            MLRdata.add(null);
        }
    }

    public void run() {
        float predictedRating = 0;
        try {

            for (String data : inputData) {
                System.out.println("input data: " + data);
            }
            generateMlrData();

            //count used predictor variables
            int usedPredictorCount = 0;
            for (int i = 0; i < 47; i++) {
                if (MLRdata.get(i) != null) {
                    usedPredictorCount++;
                }

                System.out.println("MLR index: " + i + ".  data at index: " + MLRdata.get(i));
            }


            //create predictorData Matrix and outputData Matrix based on training data
            ResultSet trainingData = getTrainingData();
            trainingData.getFetchSize();
            double[][] predictorData = new double[trainingDataRows][usedPredictorCount];
            double[][] outputData = new double[trainingDataRows][1];
            int i = 0;

            while (trainingData.next()) {
                double[] dataRow = new double[usedPredictorCount];
                int f = 0;

                if (MLRdata.get(0) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("YearSinceRelease"));
                    f++;
                } //0
                if (MLRdata.get(1) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Runtime"));
                    f++;
                } //1
                if (MLRdata.get(2) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("AvgDirectorRating"));
                    f++;
                } //2
                if (MLRdata.get(3) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("AvgWriterRating"));
                    f++;
                } //3
                if (MLRdata.get(4) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("AvgCastRating"));
                    f++;
                } //4
                if (MLRdata.get(5) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("imdbRating"));
                    f++;
                } //5
                if (MLRdata.get(6) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("budget_norm_E8"));
                    f++;
                } //6
                if (MLRdata.get(7) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("revenue_norm_E8"));
                    f++;
                } //7
                if (MLRdata.get(8) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("mpaaRating_G"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //8
                if (MLRdata.get(9) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("mpaaRating_NC-17"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //9
                if (MLRdata.get(10) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("mpaaRating_PG"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //10
                if (MLRdata.get(11) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("mpaaRating_PG-13"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //11
                if (MLRdata.get(12) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("mpaaRating_R"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //12
                if (MLRdata.get(13) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("mpaaRating_UNRATED"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //13
                if (MLRdata.get(14) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Action"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //14
                if (MLRdata.get(15) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Adventure"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //15
                if (MLRdata.get(16) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Animation"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //16
                if (MLRdata.get(17) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Comedy"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //17
                if (MLRdata.get(18) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Biography"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //18
                if (MLRdata.get(19) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Crime"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //19
                if (MLRdata.get(20) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Drama"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //20
                if (MLRdata.get(21) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Documentary"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //21
                if (MLRdata.get(22) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Family"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //22
                if (MLRdata.get(23) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Fantasy"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //23
                if (MLRdata.get(24) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_History"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //24
                if (MLRdata.get(25) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Horror"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //25
                if (MLRdata.get(26) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Mystery"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //26
                if (MLRdata.get(27) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Romance"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //27
                if (MLRdata.get(28) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Sci-Fi"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //28
                if (MLRdata.get(29) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Thriller"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //29
                if (MLRdata.get(30) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Western"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //30
                if (MLRdata.get(31) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Sport"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //31
                if (MLRdata.get(32) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Music"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //32
                if (MLRdata.get(33) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_Musical"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //33
                if (MLRdata.get(34) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Genre_War"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //34
                if (MLRdata.get(35) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Language_English"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //35
                if (MLRdata.get(36) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Language_Spanish"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //36
                if (MLRdata.get(37) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Language_European"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //37
                if (MLRdata.get(38) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Language_Asian"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //38
                if (MLRdata.get(39) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Language_Arabic"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //39
                if (MLRdata.get(40) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Language_Other"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //40
                if (MLRdata.get(41) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Countrys_English"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //41
                if (MLRdata.get(42) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Countrys_Spanish"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //42
                if (MLRdata.get(43) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Countrys_European"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //43
                if (MLRdata.get(44) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Countrys_Asian"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //44
                if (MLRdata.get(45) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Countrys_Arabic"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //45
                if (MLRdata.get(46) != null) {
                    dataRow[f] = Double.parseDouble(trainingData.getString("Countrys_Other"));
                    if (dataRow[f] == 0) {
                        dataRow[f] = Double.parseDouble("0.0001");
                    }
                    f++;
                } //46


                outputData[i] = new double[]{Double.parseDouble(trainingData.getString("imdbRating"))};
                predictorData[i] = dataRow;
                i++;
            }

            //get beta values for all used predictors
            final Matrix beta = getBetas(predictorData, outputData);

            //calculate predicted rating based on betas
            double predictedRatingDouble = beta.getValueAt(0, 0); //intercept
            for (int j = 1; j < beta.getNrows(); j++) {
                double predictor = 0;

                //find next MLRdata row that is not null
                int foundRows = 0;
                int test = 0;
                for (int k = 0; k < MLRdata.size(); k++) {
                    if (MLRdata.get(k) != null && foundRows == j - 1) {
                        predictor = MLRdata.get(k);
                        test = k;
                        break;
                    } else if (MLRdata.get(k) != null) {
                        foundRows++;
                    }
                }

                System.out.println("MLR index:" + test + ".  predictor value: " + predictor);
                predictedRatingDouble += beta.getValueAt(j, 0) * predictor;

                predictedRating = (float) predictedRatingDouble;
            }

            //return prediction result
        } catch (Exception e) {e.printStackTrace();}


        new GUIcontroller().setPredictedRating(predictedRating);
    }


    //generate MLR data list including dummy variables. based on input data list
    //input list format: (releaseYear, runtime, mpaaRating, budget, genre, director, writer, cast, language, country) all are strings
    private void generateMlrData() {
        //imdbRating and revenue default to null because they are not part of input
        MLRdata.set(5,null);
        MLRdata.set(7,null);

        //check years since release
        if (!inputData.get(0).equals("Unused predictor")) {
            double yearsSinceRelease = 2016 - Integer.parseInt(inputData.get(0));
            if (yearsSinceRelease < 0) {yearsSinceRelease=0;}
            MLRdata.set(0,yearsSinceRelease);
        } else {MLRdata.set(0,null);}

        //check runtime
        if (!inputData.get(1).equals("Unused predictor")) {
            double runtime = Integer.parseInt(inputData.get(1));
            MLRdata.set(1,runtime);
        } else {MLRdata.set(1,null);}

        //check mpaa rating
        if (!inputData.get(2).contains("Unused predictor")) {
            String mpaaRating = inputData.get(2);
            if (mpaaRating.contains("G (all ages)")) {MLRdata.set(8,Double.parseDouble("1"));} else {{MLRdata.set(8,Double.parseDouble("0.0001"));}}
            if (mpaaRating.contains("PG (parental guidance advised)")) {MLRdata.set(10,Double.parseDouble("1"));} else {{MLRdata.set(10,Double.parseDouble("0.0001"));}}
            if (mpaaRating.contains("PG-13 (13+)")) {MLRdata.set(11,Double.parseDouble("1"));} else {{MLRdata.set(11,Double.parseDouble("0.0001"));}}
            if (mpaaRating.contains("NC-17 (17+)")) {MLRdata.set(9,Double.parseDouble("1"));} else {{MLRdata.set(9,Double.parseDouble("0.0001"));}}
            if (mpaaRating.contains("R (mature audiences)")) {MLRdata.set(12,Double.parseDouble("1"));} else {{MLRdata.set(12,Double.parseDouble("0.0001"));}}
            if (mpaaRating.contains("UNRATED (no rating)")) {MLRdata.set(13,Double.parseDouble("1"));} else {{MLRdata.set(13,Double.parseDouble("0.0001"));}}
        } else {MLRdata.set(8,null);MLRdata.set(9,null);MLRdata.set(10,null);MLRdata.set(11,null);MLRdata.set(12,null);MLRdata.set(13,null);}

        //check budget (normalized)
        if (!inputData.get(3).equals("Unused predictor")) {
            double budget = Integer.parseInt(inputData.get(3));
            double budget_norm_E8 = budget / 100000000;
            MLRdata.set(6,budget_norm_E8);
        } else {MLRdata.set(6,null);}

        //check genre
        if (!inputData.get(4).contains("Unused predictor")) {
            String genreField = inputData.get(4);
            if (genreField.contains("Action")) {MLRdata.set(14,Double.parseDouble("1"));} else {{MLRdata.set(14,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Adventure")) {MLRdata.set(15,Double.parseDouble("1"));} else {{MLRdata.set(15,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Animation")) {MLRdata.set(16,Double.parseDouble("1"));} else {{MLRdata.set(16,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Comedy")) {MLRdata.set(17,Double.parseDouble("1"));} else {{MLRdata.set(17,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Biography")) {MLRdata.set(18,Double.parseDouble("1"));} else {{MLRdata.set(18,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Crime")) {MLRdata.set(19,Double.parseDouble("1"));} else {{MLRdata.set(19,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Drama")) {MLRdata.set(20,Double.parseDouble("1"));} else {{MLRdata.set(20,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Documentary")) {MLRdata.set(21,Double.parseDouble("1"));} else {{MLRdata.set(21,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Family")) {MLRdata.set(22,Double.parseDouble("1"));} else {{MLRdata.set(22,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Fantasy")) {MLRdata.set(23,Double.parseDouble("1"));} else {{MLRdata.set(23,Double.parseDouble("0.0001"));}}
            if (genreField.contains("History")) {MLRdata.set(24,Double.parseDouble("1"));} else {{MLRdata.set(24,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Horror")) {MLRdata.set(25,Double.parseDouble("1"));} else {{MLRdata.set(25,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Mystery")) {MLRdata.set(26,Double.parseDouble("1"));} else {{MLRdata.set(26,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Romance")) {MLRdata.set(27,Double.parseDouble("1"));} else {{MLRdata.set(27,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Sci-Fi")) {MLRdata.set(28,Double.parseDouble("1"));} else {{MLRdata.set(28,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Thriller")) {MLRdata.set(29,Double.parseDouble("1"));} else {{MLRdata.set(29,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Western")) {MLRdata.set(30,Double.parseDouble("1"));} else {{MLRdata.set(30,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Sport")) {MLRdata.set(31,Double.parseDouble("1"));} else {{MLRdata.set(31,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Music")) {MLRdata.set(32,Double.parseDouble("1"));} else {{MLRdata.set(32,Double.parseDouble("0.0001"));}}
            if (genreField.contains("Musical")) {MLRdata.set(33,Double.parseDouble("1"));} else {{MLRdata.set(33,Double.parseDouble("0.0001"));}}
            if (genreField.contains("War")) {MLRdata.set(34,Double.parseDouble("1"));} else {{MLRdata.set(34,Double.parseDouble("0.0001"));}}
        } else {MLRdata.set(14,null);MLRdata.set(15,null);MLRdata.set(16,null);MLRdata.set(17,null);MLRdata.set(18,null);MLRdata.set(19,null);
            MLRdata.set(20,null);MLRdata.set(21,null);MLRdata.set(22,null);MLRdata.set(23,null);MLRdata.set(24,null);MLRdata.set(25,null);
            MLRdata.set(26,null);MLRdata.set(27,null);MLRdata.set(28,null);MLRdata.set(29,null);MLRdata.set(30,null);MLRdata.set(31,null);
            MLRdata.set(32,null);MLRdata.set(33,null);MLRdata.set(34,null);}

        //check director
        if (!inputData.get(5).equals("Unused predictor")) {
            double averageDirectorRating = inputDataFloat.get(0);
            MLRdata.set(2,averageDirectorRating);
        } else {MLRdata.set(2,null);}

        //check writer
        if (!inputData.get(6).equals("Unused predictor")) {
            double averageWriterRating = inputDataFloat.get(1);
            MLRdata.set(3,averageWriterRating);
        } else {MLRdata.set(3,null);}

        //check cast
        if (!inputData.get(7).equals("Unused predictor")) {
            double averageCastRating = inputDataFloat.get(2);
            MLRdata.set(4,averageCastRating);
        } else {MLRdata.set(4,null);}

        //check language
        if (!inputData.get(8).contains("Unused predictor")) {
            String languages = inputData.get(8);
            if (languages.contains("English")) {MLRdata.set(35,Double.parseDouble("1"));} else {{MLRdata.set(35,Double.parseDouble("0.0001"));}}
            if (languages.contains("Spanish")) {MLRdata.set(36,Double.parseDouble("1"));} else {{MLRdata.set(36,Double.parseDouble("0.0001"));}}
            if (languages.contains("European")) {MLRdata.set(37,Double.parseDouble("1"));} else {{MLRdata.set(37,Double.parseDouble("0.0001"));}}
            if (languages.contains("Arabic")) {MLRdata.set(38,Double.parseDouble("1"));} else {{MLRdata.set(38,Double.parseDouble("0.0001"));}}
            if (languages.contains("Asian")) {MLRdata.set(39,Double.parseDouble("1"));} else {{MLRdata.set(39,Double.parseDouble("0.0001"));}}
            if (languages.contains("Other")) {MLRdata.set(40,Double.parseDouble("1"));} else {{MLRdata.set(40,Double.parseDouble("0.0001"));}}
        } else {MLRdata.set(35,null);MLRdata.set(36,null);MLRdata.set(37,null);MLRdata.set(38,null);MLRdata.set(39,null);MLRdata.set(40,null);}

        //check country
        if (!inputData.get(9).contains("Unused predictor")) {
            String languages = inputData.get(9);
            if (languages.contains("English")) {MLRdata.set(41,Double.parseDouble("1"));} else {{MLRdata.set(41,Double.parseDouble("0.0001"));}}
            if (languages.contains("Spanish")) {MLRdata.set(42,Double.parseDouble("1"));} else {{MLRdata.set(42,Double.parseDouble("0.0001"));}}
            if (languages.contains("European")) {MLRdata.set(43,Double.parseDouble("1"));} else {{MLRdata.set(43,Double.parseDouble("0.0001"));}}
            if (languages.contains("Arabic")) {MLRdata.set(44,Double.parseDouble("1"));} else {{MLRdata.set(44,Double.parseDouble("0.0001"));}}
            if (languages.contains("Asian")) {MLRdata.set(45,Double.parseDouble("1"));} else {{MLRdata.set(45,Double.parseDouble("0.0001"));}}
            if (languages.contains("Other")) {MLRdata.set(46,Double.parseDouble("1"));} else {{MLRdata.set(46,Double.parseDouble("0.0001"));}}
        } else {MLRdata.set(41,null);MLRdata.set(42,null);MLRdata.set(43,null);MLRdata.set(44,null);MLRdata.set(45,null);MLRdata.set(46,null);}


    }




    private Matrix getBetas(double[][] predictorData, double[][] outputData) throws Exception {
        final MultiLinear ml = new MultiLinear(new Matrix(predictorData), new Matrix(outputData));
        final Matrix beta = ml.calculate();

        for (int i =0; i < beta.getNrows(); i++) {
            System.out.println(beta.getValueAt(i,0));
        }


//        //print betas
//        System.out.println( "Intercept: " + beta.getValueAt(0, 0));
//        System.out.println( "Runtime beta" + beta.getValueAt(1, 0));
//        System.out.println( "AvgDirectorRating beta" + beta.getValueAt(2, 0));
//        System.out.println( "AvgWriterRating beta" + beta.getValueAt(3, 0));
//        System.out.println( "AvgCastRating beta" + beta.getValueAt(4, 0));

        return beta;
    }




    private ResultSet getTrainingData() {
        ResultSet trainingData = null;

        //query
        try {
            //load driver
            Class.forName("org.relique.jdbc.csv.CsvDriver");

            //configure database connection
            Connection conn = null;

            //get execution path to detect jar execution
            String executionPath = this.getClass().getResource("/mit3prototype/data/mainDatabase.csv").toExternalForm();

            //jar data connection
            if (executionPath.startsWith("jar:")) {
                conn = DriverManager.getConnection("jdbc:relique:csv:class:" + MainDbReader.class.getName());
            }

            //ide data connection
            else if (executionPath.startsWith("file:")) {
                String path = this.getClass().getResource("/mit3prototype/data").toExternalForm();
                path = path.substring("file:".length());
                conn = DriverManager.getConnection("jdbc:relique:csv:" + path);
            }


            //do the query
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM mainDatabase");
            trainingData = stmt.executeQuery();

        } catch (Exception e) { e.printStackTrace(); }

        return trainingData;
    }

}

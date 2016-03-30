import org.relique.jdbc.csv.CsvDriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;



//This program can be used to get movie info, get movie budget and revenue or calculate average ratings for directors, writers and cast.
//
//>>>>>  switch the correct comments before usage <<<<<<
//



public class WorkerThread implements Runnable {

    //thread variables
    private String command;
    private String imdbID;

    //static values for all threads (used to limit API data rates or display progress information)
    private static Connection conn = null;
    private static int counter = 0;
    private static int calculatedTargets = 0;
    private static long lastTime = System.currentTimeMillis();
    private static int requestsSinceLastTime = 0;
    private static List<String> targetImdbIDs= new ArrayList<String>();

    //default values to write to csv if calculation fails
    private String budget = "NF";
    private String revenue = "NF";


    //thread constructor (initiate database connection only once at first run)
    public WorkerThread(String directorField, String imdbID){
    //public WorkerThread(String imdbID){
        this.command=directorField;
        this.imdbID = imdbID;

        if (conn==null) {
            try {
                //establish static connection for all threads
                conn = DriverManager.getConnection("jdbc:relique:csv:" + "resources");
            } catch (Exception e) {e.printStackTrace();}

            //>>> output header: <<<
            addToNewCsvFile("imdbID,English,Spanish,European,Asian,Arabic,Other\n");
            //addToNewCsvFile("imdbID,Action,Adventure,Animation,Comedy,Biography,Crime,Drama,Documentary,Family,Fantasy,History,Horror,Mystery,Romance,Sci-Fi,Thriller,Western,Sport,Music,Musical,War\n");
        }
    }

    //sets the target IDs (used to compare mainDataset info to calculationDataset info)
    public void setTargets(List<String> targets) {
        //fill static list with all target ID's
        this.targetImdbIDs = targets;
        System.out.println("List of target imdbIDs set. total targets: "+targetImdbIDs.size() + ". Program will skip calculations for movies which ID are not featured in this list");
        System.out.println("Started calculating...");
    }


    //this method is executed when a new runnable worker thread is initiated
    @Override
    public void run() {
        counter++;
        if (counter <= 0) {
            //skip first x movies to prevent starting from scratch (edit if statement accordingly)
        }
        else if (command.equals("False")) {
            //skip threads that have been initiated with False command
        }
        else {
            //execute main thread method
            processCommand();
        }

        //print status
        if (calculatedTargets%50==1) {
            System.out.println("Target movies handled(main dataset): " + calculatedTargets + " out of "+ targetImdbIDs.size() + " movies.\nTotal movies handled(calculation dataset): " + counter);
        }
    }


    //main thread method
    private void processCommand() {
        try {
            //>>> uncomment the section you want to use <<<
            // (only use 1 at a time, switch other comments to the correct method aswell)


            //create genre dummy variables
//            List<String> movieGenres = getMovieGenres(command); //format: Action, Adventure, Animation, Comedy, Biography, Crime, Drama, Documentary, Family, Fantasy, History, Horror, Mystery, Romance, Sci-Fi, Thriller, Western, Sport, Music, Musical, War
//            String outputRow = imdbID;
//            for (String genre : movieGenres) {
//                outputRow = outputRow + ("," + genre);
//            }
//            addToNewCsvFile(outputRow + "\n");



            //create language dummy variables
//            List<String> movieLanguages = getMovieLanguages(command); //format: English,Spanish,European,Asian,Arabic,Other
//            String outputRow = imdbID;
//            for (String genre : movieLanguages) {
//                outputRow = outputRow + ("," + genre);
//            }
//            addToNewCsvFile(outputRow + "\n");


            //create country dummy variables
            List<String> movieCountrys = getMovieCountrys(command); //format: English,Spanish,European,Asian,Arabic,Other
            String outputRow = imdbID;
            for (String genre : movieCountrys) {
                outputRow = outputRow + ("," + genre);
            }
            addToNewCsvFile(outputRow + "\n");


            //>>> fix movie info <<<
//            List<String> movieInfo = fixMovieInfo(); //format: imdbID,Title,Year,Rating,Runtime,Genre,Released,Director,Writer,Cast,imdbRating,imdbVotes,language,country
//            addToNewCsvFile(imdbID + ",\"" + movieInfo.get(0) + "\"," + movieInfo.get(1)  + ",\"" + movieInfo.get(2)  + "\",\"" + movieInfo.get(3)  + "\",\"" + movieInfo.get(4)  + "\",\"" + movieInfo.get(5)  + "\",\"" + movieInfo.get(6)  + "\",\"" + movieInfo.get(7)  + "\",\"" + movieInfo.get(8)  + "\",\"" + movieInfo.get(9)  + "\",\"" + movieInfo.get(10)  + "\",\"" + movieInfo.get(11) + "\",\""  + movieInfo.get(12) + "\"\n");

            //>>> calculate average cast rating: <<<
//            for (String target : targetImdbIDs) {
//                if (target.equals(imdbID)) {
//                    String avrgCastRating = calculateAvrgCastRating(command, "ratingCalcDatabase");
//                    addToNewCsvFile(imdbID + "," + avrgCastRating + "\n");
//                }
//            }

            //>>> calculate average writer rating: <<<
//            for (String target : targetImdbIDs) {
//                if (target.equals(imdbID)) {
//                    String avrgWriterRating = calculateAvrgWriterRating(command, "ratingCalcDatabase");
//                    addToNewCsvFile(imdbID + "," + avrgWriterRating + "\n");
//                }
//            }

            //>>> calculate average director rating: <<<
//            for (String target : targetImdbIDs) {
//                if (target.equals(imdbID)) {
//                    String avrgDirectorRating = calculateAvrgDirectorRating(command, "ratingCalcDatabase");
//                    addToNewCsvFile(imdbID + "," + avrgDirectorRating + "\n");
//                }
//            }


            //>>> get movie budget: <<<
//            getMovieBudget();
//            addToNewCsvFile(imdbID + "," + budget + "," + revenue + "\n");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.command;
    }


    //writes the given row to the output csv file
    private void addToNewCsvFile(String row) {
        try {
            //handle results
            System.out.print(row);
            calculatedTargets++;

            try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("resources/output.csv", true)))) {
                out.print(row);
            }catch (IOException e) {
                System.out.println("An error occured writing to the output file. Write skipped for row: (" + row + ")");
            }


        } catch(Exception e) {e.printStackTrace();}

    }


    //this method returns a list of strings with the movies genres
    //format: Action, Adventure, Comedy, Biography, Crime, Drama, Documentary, Family, Fantasy, History, Horror, Mystery, Romance, Sci-Fi, Thriller, Western, Sport, Music, Musical, War
    private List<String> getMovieGenres(String genreField){
        List<String> genres = new ArrayList<String>();

        if (genreField.contains("Action")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Adventure")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Animation")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Comedy")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Biography")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Crime")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Drama")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Documentary")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Family")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Fantasy")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("History")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Horror")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Mystery")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Romance")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Sci-Fi")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Thriller")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Western")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Sport")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Music")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("Musical")) {genres.add("1");} else {genres.add("0");}
        if (genreField.contains("War")) {genres.add("1");} else {genres.add("0");}

        boolean atleastOneGenre = false;
        for (String genre : genres) {
            if (genre.equals("1")) {
                atleastOneGenre = true;
            }
        }

        if (!atleastOneGenre) {
            genres.set(0,"Error");
        }

        return genres;
    }

    //this method returns a list of strings with the movie language dummy variables
    //format: English,Spanish,European,Asian,Arabic,Other
    private List<String> getMovieLanguages(String languageField){
        List<String> languages = new ArrayList<String>();

        if (languageField.contains("English")  || languageField.contains("Irish") || languageField.contains("Scottish")) {languages.add("1");} else {languages.add("0");}
        if (languageField.contains("Spanish") || languageField.contains("Portuguese")) {languages.add("1");} else {languages.add("0");}
        if (languageField.contains("Dutch") || languageField.contains("French") || languageField.contains("German") || languageField.contains("Italian") || languageField.contains("Russian") || languageField.contains("Swedish") || languageField.contains("Danish") || languageField.contains("Finnish") || languageField.contains("Norwegian")) {languages.add("1");} else {languages.add("0");}
        if (languageField.contains("Mandarin") || languageField.contains("Japanese") || languageField.contains("Chinese") || languageField.contains("Korean")) {languages.add("1");} else {languages.add("0");}
        if (languageField.contains("Arabic") || languageField.contains("Hebrew") || languageField.contains("Persian") || languageField.contains("Persian")) {languages.add("1");} else {languages.add("0");}


        boolean atleastOneLanguage = false;
        for (String lang : languages) {
            if (lang.equals("1")) {
                atleastOneLanguage = true;
            }
        }

        if (!atleastOneLanguage) {
            languages.add("1"); //'other' language category is true
        }
        else {
            languages.add("0"); //'other' language category is false
        }

        return languages;
    }


    //this method returns a list of strings with the movie country dummy variables
    //format: English,Spanish,European,Asian,Arabic,Other
    private List<String> getMovieCountrys(String countryField){
        List<String> countrys = new ArrayList<String>();

        if (countryField.contains("Canada")  || countryField.contains("Ireland") || countryField.contains("Scottland") || countryField.contains("USA") || countryField.contains("UK") || countryField.contains("Australia")) {countrys.add("1");} else {countrys.add("0");}
        if (countryField.contains("Spain") || countryField.contains("Portugal") || countryField.contains("Argentina") || countryField.contains("Brazil")) {countrys.add("1");} else {countrys.add("0");}
        if (countryField.contains("Netherlands") || countryField.contains("France") || countryField.contains("Germany") || countryField.contains("Italy") || countryField.contains("Russia") || countryField.contains("Sweden") || countryField.contains("Denmark") || countryField.contains("Finland") || countryField.contains("Norway")) {countrys.add("1");} else {countrys.add("0");}
        if (countryField.contains("Taiwan") || countryField.contains("Japan") || countryField.contains("China") || countryField.contains("Korea")) {countrys.add("1");} else {countrys.add("0");}
        if (countryField.contains("Qatar") || countryField.contains("United Arab Emirates") || countryField.contains("Israel") || countryField.contains("Persian")) {countrys.add("1");} else {countrys.add("0");}


        boolean atleastOneCountry = false;
        for (String country : countrys) {
            if (country.equals("1")) {
                atleastOneCountry = true;
            }
        }

        if (!atleastOneCountry) {
            countrys.add("1"); //'other' language category is true
        }
        else {
            countrys.add("0"); //'other' language category is false
        }

        return countrys;
    }


    //This method calculates the total average imdbRating for the cast of this movie. calculating source data is found in the given dataset
    private String calculateAvrgCastRating(String castField, String dataset) {
        //get a list of all directors for this movie
        List<String> cast = new ArrayList<String>();
        //split String if multiple directors are involved
        castField = castField.replaceAll("'", "''");
        while (castField.contains(",")) {
            //get last name in field
            int lastDirectorIndex = castField.lastIndexOf(',');
            String director = castField.substring(lastDirectorIndex+2);
            castField = castField.substring(0,lastDirectorIndex);
            cast.add(director);
        }
        //add remaining director to director list
        cast.add(castField);
        //System.out.println(directors.toString());


        //calculate each directors average rating
        List<Float> avrgRatings = new ArrayList<Float>();
        for (String castMember : cast) {
            //get film ratings of every film featuring the director
            try {
                conn.setAutoCommit(false);
                PreparedStatement stmt = conn.prepareStatement("SELECT imdbRating FROM "+dataset+" WHERE Cast LIKE '%"+castMember+"%'");
                ResultSet ratings = stmt.executeQuery();

                float total = 0, count = 0;
                while (ratings.next()) {
                    total += Float.parseFloat(ratings.getString("imdbRating"));
                    count++;
                }

                float avrg = total / count;
                avrgRatings.add(avrg);
                //boolean append = true;
                //CsvDriver.writeToCsv(ratings, System.out, append);
            }
            catch(Exception e) {e.printStackTrace();}

        }


        //calculate average rating of all directors involved
        float total = 0, count = 0;
        for (float avrgRating : avrgRatings) {
            //System.out.print(" avr rating: "+avrgRating+".");
            total += avrgRating;
            count++;
        }
        String averageDirectorsScore = String.format("%.1f", (total/count));


        return averageDirectorsScore;
    }



    //This method prints the movie info from the given movie based on imdbID
    private List<String> fixMovieInfo() {
        List<String> movieInfo = new ArrayList<String>();

        //get movie info from correct csv
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("SELECT imdbID,Title,Year,Rating,Runtime,Genre,Released,Director,Writer,Cast,imdbRating,imdbVotes,Language,Country FROM datafix WHERE imdbID = '"+imdbID+"'");
            ResultSet infoNew = stmt.executeQuery();

            //boolean append = true;
            //CsvDriver.writeToCsv(infoNew, System.out, append);

            //write correct info to new csv
            infoNew.next();
            movieInfo.add(infoNew.getString("Title"));
            movieInfo.add(infoNew.getString("Year"));
            movieInfo.add(infoNew.getString("Rating"));
            movieInfo.add(infoNew.getString("Runtime"));
            movieInfo.add(infoNew.getString("Genre"));
            movieInfo.add(infoNew.getString("Released"));
            movieInfo.add(infoNew.getString("Director"));

            String writers = infoNew.getString("Writer").replaceAll("\"", "\"\"");
            movieInfo.add(writers);


            movieInfo.add(infoNew.getString("Cast"));
            movieInfo.add(infoNew.getString("imdbRating"));
            movieInfo.add(infoNew.getString("imdbVotes"));
            movieInfo.add(infoNew.getString("Language"));
            movieInfo.add(infoNew.getString("Country"));

        }
        catch(Exception e) {e.printStackTrace();}

        return movieInfo;
    }



    //This method uses the TMDB API to get movie budget and movie revenue of a certain movie
    private void getMovieBudget() {
        String apiUrl = "http://api.themoviedb.org/3/movie/"+imdbID+"?api_key=a9c6452cf540c66c984aabfb30bf16ef";

        String response = "";
        try {

            //check API rate limit and prevent hitting it (API rate limit is max 30 requests per 10 seconds)
            if (requestsSinceLastTime >= 30) {
                while ((System.currentTimeMillis()-lastTime)<=10000) {
                    Thread.sleep(50);
                    //System.out.println("waiting");
                }
                requestsSinceLastTime = 0;
                lastTime = System.currentTimeMillis();
            }
            requestsSinceLastTime++;


            final URL requestUrl = new URL(apiUrl);
            URLConnection yc = requestUrl.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                //System.out.println(inputLine);
                response += inputLine;
            in.close();

            //limit api data rate
            Thread.sleep(50);

            int budgetIndexStart = response.indexOf("\"budget\":")+9;
            int budgetIndexEnd = response.indexOf(",\"genres\":");
            budget = response.substring(budgetIndexStart, budgetIndexEnd);

            int revenueIndexStart = response.indexOf("\"revenue\":")+10;
            int revenueIndexEnd = response.indexOf(",\"runtime\":");
            revenue = response.substring(revenueIndexStart, revenueIndexEnd);

            //System.out.println(revenue);


        } catch (Exception e) {
            e.printStackTrace();
            budget = "NF";
            revenue = "NF";
        }

        //System.out.println(response);



    }



    //this method calculates the total average directors rating of all directors in the directorfield. calculating source data is found in the given dataset
    private String calculateAvrgDirectorRating(String directorField, String dataset) {
        //get a list of all directors for this movie
        List<String> directors = new ArrayList<String>();
        //split String if multiple directors are involved
        directorField = directorField.replaceAll("'", "''");
        while (directorField.contains(",")) {
            //get last name in field
            int lastDirectorIndex = directorField.lastIndexOf(',');
            String director = directorField.substring(lastDirectorIndex+2);
            directorField = directorField.substring(0,lastDirectorIndex);
            directors.add(director);
        }
        //add remaining director to director list
        directors.add(directorField);
        //System.out.println(directors.toString());


        //calculate each directors average rating
        List<Float> avrgRatings = new ArrayList<Float>();
        for (String director : directors) {
            //get film ratings of every film featuring the director
            try {
                conn.setAutoCommit(false);
                PreparedStatement stmt = conn.prepareStatement("SELECT imdbRating FROM "+dataset+" WHERE Director LIKE '%"+director+"%'");
                ResultSet ratings = stmt.executeQuery();

                float total = 0, count = 0;
                while (ratings.next()) {
                    total += Float.parseFloat(ratings.getString("imdbRating"));
                    count++;
                }

                float avrg = total / count;
                avrgRatings.add(avrg);
                //boolean append = true;
                //CsvDriver.writeToCsv(ratings, System.out, append);
            }
            catch(Exception e) {e.printStackTrace();}

        }


        //calculate average rating of all directors involved
        float total = 0, count = 0;
        for (float avrgRating : avrgRatings) {
            //System.out.print(" avr rating: "+avrgRating+".");
            total += avrgRating;
            count++;
        }
        String averageDirectorsScore = String.format("%.1f", (total/count));


        return averageDirectorsScore;
    }


    //this method calculates the total average Writers rating of all writers in the writerfield. calculating source data is found in the given dataset
    private String calculateAvrgWriterRating(String writerField, String dataset) {
        //get a list of all directors for this movie
        List<String> writers = new ArrayList<String>();
        //split String if multiple directors are involved

        //fix for writer names containing an apostrophe
        writerField = writerField.replaceAll("'", "''");


        while (writerField.contains(",")) {
            //get last name in field
            int lastWriterIndex = writerField.lastIndexOf(',');
            String writer = writerField.substring(lastWriterIndex+2);

            //strip writer role from name
            if (writer.contains("(") && writer.contains(")")) {
                int roleStartIndex = writer.indexOf("(");
                writer = writer.substring(0,roleStartIndex);
                //System.out.println(writer);
            }

            writers.add(writer);
            writerField = writerField.substring(0,lastWriterIndex);
        }
        //add remaining director to director list
        writers.add(writerField);
        //System.out.println(writers.toString());


        //calculate each writers average rating
        List<Float> avrgRatings = new ArrayList<Float>();
        for (String writer : writers) {
            //get film ratings of every film featuring the director
            try {
                conn.setAutoCommit(false);
                PreparedStatement stmt = conn.prepareStatement("SELECT imdbRating FROM "+dataset+" WHERE Writer LIKE '%"+writer+"%'");
                ResultSet ratings = stmt.executeQuery();

                float total = 0, count = 0;
                while (ratings.next()) {
                    total += Float.parseFloat(ratings.getString("imdbRating"));
                    count++;
                }

                float avrg = total / count;
                avrgRatings.add(avrg);
                //boolean append = true;
                //CsvDriver.writeToCsv(ratings, System.out, append);
            }
            catch(Exception e) {e.printStackTrace();}

        }


        //calculate average rating of all directors involved
        float total = 0, count = 0;
        for (float avrgRating : avrgRatings) {
            //System.out.print(" avr rating: "+avrgRating+".");
            total += avrgRating;
            count++;
        }
        String averageWritersScore = String.format("%.1f", (total/count));


        return averageWritersScore;
    }
}

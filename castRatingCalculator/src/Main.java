import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//this program uses the csvjdbc library to work directly with csv files. get JAR at: http://csvjdbc.sourceforge.net/
import org.relique.jdbc.csv.CsvDriver;

public class Main {


    private static Connection conn;

    public static void main(String[] args) {
        try
        {
            //Load the driver.
            Class.forName("org.relique.jdbc.csv.CsvDriver");

            //Create a connection giving the directory that contains the csv files
            conn = DriverManager.getConnection("jdbc:relique:csv:" + "resources");

            //Statement object to execute the query
            Statement stmt = conn.createStatement();

            //Enter the query
            ResultSet results = stmt.executeQuery("SELECT imdbID,Director FROM data WHERE Rating != 'UNRATED'"); //"SELECT ID,Title,Runtime FROM data WHERE ID = '5310090'"

            //calculate average director rating per film
            int moviesDone = 0;
            int moviesToSkip = 0;
            while (results.next()) {
                if (moviesToSkip > 0) {
                    moviesToSkip--;
                }
                else {
                    if (moviesDone % 10 == 0) {
                        System.out.println("number of movies handled: " + moviesDone);
                    }
                    moviesDone++;
                    String directorField = results.getString("Director");
                    //System.out.print("Directors:" + directorField);
                    String avrgDirectorRating = calculateAvrgDirectorRating(directorField);
                    //System.out.println(" Average rating for these directors: " + avrgDirectorRating);

                    //save to csv in format: imdbId;avrgDirectorRating
                    addToNewCsvFile(results.getString("imdbID") + "," + avrgDirectorRating + "\n");
                }
            }

            //clean
            conn.close();
        }
        catch(Exception e) {e.printStackTrace();}
    }


    private static void addToNewCsvFile(String row) {
        try {
            //handle results
            System.out.print(row);

            try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("resources/output.csv", true)))) {
                out.print(row);
            }catch (IOException e) {
                //exception handling left as an exercise for the reader
            }


        } catch(Exception e) {e.printStackTrace();}

    }


    private static String calculateAvrgDirectorRating(String directorField) {
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
                    PreparedStatement stmt = conn.prepareStatement("SELECT imdbRating FROM data WHERE Director LIKE '%"+director+"%'");
                    //System.out.println("SELECT imdbRating FROM data WHERE Director CONTAINS '"+director+"'");
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
}

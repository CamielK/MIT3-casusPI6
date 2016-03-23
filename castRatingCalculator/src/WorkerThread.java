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

public class WorkerThread implements Runnable {

    private String command;
    private String imdbID;
    private static Connection conn = null;
    private static int counter = 0;

    public WorkerThread(String directorField, String imdbID){
        this.command=directorField;
        this.imdbID = imdbID;

        if (conn==null) {
            try {
                conn = DriverManager.getConnection("jdbc:relique:csv:" + "resources");
            } catch (Exception e) {e.printStackTrace();}
        }

    }

    @Override
    public void run() {
        counter++;
        //System.out.println(Thread.currentThread().getName()+" Start. Command = "+command);
        processCommand();
        //System.out.println(Thread.currentThread().getName()+" End.");
        if (counter%50==0) {
            System.out.println("movies handled: " + counter);
        }
    }

    private void processCommand() {
        try {

            String avrgDirectorRating = calculateAvrgDirectorRating(command);

            addToNewCsvFile(imdbID + "," + avrgDirectorRating + "\n");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.command;
    }


    private void addToNewCsvFile(String row) {
        try {
            //handle results
            //System.out.print(row);

            try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("resources/output.csv", true)))) {
                out.print(row);
            }catch (IOException e) {
                //exception handling left as an exercise for the reader
            }


        } catch(Exception e) {e.printStackTrace();}

    }



    private String calculateAvrgDirectorRating(String directorField) {
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

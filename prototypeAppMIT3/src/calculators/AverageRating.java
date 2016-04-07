package calculators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camiel on 07-Apr-16.
 */
public class AverageRating {

    //database connection
    private static Connection conn;


    public AverageRating() {
        //establish static connection for all threads
        try {
            conn = DriverManager.getConnection("jdbc:relique:csv:" + "src/data");
        } catch (Exception e) {e.printStackTrace();}

    }


    //returns the combined average rating for a movie based on all given persons individual average imdb ratings in the given category
    //this method works for directors, writers or cast
    //for example: calling getAvgRating(directors, "director") returns the average rating for a film with all the given directors
    public float getAvgRating(List<String> persons, String category) {

        //get csv column name from category
        String csvColumnName = category.substring(0,1).toUpperCase() + category.substring(1,category.length());

        //calculate each writers average rating
        List<Float> avrgRatings = new ArrayList<Float>();
        for (String person : persons) {
            //get film ratings of every film featuring the person
            try {
                //execute query
                conn.setAutoCommit(false);
                PreparedStatement stmt = conn.prepareStatement("SELECT imdbRating FROM ratingCalcDatabase WHERE "+csvColumnName+" LIKE '%"+person+"%'");
                ResultSet ratings = stmt.executeQuery();

                //calculate average
                float total = 0, count = 0;
                while (ratings.next()) {
                    String nextRating = ratings.getString("imdbRating");
                    total += Float.parseFloat(nextRating);
                    count++;
                }
                float avrg = total / count;
                avrgRatings.add(avrg);
            }
            catch(Exception e) {e.printStackTrace();}

        }

        //calculate average rating of all people combined
        float total = 0, count = 0;
        for (float avrgRating : avrgRatings) {
            total += avrgRating;
            count++;
        }

        //return combined average rating
        return (total/count);
    }


}

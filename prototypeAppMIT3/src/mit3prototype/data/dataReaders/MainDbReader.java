package mit3prototype.data.dataReaders;

import org.relique.io.TableReader;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.JarURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

/**
 * Created by Camiel on 08-Apr-16.
 */
public class MainDbReader implements TableReader {

    public Reader getReader(Statement statement, String tableName) throws SQLException {
        //TODO add maindatabase to project
        try {
            URL path = new URL(this.getClass().getResource("/mit3prototype/data/mainDatabase.csv").toExternalForm());
            JarURLConnection connection = (JarURLConnection) path.openConnection();
            //InputStream data = JarReader.class.getClassLoader().getResourceAsStream("/mit3prototype/data/ratingCalcDatabase.csv");


            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            return reader;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }

    }


    public List getTableNames(Connection connection) {
        // Return list of available table names
        //TODO set correct column names
        Vector v = new Vector();

        v.add("imdbID");
        v.add("Title");
        v.add("YearsSinceRelease");
        v.add("Runtime");
        v.add("AvgDirectorRating");
        v.add("AvgWriterRating");
        v.add("AvgCastRating");
        v.add("imdbRating");
        v.add("Budget");
        v.add("Revenue");
        v.add("budget_norm_E8");
        v.add("revenue_norm_E8");
        v.add("mpaaRating_G");
        v.add("mpaaRating_NC-17");
        v.add("mpaaRating_PG");
        v.add("mpaaRating_PG-13");
        v.add("mpaaRating_R");
        v.add("mpaaRating_UNRATED");
        v.add("Genre_Action");
        v.add("Genre_Adventure");
        v.add("Genre_Animation");
        v.add("Genre_Comedy");
        v.add("Genre_Biography");
        v.add("Genre_Crime");
        v.add("Genre_Drama");
        v.add("Genre_Documentary");
        v.add("Genre_Family");
        v.add("Genre_Fantasy");
        v.add("Genre_History");
        v.add("Genre_Horror");
        v.add("Genre_Mystery");
        v.add("Genre_Romance");
        v.add("Genre_Sci-Fi");
        v.add("Genre_Thriller");
        v.add("Genre_Western");
        v.add("Genre_Sport");
        v.add("Genre_Music");
        v.add("Genre_Musical");
        v.add("Genre_War");
        v.add("Language_English");
        v.add("Language_Spanish");
        v.add("Language_European");
        v.add("Language_Asian");
        v.add("Language_Arabic");
        v.add("Language_Other");
        v.add("Countrys_English");
        v.add("Countrys_Spanish");
        v.add("Countrys_European");
        v.add("Countrys_Asian");
        v.add("Countrys_Arabic");
        v.add("Countrys_Other");


        return v;
    }

}
package mit3prototype.data.dataReaders;

import org.relique.io.TableReader;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.JarURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by user on 08-Apr-16.
 */
public class MainDbReader implements TableReader {

    public Reader getReader(Statement statement, String tableName) throws SQLException {
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
        Vector v = new Vector();

        v.add("imdbID");
        v.add("Title");
        v.add("YearsSinceRelease"); //0
        v.add("Runtime"); //1
        v.add("AvgDirectorRating"); //2
        v.add("AvgWriterRating"); //3
        v.add("AvgCastRating"); //4
        v.add("imdbRating"); //5
        v.add("Budget");
        v.add("Revenue");
        v.add("budget_norm_E8"); //6
        v.add("revenue_norm_E8"); //7
        v.add("mpaaRating_G"); //8
        v.add("mpaaRating_NC-17"); //9
        v.add("mpaaRating_PG"); //10
        v.add("mpaaRating_PG-13"); //11
        v.add("mpaaRating_R"); //12
        v.add("mpaaRating_UNRATED"); //13
        v.add("Genre_Action"); //14
        v.add("Genre_Adventure"); //15
        v.add("Genre_Animation"); //16
        v.add("Genre_Comedy"); //17
        v.add("Genre_Biography"); //18
        v.add("Genre_Crime"); //19
        v.add("Genre_Drama"); //20
        v.add("Genre_Documentary"); //21
        v.add("Genre_Family"); //22
        v.add("Genre_Fantasy"); //23
        v.add("Genre_History"); //24
        v.add("Genre_Horror"); //25
        v.add("Genre_Mystery"); //26
        v.add("Genre_Romance"); //27
        v.add("Genre_Sci-Fi"); //28
        v.add("Genre_Thriller"); //29
        v.add("Genre_Western"); //30
        v.add("Genre_Sport"); //31
        v.add("Genre_Music"); //32
        v.add("Genre_Musical"); //33
        v.add("Genre_War"); //34
        v.add("Language_English"); //35
        v.add("Language_Spanish"); //36
        v.add("Language_European"); //37
        v.add("Language_Asian"); //38
        v.add("Language_Arabic"); //39
        v.add("Language_Other"); //40
        v.add("Countrys_English"); //41
        v.add("Countrys_Spanish"); //42
        v.add("Countrys_European"); //43
        v.add("Countrys_Asian"); //44
        v.add("Countrys_Arabic"); //45
        v.add("Countrys_Other"); //46


        return v;
    }



    //get all records from the mainDatabase
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
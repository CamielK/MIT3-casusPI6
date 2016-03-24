import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            ResultSet results = stmt.executeQuery("SELECT imdbID FROM data");
            //ResultSet results = stmt.executeQuery("SELECT imdbID,Director FROM data");

            //handle threads
            ExecutorService executor = Executors.newFixedThreadPool(1);
            while (results.next()) {
                //start new thread
                Runnable worker = new WorkerThread(results.getString("imdbID"));
                //Runnable worker = new WorkerThread(results.getString("Director"), results.getString("imdbID"));
                executor.execute(worker);
            }

            //shutdown thread executor
            executor.shutdown();

            // Clean up
            conn.close();

            while (!executor.isTerminated()) {
            }
            System.out.println("Finished all threads");

        }
        catch(Exception e) {e.printStackTrace();}
    }

}
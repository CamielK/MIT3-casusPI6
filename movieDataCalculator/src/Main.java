import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//this program uses the csvjdbc library to work directly with csv files. get JAR at: http://csvjdbc.sourceforge.net/
import org.relique.jdbc.csv.CsvDriver;



//This program can be used to get movie info, get movie budget and revenue or calculate average ratings for directors, writers and cast.
//
//>>>>>  switch the correct comments before usage <<<<<<
//

public class Main {

    //database connection
    private static Connection conn;

    //max ammount of concurrent threads to optimize calculation speeds.
    //set at ~50 for calculations
    private static int threadPoolSize = 50;


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
            //ResultSet results = stmt.executeQuery("SELECT imdbID FROM data_unrated");
            //ResultSet results = stmt.executeQuery("SELECT imdbID,Director FROM ratingCalcDatabase WHERE imdbID = 'tt0103064'");
            //ResultSet results = stmt.executeQuery("SELECT imdbID,Director,Writer,Cast FROM ratingCalcDatabase WHERE imdbID = (SELECT imdbID FROM mainDatabase WHERE imdbID = 'tt0103064')");
            //ResultSet results = stmt.executeQuery("SELECT imdbID,Director FROM ratingCalcDatabase");
            //ResultSet results = stmt.executeQuery("SELECT imdbID,Writer FROM ratingCalcDatabase");
            ResultSet results = stmt.executeQuery("SELECT imdbID,Cast FROM ratingCalcDatabase");
            //ResultSet results = stmt.executeQuery("SELECT imdbID FROM data");


            //set static list of all target IDs for use in worker threads
            stmt = conn.createStatement();
            ResultSet targetIds = stmt.executeQuery("SELECT imdbID FROM mainDatabase");
            List<String> targetImdbIDs= new ArrayList<String>();
            while (targetIds.next()) {
                targetImdbIDs.add(targetIds.getString("imdbID"));
            }
            new WorkerThread("False","False").setTargets(targetImdbIDs);
            Thread.sleep(5000); //wait for first thread to finish


            // print all query results:
//            boolean append = true;
//            CsvDriver.writeToCsv(results, System.out, append);


            //handle threads (this will initiate a new worker thread as soon as a new thread is available)
            ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
            while (results.next()) {
                //start new thread:

                //Runnable worker = new WorkerThread(results.getString("imdbID"));
                //Runnable worker = new WorkerThread(results.getString("Director"), results.getString("imdbID"));
                //Runnable worker = new WorkerThread(results.getString("Writer"), results.getString("imdbID"));
                Runnable worker = new WorkerThread(results.getString("Cast"), results.getString("imdbID"));
                //Runnable worker = new WorkerThread(results.getString("imdbID"));
                executor.execute(worker);
            }

            // shutdown thread executor
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
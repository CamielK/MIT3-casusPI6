/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mit3prototype.data;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.*;

import org.relique.io.TableReader;

/**
 *
 * @author Camiel
 */
public class JarReader implements TableReader {

    private String databaseName;

    public JarReader (String database) {
        this.databaseName = database;
    }
    
    public Reader getReader(Statement statement, String tableName) throws SQLException {
        
        try {
            URL path = new URL(this.getClass().getResource("/mit3prototype/data/ratingCalcDatabase.csv").toExternalForm());
            JarURLConnection connection = (JarURLConnection) path.openConnection();
            //InputStream data = JarReader.class.getClassLoader().getResourceAsStream("/mit3prototype/data/ratingCalcDatabase.csv");
            
            
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            return reader;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
        
    }
    
    
    public List getTableNames(Connection connection)
  {
    // Return list of available table names
    Vector v = new Vector();
    v.add("imdbID");
    v.add("Director");
    v.add("Writer");
    v.add("Cast");
    v.add("imdbRating");
    return v;
  }
    
}

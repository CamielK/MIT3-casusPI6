import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Main {

    private String responseFormat = "json"; //can switch to "xml"


    public static void main(String[] args) throws Exception {
        new Main().crawlImdb();
    }

    public void crawlImdb() {

        for (int movieID = 1600000; movieID<=1600110; movieID++) {
            String id = "tt" + Integer.toString(movieID);
            String response = getMovieInfo(id);
            System.out.println(response);
        }

    }

    private String getMovieInfo(String movieID) {
        String url = "http://www.omdbapi.com/?i=" + movieID + "&plot=short&r=json";
        return httpRequest(url);
    }

    private String httpRequest(String url) {
        String response = "";
        try {
            final URL requestUrl = new URL(url);
            URLConnection yc = requestUrl.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                //System.out.println(inputLine);
                response += inputLine;
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}

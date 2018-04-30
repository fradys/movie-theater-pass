package net.androidbootcamp.movietheatremonthlypass;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MovieWebService {

    private static final String API_KEY = "0f6d387af45d98da9706514314e27849";

    private String poster_path_base = "http://image.tmdb.org/t/p/w185";

    private JSONArray genresList = new JSONArray();



    public MovieWebService()
    {

        String request = "https://api.themoviedb.org/3/genre/movie/list?api_key="+ API_KEY +"&language=en-US";

        JSONParser parser = new JSONParser();

        String s = jsonGetRequest(request);
        System.out.println("json request == " + s);

        try
        {
            genresList = (JSONArray)((JSONObject)parser.parse(s)).get("genres");

        }catch(ParseException pe){
            pe.printStackTrace();
        }


        //genresList

    }

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next(); // was \\Z
        return text;
    }

    public static String jsonGetRequest(String urlQueryString) {
        String json = null;
        try {
            URL url = new URL(urlQueryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(false); //was true
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream); // input stream to string
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }


    public Movie getMovieById(String id)
    {

        String request = "https://api.themoviedb.org/3/movie/"+id+"?api_key="+ API_KEY +"&language=en-US";

        JSONParser parser = new JSONParser();

        String s = jsonGetRequest(request);

        JSONObject movieResults = new JSONObject();

        try
        {
            movieResults = (JSONObject)parser.parse(s);

        }catch(ParseException pe){

        }

        return parseMovieJSON(movieResults);
    }

    public ArrayList<Movie> getMoviesInTheater()
    {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();

        String request = "https://api.themoviedb.org/3/movie/now_playing?api_key="+ API_KEY +"&language=en-US";

        JSONParser parser = new JSONParser();

        String s = jsonGetRequest(request);

        JSONArray movieResults = new JSONArray();

        try
        {
            movieResults = (JSONArray)((JSONObject)parser.parse(s)).get("results");

        }catch(ParseException pe){
            pe.printStackTrace();
        }

        for(int x = 0;x<movieResults.size();x++)
            moviesList.add(parseMovieJSON((JSONObject)movieResults.get(x)));



        return moviesList;
    }

    private Movie parseMovieJSON(JSONObject movieResults){

        String id = movieResults.get("id").toString();

        String title = movieResults.get("title").toString(),
                overview = movieResults.get("overview").toString(),
                imageUrl = poster_path_base + movieResults.get("poster_path");

        double voteAverage = Double.parseDouble(movieResults.get("vote_average").toString());
        String totalTime = "n/a";
        if(movieResults.get("runtime") != null)
            totalTime = movieResults.get("runtime").toString();

        String sDate1= movieResults.get("release_date").toString();

        Date releaseDate;

        try{
            releaseDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA).parse(sDate1);
        }catch(Exception e)
        {
            releaseDate = new Date();
        }

        JSONArray genresArray = (JSONArray) movieResults.get("genres");
        ArrayList<String> genres = new ArrayList<String>();

        if(genresArray == null)
        {

            JSONArray genre_ids = ((JSONArray) movieResults.get("genre_ids"));

            for(int y = 0; y < genre_ids.size();y++)
            {
                String currId = genre_ids.get(y).toString();

                for(int x = 0;x<genresList.size();x++)
                {
                    if(((JSONObject) genresList.get(x)).get("id").toString().equals(currId) )
                        genres.add(((JSONObject) genresList.get(x)).get("name").toString());
                }
            }

        }else
        {

            for(int x = 0; x < genresArray.size(); x++)
                genres.add(((JSONObject)genresArray.get(x)).get("name").toString());

        }
        return new Movie(id,title, overview, imageUrl,genres,releaseDate,voteAverage, totalTime);
    }


}

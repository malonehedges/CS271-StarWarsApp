package io.malone.starwarsapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Movie {

    public String title;
    public String episodeNumber;
    public String[] mainCharacters;
    public String description;
    public String poster;
    public String url;

    /**
     * Gets up to 3 main characters, joined by a comma, for display in the list view.
     * @return a String with up to 3 characters joined by a comma
     */
    public String getMainCharactersForDisplay() {
        int numberOfCharacters = Math.min(this.mainCharacters.length, 3);

        StringBuilder builder = new StringBuilder(this.mainCharacters[0]);
        for (int i = 1; i < numberOfCharacters; i++) {
            builder.append(", ");
            builder.append(this.mainCharacters[i]);
        }

        return builder.toString();
    }

    public static ArrayList<Movie> getMoviesFromFile(String filename, Context context){
        ArrayList<Movie> movieList = new ArrayList<>();

        // try to read from JSON file
        // get information by using the tags
        // construct a Movie Object for each movie in JSON
        // add the object to ArrayList
        // return ArrayList
        try {
            String jsonString = loadJSONStringFromAsset(filename, context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray movieJSONArray = json.getJSONArray("movies");

            // for loop to go through each movie in the movies.json file
            for (int i = 0; i < movieJSONArray.length(); i++) {
                JSONObject movieJSON = movieJSONArray.getJSONObject(i);

                Movie movie = new Movie();
                movie.title = movieJSON.getString("title");
                movie.episodeNumber = movieJSON.getString("episode_number");

                // handle array of characters
                JSONArray mainCharactersJSONArray = movieJSON.getJSONArray("main_characters");
                String[] mainCharacters = new String[mainCharactersJSONArray.length()];
                for (int j = 0; j < mainCharactersJSONArray.length(); j++) {
                    mainCharacters[j] = mainCharactersJSONArray.getString(j);
                }
                movie.mainCharacters = mainCharacters;

                movie.description = movieJSON.getString("description");
                movie.poster = movieJSON.getString("poster");
                movie.url = movieJSON.getString("url");

                // add to ArrayList
                movieList.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    // helper method that loads from any Json file
    private static String loadJSONStringFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}

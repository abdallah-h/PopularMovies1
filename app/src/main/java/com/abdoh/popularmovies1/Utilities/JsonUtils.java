package com.abdoh.popularmovies1.Utilities;

import com.abdoh.popularmovies1.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abdoh on 2018-02-23.
 */

public class JsonUtils {

    public static MovieInfo[] ParseJsonData(String JsonString) throws JSONException {

        JSONObject obj = new JSONObject(JsonString);
        JSONArray resultsArray = obj.getJSONArray("results");

        MovieInfo[] movieInfos = new MovieInfo[resultsArray.length()];

        // Traverse through movieInfo one by one and get data
        for (int i = 0; i < resultsArray.length(); i++) {
            // Initialize each object before it can be used
            movieInfos[i] = new MovieInfo();

            JSONObject movieInfo = resultsArray.getJSONObject(i);

            movieInfos[i].setTitle(movieInfo.getString("original_title"));
            movieInfos[i].setPoster(movieInfo.getString("poster_path"));
            movieInfos[i].setPlot(movieInfo.getString("overview"));
            movieInfos[i].setRating(movieInfo.getDouble("vote_average"));
            movieInfos[i].setReleaseDate(movieInfo.getString("release_date"));
        }

        return movieInfos;
    }

}

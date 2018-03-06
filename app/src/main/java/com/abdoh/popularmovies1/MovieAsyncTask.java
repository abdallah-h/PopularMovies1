package com.abdoh.popularmovies1;

import android.os.AsyncTask;
import android.util.Log;

import com.abdoh.popularmovies1.Utilities.JsonUtils;
import com.abdoh.popularmovies1.Utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import static com.abdoh.popularmovies1.Utilities.NetworkUtils.getResponseFromHttpUrl;

/**
 * Created by abdoh.
 */


class MovieAsyncTask extends AsyncTask<String, Void, MovieInfo[]> {


    private final String LOG_TAG = MovieAsyncTask.class.getSimpleName();

    private final OnTaskCompleted mListener;


    public MovieAsyncTask(OnTaskCompleted listener) {
        super();
        mListener = listener;
    }

    @Override
    protected MovieInfo[] doInBackground(String... params) {

        String JsonString = null;

        try {
            // build the uri with the givin parameter.
            URL url = NetworkUtils.buildUri(params);

            // get json data from the givin url.
            JsonString = getResponseFromHttpUrl(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }

        try {
            // get organized data from json data.
            return JsonUtils.ParseJsonData(JsonString);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(MovieInfo[] movieInfos) {
        super.onPostExecute(movieInfos);

        // Notify UI
        mListener.onFetchMoviesTaskCompleted(movieInfos);
    }
}

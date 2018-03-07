package com.abdoh.popularmovies1.Utilities;

import android.net.Uri;

import com.abdoh.popularmovies1.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by abdoh on 2018-03-06.
 */

public class NetworkUtils {

    public static URL buildUri(String[] parameters) throws MalformedURLException {

        final String BASE_URL = "http://api.themoviedb.org/3/movie";
        final String API_KEY_VARIABLE = "api_key";
        final String API_KEY_VALUE = BuildConfig.API_KEY;

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(parameters[0])
                .appendQueryParameter(API_KEY_VARIABLE, API_KEY_VALUE)
                .build();

        return new URL(builtUri.toString());
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

package com.abdoh.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by abdoh.
 */

public class MainActivity extends AppCompatActivity {


    private GridView gridView;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(moviePosterClickListener);

        if (savedInstanceState == null) {
            // Get data from the Internet
            getMoviesFromTMDb(getSortMethod());

        } else {

            Parcelable[] parcelable = savedInstanceState.
                    getParcelableArray(getString(R.string.parcel_movie));

            if (parcelable != null) {
                int numMovieObjects = parcelable.length;
                MovieInfo[] movieInfos = new MovieInfo[numMovieObjects];
                for (int i = 0; i < numMovieObjects; i++) {
                    movieInfos[i] = (MovieInfo) parcelable[i];
                }

                // Load movie objects into view
                gridView.setAdapter(new MovieAdapter(this, movieInfos));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, this.menu);

        this.menu = menu;

        this.menu.add(Menu.NONE, R.string.popular_sorting_key, Menu.NONE, null)
                .setVisible(false)
                .setIcon(R.drawable.ic_action_whatshot)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        this.menu.add(Menu.NONE, R.string.top_rated_sorting_key, Menu.NONE, null)
                .setVisible(false)
                .setIcon(R.drawable.ic_action_poll)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        // Update menu to show relevant items
        updateMenu();

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        int numMovieObjects = gridView.getCount();
        if (numMovieObjects > 0) {
            // Get MovieInfo objects from gridview
            MovieInfo[] movieInfos = new MovieInfo[numMovieObjects];
            for (int i = 0; i < numMovieObjects; i++) {
                movieInfos[i] = (MovieInfo) gridView.getItemAtPosition(i);
            }

            // Save MovieInfo objects to bundle
            outState.putParcelableArray(getString(R.string.parcel_movie), movieInfos);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.string.popular_sorting_key:
                updateSharedPrefs(getString(R.string.popular_sorting));
                updateMenu();
                getMoviesFromTMDb(getSortMethod());
                return true;
            case R.string.top_rated_sorting_key:
                updateSharedPrefs(getString(R.string.top_rated_sorting));
                updateMenu();
                getMoviesFromTMDb(getSortMethod());
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Listener for clicks on movie posters in GridView
     */
    private final GridView.OnItemClickListener moviePosterClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MovieInfo movieInfo = (MovieInfo) parent.getItemAtPosition(position);

            Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
            intent.putExtra(getResources().getString(R.string.parcel_movie), movieInfo);

            startActivity(intent);
        }
    };


    /*
        Execute the url by givin Sorting String
     */
    private void getMoviesFromTMDb(String sort) {
        if (isNetworkAvailable()) {

            // Listener for when AsyncTask is ready to update UI
            OnTaskCompleted taskCompleted = new OnTaskCompleted() {
                @Override
                public void onFetchMoviesTaskCompleted(MovieInfo[] movies) {
                    gridView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                }
            };
            // Execute the sort string.
            MovieAsyncTask movieTask = new MovieAsyncTask(taskCompleted);
            movieTask.execute(sort);
        } else {
            Toast.makeText(this, getString(R.string.error_need_internet), Toast.LENGTH_LONG).show();
        }
    }

    // Check if there is internet connection or not
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    /*
        Update menu based on the current sorted String in SharedPreferences
    */
    private void updateMenu() {
        String sort = getSortMethod();

        if (sort.equals(getString(R.string.popular_sorting))) {
            menu.findItem(R.string.popular_sorting_key).setVisible(false);
            menu.findItem(R.string.top_rated_sorting_key).setVisible(true);
        } else {
            menu.findItem(R.string.top_rated_sorting_key).setVisible(false);
            menu.findItem(R.string.popular_sorting_key).setVisible(true);
        }
    }

    /*
     * Gets the default sort string set by user from SharedPreferences.
     */
    private String getSortMethod() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        return defaultSharedPreferences.getString(getString(R.string.sort_method_key),
                getString(R.string.popular_sorting));
    }

    /*
     * Saves the selected sort method.
     */
    private void updateSharedPrefs(String sort) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.sort_method_key), sort);
        editor.apply();
    }
}


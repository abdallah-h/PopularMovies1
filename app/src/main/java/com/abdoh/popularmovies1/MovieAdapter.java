package com.abdoh.popularmovies1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by abdoh.
 */

class MovieAdapter extends BaseAdapter {
    private final Context mContext;
    private final MovieInfo[] movieInfos;


    public MovieAdapter(Context context, MovieInfo[] movieInfos) {
        mContext = context;
        this.movieInfos = movieInfos;
    }

    @Override
    public int getCount() {
        if (movieInfos == null || movieInfos.length == 0) {
            return -1;
        }

        return movieInfos.length;
    }

    @Override
    public MovieInfo getItem(int position) {
        if (movieInfos == null || movieInfos.length == 0) {
            return null;
        }

        return movieInfos[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(movieInfos[position].getPoster())
                .resize(mContext.getResources().getInteger(R.integer.poster_width),
                        mContext.getResources().getInteger(R.integer.poster_height))
                .error(R.drawable.no_poster)
                .placeholder(R.drawable.loading_poster)
                .into(imageView);

        return imageView;
    }
}

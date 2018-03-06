package com.abdoh.popularmovies1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdoh.
 */

public class MovieInfo implements Parcelable {
    private String title;
    private String poster;
    private String plot;
    private Double rating;
    private String releaseDate;


    public MovieInfo() {
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setPoster(String poster) {
        this.poster = poster;
    }


    public void setPlot(String plot) {
        if(!plot.equals("null")) {
            this.plot = plot;
        }
    }


    public void setRating(Double rating) {
        this.rating = rating;
    }


    public void setReleaseDate(String releaseDate) {
        if(!releaseDate.equals("null")) {
            this.releaseDate = releaseDate;
        }
    }


    public String getTitle() {
        return title;
    }


    public String getPoster() {
        final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

        return POSTER_BASE_URL + poster;
    }

    public String getPlot() {
        return plot;
    }
    private Double getRating() {
        return rating;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public String getDetailedVoteAverage() {
        return String.valueOf(getRating()) + "/10";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(plot);
        dest.writeValue(rating);
        dest.writeString(releaseDate);
    }

    private MovieInfo(Parcel in) {
        title = in.readString();
        poster = in.readString();
        plot = in.readString();
        rating = (Double) in.readValue(Double.class.getClassLoader());
        releaseDate = in.readString();
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        public MovieInfo createFromParcel(Parcel source) {
            return new MovieInfo(source);
        }

        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
}

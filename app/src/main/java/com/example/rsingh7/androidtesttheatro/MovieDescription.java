package com.example.rsingh7.androidtesttheatro;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.rsingh7.androidtesttheatro.controller.AppController;
import com.example.rsingh7.androidtesttheatro.model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MovieDescription extends AppCompatActivity {
    private static final String TAG = MovieDescription.class.getSimpleName();

    private static String url = null;
    private static NetworkImageView thumbNail;
    private static TextView title, genre, year, rating;
    ProgressDialog pDialog;
    ImageLoader imageLoader = null;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_description);

        pDialog = new ProgressDialog(this);
        thumbNail = findViewById(R.id.thumbnail);
        title = findViewById(R.id.descriptionTitle);
        rating = findViewById(R.id.rating);
        genre = findViewById(R.id.actor);
        year = findViewById(R.id.releaseYear);

        String imdID = getIntent().getExtras().getString("movieID");
        url = "http://www.omdbapi.com/?i="+ imdID +"&apikey=d34b6137";

        JsonObjectRequest movieReq1 = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        try {
                            if (imageLoader == null)
                                imageLoader = AppController.getInstance().getImageLoader();

                            movie = new Movie();
                            movie.setTitle(response.getString("Title"));
                            movie.setThumbnailUrl(response.getString("Poster"));
                            movie.setRating(response.getString("imdbRating"));
                            movie.setYear(response.getString("Released"));
                            movie.setGenre(response.getString("Actors"));

                            thumbNail.setImageUrl(Movie.getThumbnailUrl(),imageLoader);
                            title.setText("Title: "+movie.getTitle());
                            genre.setText("Actor: "+movie.getGenre());
                            year.setText("Released: "+movie.getYear());
                            rating.setText("Rating: "+movie.getRating());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq1);

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}

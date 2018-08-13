package com.example.rsingh7.androidtesttheatro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.rsingh7.androidtesttheatro.adapter.CustomListAdapter;
import com.example.rsingh7.androidtesttheatro.controller.AppController;
import com.example.rsingh7.androidtesttheatro.model.Movie;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MovieList extends AppCompatActivity {
    private static final String TAG = MovieList.class.getSimpleName();

    private static String url = null;
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        listView = findViewById(R.id.list);
        adapter = new CustomListAdapter(this, movieList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);

        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        //Movie Search url
        String searchText = getIntent().getExtras().getString("movieName");
        url = "http://www.omdbapi.com/?T="+searchText+"&apikey=d34b6137";

        // Creating volley request obj..Data is coming only in JSONObject so I am using JsonObject request
        JsonObjectRequest movieReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                            try {
                                Movie movie = new Movie();
                                movie.setTitle(response.getString("Title"));
                                movie.setThumbnailUrl(response.getString("Poster"));
                                movie.setImdbid(response.getString("imdbID"));

                                // adding movie to movies array
                                movieList.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                            //Select particular item and pass id to show detail
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String movieId = movieList.get(position).getImdbid();
                                    Intent intent = new Intent(MovieList.this, MovieDescription.class);
                                    intent.putExtra("movieID", movieId);
                                    startActivity(intent);
                                }
                            });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}

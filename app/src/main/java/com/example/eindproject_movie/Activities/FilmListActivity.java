package com.example.eindproject_movie.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eindproject_movie.Adapters.FilmListAdapter;
import com.example.eindproject_movie.Domein.ListFilm;
import com.example.eindproject_movie.R;
import com.google.gson.Gson;
import java.util.Objects;

public class FilmListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterFilmList;
    private RecyclerView recyclerViewFilmList;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar loadingFilmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);


        String selectedGenre = getIntent().getStringExtra("genre");

        initView();
        sendRequestFilmList(selectedGenre);
    }

    private void sendRequestFilmList(String selectedGenre) {
        mRequestQueue = Volley.newRequestQueue(this);
        loadingFilmList.setVisibility(View.VISIBLE);
        String apiUrl = "https://moviesapi.ir/api/v1/movies?page=1&genre=" + selectedGenre;

        mStringRequest = new StringRequest(Request.Method.GET, apiUrl, response -> {
            Gson gson = new Gson();
            loadingFilmList.setVisibility(View.GONE);
            ListFilm items = gson.fromJson(response, ListFilm.class);
            adapterFilmList = new FilmListAdapter(items);
            recyclerViewFilmList.setAdapter(adapterFilmList);
        }, error -> {
            loadingFilmList.setVisibility(View.GONE);
            Log.i("Sorry, er is iets mis", "onErrorResponse: " + error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void initView() {
        recyclerViewFilmList = findViewById(R.id.recyclerViewFilmList);
        recyclerViewFilmList.setLayoutManager(new LinearLayoutManager(this));
        loadingFilmList = findViewById(R.id.progressBarFilmList);
    }
}

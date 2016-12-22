package edu.gatech.projectyasuo.projectyasuoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Implementation of Search Movies
 * @author Jongwoo Jang
 */
public class SearchMovies extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);
        String[] recentMovies = Movie.getRecentMovies();
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recentMovies);
        listView.setAdapter(adapter);

    }

}
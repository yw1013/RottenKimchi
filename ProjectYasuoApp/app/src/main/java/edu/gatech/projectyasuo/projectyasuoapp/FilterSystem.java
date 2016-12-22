package edu.gatech.projectyasuo.projectyasuoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Filters movie by filter option chosen
 * @Author Jongwoo Jang
 */
public class FilterSystem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_movies);
        String[] sorted = Movie.getRecentMovies();
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sorted);
        listView.setAdapter(adapter);

    }

}
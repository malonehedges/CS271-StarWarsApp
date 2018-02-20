package io.malone.starwarsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int UPDATE_HAS_SEEN_STATUS_REQUEST = 1;

    private ListView mListView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        ArrayList<Movie> movieList = Movie.getMoviesFromFile("movies.json", this);
        final MovieAdapter adapter = new MovieAdapter(this, movieList);

        // find the ListView in the layout and set the adapter to ListView
        mListView = findViewById(R.id.movie_list_view);
        mListView.setAdapter(adapter);

        // make each item clickable
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) adapter.getItem(position);

                // create intent package and startActivity with that intent
                Intent detailIntent = new Intent(mContext, MovieDetailActivity.class);
                detailIntent.putExtra("position", position);
                detailIntent.putExtra("title", movie.title);
                detailIntent.putExtra("description", movie.description);
                detailIntent.putExtra("poster", movie.poster);
                detailIntent.putExtra("hasSeenStatus", adapter.getHasSeenStatus(position));

                startActivityForResult(detailIntent, UPDATE_HAS_SEEN_STATUS_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_HAS_SEEN_STATUS_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Get adapter
                MovieAdapter movieAdapter = (MovieAdapter) mListView.getAdapter();

                // Update the movie has seen status
                int position = data.getIntExtra("position", -1);
                String hasSeenStatus = data.getStringExtra("hasSeenStatus");
                // Update has seen status where the data is stored for the ListView
                movieAdapter.updateMovieSeenStatus(position, hasSeenStatus);

                // Notify data has changed
                movieAdapter.notifyDataSetChanged();
            }
        }
    }
}

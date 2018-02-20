package io.malone.starwarsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private String hasSeenStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle intentExtras = this.getIntent().getExtras();
        final int position = intentExtras.getInt("position");
        String title = intentExtras.getString("title");
        String description = intentExtras.getString("description");
        String poster = intentExtras.getString("poster");
        String incomingHasSeenStatus = intentExtras.getString("hasSeenStatus");

        // Set the hasSeenStatus instance variable to the incoming data
        this.hasSeenStatus = incomingHasSeenStatus;

        // set the title on the action bar
        setTitle(title);

        TextView titleTextView = findViewById(R.id.movie_detail_title);
        titleTextView.setText(title);

        TextView descriptionTextView = findViewById(R.id.movie_detail_description);
        descriptionTextView.setText(description);

        // handle submit button
        Button submitButton = findViewById(R.id.movie_detail_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("position", position);
                intent.putExtra("hasSeenStatus", hasSeenStatus);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // use Picasso library to load image from the image url
        ImageView thumbnailImageView = findViewById(R.id.movie_detail_thumbnail);
        Picasso.with(this).load(poster).into(thumbnailImageView);

        // Load radio buttons based on incoming value
        RadioButton alreadySeenRadioButton = findViewById(R.id.movie_detail_radio_as);
        RadioButton wantToSeeRadioButton = findViewById(R.id.movie_detail_radio_wts);
        RadioButton doNotLikeRadioButton = findViewById(R.id.movie_detail_radio_dnl);
        alreadySeenRadioButton.setChecked(incomingHasSeenStatus.equals(getString(R.string.has_seen_status_already_seen)));
        wantToSeeRadioButton.setChecked(incomingHasSeenStatus.equals(getString(R.string.has_seen_status_want_to_see)));
        doNotLikeRadioButton.setChecked(incomingHasSeenStatus.equals(getString(R.string.has_seen_status_do_not_like)));
    }

    public void onRadioChange(View view) {
        this.hasSeenStatus = (String) ((RadioButton) view).getText();
    }
}

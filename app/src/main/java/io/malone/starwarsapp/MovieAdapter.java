package io.malone.starwarsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Movie> mMovieList;
    private String[] hasSeenStatuses;

    private LayoutInflater mInflater;

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        this.mContext = context;
        this.mMovieList = movieList;
        this.hasSeenStatuses = new String[movieList.size()];

        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateMovieSeenStatus(int i, String status) {
        hasSeenStatuses[i] = status;
    }

    public String getHasSeenStatus(int i) {
        String hasSeenStatus = hasSeenStatuses[i];
        if (hasSeenStatus == null) {
            hasSeenStatus = mContext.getString(R.string.has_seen_status_default);
        }
        return hasSeenStatus;
    }

    private int getColorForStatus(String hasSeenStatus) {
        if (hasSeenStatus.equals(mContext.getString(R.string.has_seen_status_default))) {
            // Default to gray
            return mContext.getColor(R.color.has_seen_status_default_color);
        } else {
            // Otherwise make it blue to indicate the user has inputted their choice
            return mContext.getColor(R.color.has_seen_status_picked_color);
        }
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Override
    public Object getItem(int i) {
        return mMovieList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // check if the view already exists
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_movie, parent, false);

            // add the views to the holder
            holder = new ViewHolder();
            holder.thumbnailImageView = convertView.findViewById(R.id.movie_list_thumbnail);
            holder.titleTextView = convertView.findViewById(R.id.movie_list_title);
            holder.descriptionTextView = convertView.findViewById(R.id.movie_list_description);
            holder.mainCharactersTextView = convertView.findViewById(R.id.movie_list_main_characters);
            holder.hasSeenTextView = convertView.findViewById(R.id.movie_list_has_seen);

            // add the holder to the view
            convertView.setTag(holder);
        } else {
            // get the view holder from convertView
            holder = (ViewHolder) convertView.getTag();
        }

        // get the movie for this row
        Movie movie = (Movie) getItem(position);

        // get relative subview of the row view
        ImageView thumbnailImageView = holder.thumbnailImageView;
        TextView titleTextView = holder.titleTextView;
        TextView descriptionTextView = holder.descriptionTextView;
        TextView mainCharactersTextView = holder.mainCharactersTextView;
        TextView hasSeenTextView = holder.hasSeenTextView;

        // use Picasso library to load image from the image url
        Picasso.with(mContext).load(movie.poster).into(thumbnailImageView);

        titleTextView.setText(movie.title);
        descriptionTextView.setText(movie.description);
        mainCharactersTextView.setText(movie.getMainCharactersForDisplay());

        // Because we can't store hasSeen as a field on the Movie class, store this data separately
        String hasSeenStatus = getHasSeenStatus(position);
        hasSeenTextView.setText(hasSeenStatus);
        hasSeenTextView.setTextColor(getColorForStatus(hasSeenStatus));

        return convertView;
    }

    public static class ViewHolder {
        public ImageView thumbnailImageView;
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView mainCharactersTextView;
        public TextView hasSeenTextView;
    }
}

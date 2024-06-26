package com.example.spotifyapp2340.ui.wrapped;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.wrappers.ArtistObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * The type Artist adapter.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<ArtistObject> artistList;

    /**
     * Instantiates a new Artist adapter.
     *
     * @param context    the context
     * @param artistList the artist list
     */
    public ArtistAdapter(Context context, ArrayList<ArtistObject> artistList) {
        this.context = context;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public ArtistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_topartistswrapped, parent, false);
        return new ViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.ViewHolder holder, int position) {
        ArtistObject model = artistList.get(position);
        holder.artName.setText(model.getName());
        if (model.getImages().length > 0) {
            Picasso.get().load(model.getImages()[0].getUrl()).into(holder.artImage);
        }
        holder.artRating.setText(Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    /**
     * The type View holder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView artName;
        private final TextView artRating;
        private final ImageView artImage;
        private ArtistAdapter adapter;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artName = itemView.findViewById(R.id.artistName);
            artRating = itemView.findViewById(R.id.artistRating);
            artImage = itemView.findViewById(R.id.artistImage);
        }

        /**
         * Link adapter view holder.
         *
         * @param adapter the adapter
         * @return the view holder
         */
        public ViewHolder linkAdapter(ArtistAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}

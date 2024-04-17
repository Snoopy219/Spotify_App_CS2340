package com.example.spotifyapp2340.ui.wrapped;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.wrappers.TrackObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder>{
    private final Context context;
    private ArrayList<String> genreList;

    public GenreAdapter(Context context, ArrayList<String> genreList) {
        this.context = context;
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_topgenrewrapped, parent, false);
        return new ViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.ViewHolder holder, int position) {
        String model = genreList.get(position);
        System.out.println(holder.genreName);
        System.out.println(holder.genreRating);
        holder.genreName.setText(model);
//        holder.songName.setText(model.getName());
//        Picasso.get().load(model.getImages()[0].getUrl()).into(holder.songImage);
        holder.genreRating.setText(Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView genreName;
        private final TextView genreRating;
        private final ImageView genreImage;
        private GenreAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            genreImage = itemView.findViewById(R.id.genreImage);
            genreName = itemView.findViewById(R.id.genreName);
            genreRating = itemView.findViewById(R.id.genreRating);
        }

        public ViewHolder linkAdapter(GenreAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}

package com.example.spotifyapp2340.ui.wrapped;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.ui.timeMachine.TimeMachineCardAdapter;
import com.example.spotifyapp2340.wrappers.TrackObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{
    private final Context context;
    private ArrayList<TrackObject> trackList;

    public SongAdapter(Context context, ArrayList<TrackObject> trackList) {
        this.context = context;
        this.trackList = trackList;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_topsongswrapped, parent, false);
        return new ViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        TrackObject model = trackList.get(position);
        holder.songName.setText(model.getName());
        Picasso.get().load(model.getImages()[0].getUrl()).into(holder.songImage);
        holder.songRating.setText(Integer.toString(position + 1));
        if (MainActivity.currUser.isPremium()) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WrappedFragment.player.stop();
                    WrappedFragment.player.play(model.getUrl());
                    WrappedFragment.time = 0;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView songName;
        private final TextView songRating;
        private final ImageView songImage;
        private SongAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songName);
            songRating = itemView.findViewById(R.id.songRating);
            songImage = itemView.findViewById(R.id.songImage);
        }

        public ViewHolder linkAdapter(SongAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}

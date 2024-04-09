package com.example.spotifyapp2340.ui.timeMachine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.wrappers.Wrapped;

import java.util.ArrayList;

public class TimeMachineAdapter extends RecyclerView.Adapter<TimeMachineAdapter.ViewHolder>{
    private final Context context;
    private ArrayList<Wrapped> wrappedArrayList;

    public TimeMachineAdapter(Context context, ArrayList<Wrapped> wrappedArrayList) {
        this.context = context;
        this.wrappedArrayList = wrappedArrayList;
    }

    @NonNull
    @Override
    public TimeMachineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_past_wrapped, parent, false);
        return new ViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeMachineAdapter.ViewHolder holder, int position) {
        Wrapped model = wrappedArrayList.get(position);

        holder.dateTextView.setText(model.getDate().toString());
        holder.topGenreTextView.setText("Placeholder.");

        try {
            holder.song1TextView.setText(model.getTracks().get(0).getName());
            holder.song2TextView.setText(model.getTracks().get(1).getName());
            holder.song3TextView.setText(model.getTracks().get(2).getName());
            holder.song4TextView.setText(model.getTracks().get(3).getName());
            holder.song5TextView.setText(model.getTracks().get(4).getName());
        } catch (IndexOutOfBoundsException ignored){
        }

        try {
            holder.artist1TextView.setText(model.getArtists().get(0).getName());
            holder.artist2TextView.setText(model.getArtists().get(1).getName());
            holder.artist3TextView.setText(model.getArtists().get(2).getName());
            holder.artist4TextView.setText(model.getArtists().get(3).getName());
            holder.artist5TextView.setText(model.getArtists().get(4).getName());
        } catch (IndexOutOfBoundsException ignored){
        }
    }

    @Override
    public int getItemCount() {
        return wrappedArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView artist1TextView;
        public final TextView artist2TextView;

        public final TextView artist3TextView;
        public final TextView artist4TextView;
        public final TextView artist5TextView;
        public final TextView song1TextView;
        public final TextView song2TextView;

        public final TextView song3TextView;
        public final TextView song4TextView;
        public final TextView song5TextView;
        public final TextView topGenreTextView;

        public final TextView dateTextView;
        public TimeMachineAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            artist1TextView = itemView.findViewById(R.id.artist1TextView);
            artist2TextView = itemView.findViewById(R.id.artist2TextView);
            artist3TextView = itemView.findViewById(R.id.artist3TextView);
            artist4TextView = itemView.findViewById(R.id.artist4TextView);
            artist5TextView = itemView.findViewById(R.id.artist5TextView);
            song1TextView = itemView.findViewById(R.id.song1TextView);
            song2TextView = itemView.findViewById(R.id.song2TextView);
            song3TextView = itemView.findViewById(R.id.song3TextView);
            song4TextView = itemView.findViewById(R.id.song4TextView);
            song5TextView = itemView.findViewById(R.id.song5TextView);
            topGenreTextView = itemView.findViewById(R.id.topGenreTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }

        public ViewHolder linkAdapter(TimeMachineAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}
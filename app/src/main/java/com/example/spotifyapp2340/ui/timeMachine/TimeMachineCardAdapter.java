package com.example.spotifyapp2340.ui.timeMachine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.ui.timeMachine.TimeMachineAdapter;
import com.example.spotifyapp2340.wrappers.ArtistObject;
import com.example.spotifyapp2340.wrappers.Wrapped;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TimeMachineCardAdapter extends RecyclerView.Adapter<TimeMachineCardAdapter.ViewHolder>{
    private final Context context;
    private ArrayList<Wrapped> wrappedList;

    private static NavController navController;

    public TimeMachineCardAdapter(Context context, ArrayList<Wrapped> wrappedList, NavController navController) {
        this.context = context;
        this.wrappedList = wrappedList;
        this.navController = navController;
    }

    @NonNull
    @Override
    public TimeMachineCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_past_wrapped, parent, false);
        return new ViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeMachineCardAdapter.ViewHolder holder, int position) {
        Wrapped model = wrappedList.get(position);
        holder.dateName.setText(model.getDate().toString());
        Picasso.get().load(model.getArtists().get(0).getImages()[0].getUrl()).into(holder.artImage);
    }

    @Override
    public int getItemCount() {
        return wrappedList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateName;
        private final ImageView artImage;
        private TimeMachineCardAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateName = itemView.findViewById(R.id.dateText);
            artImage = itemView.findViewById(R.id.artistImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimeMachineCardAdapter.navController.navigate(R.id.action_navigation_timeMachine_to_wrap);
                }
            });
        }

        public ViewHolder linkAdapter(TimeMachineCardAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}
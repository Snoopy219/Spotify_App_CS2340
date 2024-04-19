package com.example.spotifyapp2340.ui.wrapped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.audioPlayer.AppPlayer;
import com.example.spotifyapp2340.databinding.FragmentWrappedBinding;
import com.example.spotifyapp2340.wrappers.ArtistObject;
import com.example.spotifyapp2340.wrappers.TrackObject;
import com.example.spotifyapp2340.wrappers.Wrapped;
import okhttp3.OkHttpClient;

/**
 * The type Dashboard fragment.
 */
public class WrappedFragment extends Fragment {

    private FragmentWrappedBinding binding;
    private Wrapped thisWrap;

    public static SongAdapter songAdapter;

    public static ArtistAdapter artistAdapter;
    public static GenreAdapter genreAdapter;
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    private Thread playSong;

    public final static AppPlayer player = new AppPlayer();

    private static boolean onWrapped = false;

    public static int index = 0;
    public static int time = 0;
    public static boolean isPaused = false;

    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWrappedBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        ((MainActivity) getActivity()).setNavView(View.GONE);
        ((MainActivity) getActivity()).setBackVisible(true);
        onWrapped = true;
        time = 0;
        //create click listeners for the titles
        TextView sectionTopSongs = root.findViewById(R.id.sectionTopSongs);
        TextView sectionTopArtists = root.findViewById(R.id.sectionTopArtists);
        TextView sectionTopGenres = root.findViewById(R.id.sectionTopGenres);
        sectionTopSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRecyclerViewVisibility(R.id.recyclerViewTopSongs);
            }
        });
        sectionTopArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRecyclerViewVisibility(R.id.recyclerViewTopArtists);
            }
        });
        sectionTopGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRecyclerViewVisibility(R.id.recyclerViewTopGenres);
            }
        });
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Wrapped wrapped = new Wrapped(Calendar.getInstance());
//                MainActivity.currUser.addWrapped(wrapped);
//                onNewWrapped(wrapped);
//                getArtistWrapped(wrapped);
//            }
//        };
//        thread.start();

        //int index = WrappedFragmentArgs.fromBundle(getArguments()).getIndex();
        System.out.println("im in wrapped");
//        int index = MainActivity.currUser.getWraps().size() - 1;
        if (index < MainActivity.currUser.getWraps().size()) {
            thisWrap = MainActivity.currUser.getWraps().get(index);
        } else {
            System.out.println("error getting wrapped");
            if (index - 1 < MainActivity.currUser.getWraps().size()) {
                thisWrap = MainActivity.currUser.getWraps().get(index - 1);
            }
        }
        System.out.println(thisWrap.getArtists().size());
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        //setup songs
        for (ArtistObject a : thisWrap.getArtists()) {
            System.out.println(a.getName());
        }
        RecyclerView trackCards = root.findViewById(R.id.recyclerViewTopSongs);
        songAdapter = new SongAdapter(getContext(), thisWrap.getTracks());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        trackCards.setLayoutManager(linearLayoutManager);
        trackCards.setAdapter(songAdapter);

//        setup artists
        RecyclerView artistCards = root.findViewById(R.id.recyclerViewTopArtists);
        artistAdapter = new ArtistAdapter(getContext(), thisWrap.getArtists());
        LinearLayoutManager linearLayoutManagerArtist = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        artistCards.setLayoutManager(linearLayoutManagerArtist);
        artistCards.setAdapter(artistAdapter);

        //        setup genre
        RecyclerView genreCards = root.findViewById(R.id.recyclerViewTopGenres);
        genreAdapter = new GenreAdapter(getContext(), thisWrap.getTopGenres());
        LinearLayoutManager linearLayoutManagerGenre = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        genreCards.setLayoutManager(linearLayoutManagerGenre);
        genreCards.setAdapter(genreAdapter);
        if (MainActivity.currUser.isPremium()) {
            playSong = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (Thread.interrupted()) {
                        if (player != null) {
                            player.stop();
                        }
                        return;
                    }
                    int count = 0;
                    while (onWrapped) {
                        if (Thread.interrupted()) {
                            if (player != null) {
                                player.stop();
                                System.out.println("interuppted here");
                            }
                            return;
                        } else {
                            TrackObject currTrack = thisWrap.getTracks().get(count);
                            while (currTrack.getUrl().equals("null")) {
                                count = (count + 1) % thisWrap.getTracks().size();
                                currTrack = thisWrap.getTracks().get(count);
                            }
                            if (!Thread.interrupted() && onWrapped && player != null) {
                                player.play(currTrack.getUrl());
                            }
                            try {
                                for (time = 0; time < 300; time++) {
                                    Thread.sleep(100);
                                    if (!onWrapped) {
                                        player.stop();
                                        return;
                                    }
                                    if (isPaused) {
                                        time--;
                                    }
                                }
                            } catch (InterruptedException e) {
                                System.out.println("interuppted");
                            }
                            count = (count + 1) % thisWrap.getTracks().size();
                        }
                    }
                }
            });
            playSong.start();
        }
        if (!MainActivity.currUser.isPremium()) {
            Toast toast = Toast.makeText(getContext(), "Not Premium", Toast.LENGTH_LONG);
            toast.show();
        }
        return root;
    }

    private void toggleRecyclerViewVisibility(int recyclerViewId) {
        RecyclerView recyclerView = root.findViewById(recyclerViewId);
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        if (playSong != null) {
            if (player != null) {
                System.out.println("destroying");
                player.stop();
                System.out.println(player);
            }
            playSong.interrupt();
        }
//        MainActivity.navController.popBackStack();
        onWrapped = false;
        ((MainActivity) getActivity()).setNavView(View.VISIBLE);
        ((MainActivity) getActivity()).setBackVisible(false);
        super.onDestroy();
    }
}
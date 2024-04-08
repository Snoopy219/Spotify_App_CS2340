package com.example.spotifyapp2340.ui.wrapped;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.audioPlayer.AppPlayer;
import com.example.spotifyapp2340.databinding.FragmentNewWrappedBinding;
import com.example.spotifyapp2340.databinding.FragmentWrappedBinding;
import com.example.spotifyapp2340.handleJSON.HANDLE_JSON;
import com.example.spotifyapp2340.ui.newWrapped.NewWrappedViewModel;
import com.example.spotifyapp2340.wrappers.ArtistObject;
import com.example.spotifyapp2340.wrappers.TrackObject;
import com.example.spotifyapp2340.wrappers.Wrapped;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    private final static AppPlayer player = new AppPlayer();

    private static boolean onWrapped = false;

    public static int index = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWrappedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        onWrapped = true;

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
        thisWrap = MainActivity.currUser.getWraps().get(index);
        System.out.println(thisWrap.getArtists().size());

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
                            for (int i = 0; i < 300; i++) {
                                Thread.sleep(100);
                                if (!onWrapped) {
                                    player.stop();
                                    return;
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
        return root;
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
        onWrapped = false;
        super.onDestroy();
    }
}
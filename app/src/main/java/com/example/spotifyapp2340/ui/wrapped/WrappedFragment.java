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
import com.example.spotifyapp2340.databinding.FragmentNewWrappedBinding;
import com.example.spotifyapp2340.databinding.FragmentWrappedBinding;
import com.example.spotifyapp2340.handleJSON.HANDLE_JSON;
import com.example.spotifyapp2340.ui.newWrapped.NewWrappedViewModel;
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
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    private static Call mCall;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWrappedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Thread thread = new Thread() {
            @Override
            public void run() {
                Wrapped wrapped = new Wrapped(Calendar.getInstance());
                MainActivity.currUser.addWrapped(wrapped);
                onNewWrapped(wrapped);
                getArtistWrapped(wrapped);
            }
        };
        thread.start();

        //int index = WrappedFragmentArgs.fromBundle(getArguments()).getIndex();
//        int index = MainActivity.currUser.getWraps().size();
//        thisWrap = MainActivity.currUser.getWraps().get(index);

        //setup songs
//        RecyclerView trackCards = root.findViewById(R.id.recyclerViewTopSongs);
//        songAdapter = new SongAdapter(getContext(), thisWrap.getTracks());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        trackCards.setLayoutManager(linearLayoutManager);
//        trackCards.setAdapter(songAdapter);

        //setup artists
//        RecyclerView artistCards = root.findViewById(R.id.recyclerViewTopArtists);
//        artistAdapter = new ArtistAdapter(getContext(), thisWrap.getArtists());
//        LinearLayoutManager linearLayoutManagerArtist = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        artistCards.setLayoutManager(linearLayoutManagerArtist);
//        artistCards.setAdapter(artistAdapter);
        return root;
    }

    public void notifyArt() {
        //artistAdapter.notifyDataSetChanged();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("here at art");
                RecyclerView artistCards = binding.getRoot().findViewById(R.id.recyclerViewTopArtists);
                artistAdapter = new ArtistAdapter(getContext(), MainActivity.currUser.getWraps().get(0).getArtists());
                LinearLayoutManager linearLayoutManagerArtist = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                artistCards.setLayoutManager(linearLayoutManagerArtist);
                artistCards.setAdapter(artistAdapter);
            }
        });

    }

    public void notifyTrack() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView trackCards = binding.getRoot().findViewById(R.id.recyclerViewTopSongs);
                songAdapter = new SongAdapter(getContext(), MainActivity.currUser.getWraps().get(0).getTracks());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                trackCards.setLayoutManager(linearLayoutManager);
                trackCards.setAdapter(songAdapter);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onNewWrapped(Wrapped wrapped) {
        //Getting tracks
        final Request req = new Request.Builder().url("https://api.spotify.com/v1/me/top/tracks")
                .addHeader("Authorization",
                        "Bearer " + MainActivity.mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(req);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String track = response.body().string();
                System.out.println("TRACK" + track);
                wrapped.setJSONTrack(track);
                notifyTrack();
                //setTextAsync(jsonObject.toString(3), profileTextView);
            }
        });

        //Getting artists

        //navigate to new wrap screen
        MainActivity.updateUser(MainActivity.currUser);
    }

    public void getArtistWrapped(Wrapped wrapped) {
        final Request req2 = new Request.Builder().url("https://api.spotify.com/v1/me/top/tracks")
                .addHeader("Authorization", "Bearer " + MainActivity.mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(req2);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String art = response.body().string();
                System.out.println("ART" + art);
//                try {
//                    MainActivity.currUser.getWraps().get(0).setArtists(HANDLE_JSON.createWrappedFromJSON()));
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
                wrapped.setJSONArt(art);
                notifyArt();
                //setTextAsync(jsonObject.toString(3), profileTextView);

            }
        });
    }

    private static AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(MainActivity.CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[] { "user-read-email" }) // <--- Change the scope of your requested token here
                .setCampaign("your-campaign-token")
                .build();
    }

    /**
     * Gets the redirect Uri for Spotify
     *
     * @return redirect Uri object
     */
    private static Uri getRedirectUri() {
        return Uri.parse(MainActivity.REDIRECT_URI);
    }

    private static void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
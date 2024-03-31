package com.example.spotifyapp2340.ui.wrapped;

import android.os.Bundle;
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
import com.example.spotifyapp2340.ui.newWrapped.NewWrappedViewModel;
import com.example.spotifyapp2340.wrappers.Wrapped;

/**
 * The type Dashboard fragment.
 */
public class WrappedFragment extends Fragment {

    private FragmentWrappedBinding binding;
    private Wrapped thisWrap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWrappedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //int index = WrappedFragmentArgs.fromBundle(getArguments()).getIndex();
        int index = MainActivity.currUser.getWraps().size() - 1;
        thisWrap = MainActivity.currUser.getWraps().get(index);

        //setup songs
        RecyclerView trackCards = root.findViewById(R.id.recyclerViewTopSongs);
        SongAdapter songAdapter = new SongAdapter(getContext(), thisWrap.getTracks());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        trackCards.setLayoutManager(linearLayoutManager);
        trackCards.setAdapter(songAdapter);

        //setup artists
        RecyclerView artistCards = root.findViewById(R.id.recyclerViewTopArtists);
        ArtistAdapter artistAdapter = new ArtistAdapter(getContext(), thisWrap.getArtists());
        LinearLayoutManager linearLayoutManagerArtist = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        artistCards.setLayoutManager(linearLayoutManagerArtist);
        artistCards.setAdapter(artistAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
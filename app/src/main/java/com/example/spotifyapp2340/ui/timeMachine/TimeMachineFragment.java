package com.example.spotifyapp2340.ui.timeMachine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.databinding.FragmentTimeMachineBinding;
import com.example.spotifyapp2340.wrappers.Wrapped;

import java.util.ArrayList;

/**
 * The type Time Machine fragment.
 */
public class TimeMachineFragment extends Fragment {

    private FragmentTimeMachineBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TimeMachineViewModel timeMachineViewModel =
                new ViewModelProvider(this).get(TimeMachineViewModel.class);

        binding = FragmentTimeMachineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Wrapped> currWraps = MainActivity.currUser.getWraps();
        TimeMachineAdapter recyclerAdapter = new TimeMachineAdapter(getContext(), currWraps);
        if (currWraps.size() > 0) {
            binding.recyclerviewTimemachine.setAdapter(recyclerAdapter);
            binding.textTimeMachine.setVisibility(View.INVISIBLE);
        } else {
            binding.recyclerviewTimemachine.setVisibility(View.INVISIBLE);
        }


        final TextView textView = binding.textTimeMachine;
        timeMachineViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
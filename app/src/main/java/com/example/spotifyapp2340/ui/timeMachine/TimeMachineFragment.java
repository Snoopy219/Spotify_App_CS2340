package com.example.spotifyapp2340.ui.timeMachine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifyapp2340.databinding.FragmentTimeMachineBinding;

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
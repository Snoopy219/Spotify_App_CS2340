package com.example.spotifyapp2340.ui.newWrapped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifyapp2340.databinding.FragmentNewWrappedBinding;

/**
 * The type Dashboard fragment.
 */
public class NewWrappedFragment extends Fragment {

    private FragmentNewWrappedBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewWrappedViewModel newWrappedViewModel =
                new ViewModelProvider(this).get(NewWrappedViewModel.class);

        binding = FragmentNewWrappedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.createNewWrapped;
        newWrappedViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
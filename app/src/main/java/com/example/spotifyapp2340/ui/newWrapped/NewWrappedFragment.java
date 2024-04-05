package com.example.spotifyapp2340.ui.newWrapped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.asyncTasks.NewWrappedAsync;
import com.example.spotifyapp2340.databinding.FragmentNewWrappedBinding;
import com.example.spotifyapp2340.wrappers.Wrapped;

import java.util.Calendar;

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

        binding.createNewWrapped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.createNewWrapped.setText("Loading...");
                new NewWrappedAsync(NavHostFragment.findNavController(NewWrappedFragment.this), getActivity()).execute();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
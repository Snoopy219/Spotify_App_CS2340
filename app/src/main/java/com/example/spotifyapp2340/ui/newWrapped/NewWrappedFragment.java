package com.example.spotifyapp2340.ui.newWrapped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.SpotifyCalls.SpotifyCalls;
import com.example.spotifyapp2340.asyncTasks.NewWrappedAsync;
import com.example.spotifyapp2340.databinding.FragmentNewWrappedBinding;
import com.example.spotifyapp2340.ui.wrapped.WrappedFragment;
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

        Spinner spinner = binding.getRoot().findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.time_options, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String timephase = (String) spinner.getSelectedItem();
                if (timephase.charAt(0) == 'S') {
                    NewWrappedAsync.timePhase = 0;
                } else if (timephase.charAt(0) == 'M') {
                    NewWrappedAsync.timePhase = 1;
                } else {
                    NewWrappedAsync.timePhase = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final TextView textView = binding.createNewWrapped;
        newWrappedViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.createNewWrapped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.createNewWrapped.setText("Loading...");
                WrappedFragment.index = MainActivity.currUser.getWraps().size();
                new NewWrappedAsync(NavHostFragment
                        .findNavController(NewWrappedFragment.this), getActivity()).execute();
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

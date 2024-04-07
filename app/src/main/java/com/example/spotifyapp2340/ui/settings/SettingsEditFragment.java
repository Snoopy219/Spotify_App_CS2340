package com.example.spotifyapp2340.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.databinding.FragmentSettingsBinding;
import com.example.spotifyapp2340.databinding.FragmentSettingsEditBinding;
import com.example.spotifyapp2340.storage.FIRESTORE;

/**
 * The type Settings fragment.
 */
public class SettingsEditFragment extends Fragment {

    private FragmentSettingsEditBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsEditBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.editName.setText(MainActivity.currUser.getId());

        binding.editSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.currUser.setDisplay_name(binding.editName.getText().toString());
                FIRESTORE.updateUser(MainActivity.currUser);

                //navigate back
                MainActivity.navController.popBackStack();

            }
        });

        binding.cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate back
                MainActivity.navController.popBackStack();
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
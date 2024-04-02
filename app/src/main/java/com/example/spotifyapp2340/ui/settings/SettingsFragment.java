package com.example.spotifyapp2340.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spotifyapp2340.LoginActivity;
import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.databinding.FragmentSettingsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * The type Settings fragment.
 */
public class SettingsFragment extends Fragment {


    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);


        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //need to firgure out how to do this so no null pointer exception
        while (MainActivity.currUser == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        binding.namePlaceHolder.setText(MainActivity.currUser.getDisplay_name());
        binding.textView6.setText(MainActivity.currUser.getId());
        binding.spotifyAccountPlaceHolder.setText(MainActivity.currUser.getEmail());

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.db.collection("users").document(MainActivity.currUser.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                MainActivity.currUser = null;
                                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                                editor.putString("user", "");
                                editor.commit();
                                Intent myIntent = new Intent(v.getContext(), LoginActivity.class);
                                startActivity(myIntent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
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
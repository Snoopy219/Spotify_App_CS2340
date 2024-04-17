package com.example.spotifyapp2340.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifyapp2340.LoginActivity;
import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.databinding.FragmentSettingsBinding;
import com.example.spotifyapp2340.handleJSON.HANDLE_JSON;
import com.example.spotifyapp2340.ui.timeMachine.TimeMachineFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * The type Settings fragment.
 */
public class SettingsFragment extends Fragment {


    private static FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);


        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if (binding != null && MainActivity.currUser != null) {
            binding.namePlaceHolder.setText(MainActivity.currUser.getDisplay_name());
            binding.textView6.setText(MainActivity.currUser.getId());
            binding.spotifyAccountPlaceHolder.setText(MainActivity.currUser.getEmail());
        }

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to edit
                NavController navController = MainActivity.navController;
                navController.navigate(R.id.settingsEditPage);
            }
        });
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteAccount();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();

                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                // on below line we are setting message for our dialog box.
                builder.setMessage("Are you sure you want to delete your account?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();// on below line we are creating a build
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor2 = LoginActivity.sharedPreferences.edit();
                editor2.putString("user", "");
                editor2.commit();
                MainActivity.navController.navigate(R.id.action_navigation_settings_to_navigation_newWrapped);
                Intent myIntent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(myIntent);
            }
        });

        return root;
    }

    private void deleteAccount() {
        System.out.println("deleting");
        SharedPreferences.Editor editor2 = LoginActivity.sharedPreferences.edit();
        editor2.putString("user", "");
        editor2.commit();
        MainActivity.db.collection("users").document(MainActivity.currUser.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences.Editor editor2 = LoginActivity.sharedPreferences.edit();
                        editor2.putString("user", "");
                        editor2.commit();
                        MainActivity.db.collection("/users/" + MainActivity.currUser.getId() + "/wraps")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                document.getReference().delete();
                                            }
                                            MainActivity.currUser = null;
                                            MainActivity.navController.navigate(R.id.action_navigation_settings_to_navigation_newWrapped);
                                            Intent myIntent = new Intent(getView().getContext(), LoginActivity.class);
                                            startActivity(myIntent);
                                        } else {
                                            System.out.println("failed");
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public static void onCallback() {
        if (binding != null) {
            binding.namePlaceHolder.setText(MainActivity.currUser.getDisplay_name());
            binding.textView6.setText(MainActivity.currUser.getId());
            binding.spotifyAccountPlaceHolder.setText(MainActivity.currUser.getEmail());
        }
    }

    public static void updateUser(){
        if (binding != null && MainActivity.currUser != null) {
            binding.namePlaceHolder.setText(MainActivity.currUser.getDisplay_name());
            binding.textView6.setText(MainActivity.currUser.getId());
            binding.spotifyAccountPlaceHolder.setText(MainActivity.currUser.getEmail());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.spotifyapp2340.storage;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.handleJSON.HANDLE_JSON;
import com.example.spotifyapp2340.ui.settings.SettingsFragment;
import com.example.spotifyapp2340.ui.timeMachine.TimeMachineFragment;
import com.example.spotifyapp2340.wrappers.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class FIRESTORE {
    /**
     * Please pass in a formatted string in the following way.
     * "Name: [username]; (JSON OBJECTS OF SERIALIZED SPOTIFY WRAPPED)
     *
     * @param id User to add
     */
    public static void newUser(String id) {
        DocumentReference docRef = MainActivity.db.collection("users").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println(document.getData().toString().substring(11));
                        MainActivity.currUser = HANDLE_JSON.createUserFromFirestore(document.getData().toString().substring(11));
                        if (MainActivity.mAccessToken == null) {
                            MainActivity.mAccessToken = MainActivity.currUser.getAccessToken();
                        }
                        MainActivity.currActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("MOVING");
                                MainActivity.navController.navigate(R.id.navigation_newWrapped);
                                TimeMachineFragment.updateUser();
                                SettingsFragment.updateUser();
                            }
                        });
                    } else {
                        //make new user
                        MainActivity.currUser = HANDLE_JSON.createUserFromJSON(MainActivity.userJSON.toString());
                        Map<String, String> user = new HashMap<>();
                        user.put("user_data", HANDLE_JSON.exportUser(MainActivity.currUser).toString());
//        usersWrapped.document(s.substring(0, s.indexOf(";" + SPLITTER))).set(user);
                        CollectionReference usersWrapped = MainActivity.db.collection("users");
                        usersWrapped.document(id).set(user);
                        MainActivity.mAccessToken = MainActivity.currUser.getAccessToken();
                        MainActivity.currActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("MOVING");
                                MainActivity.navController.navigate(R.id.navigation_newWrapped);
                                TimeMachineFragment.updateUser();
                                SettingsFragment.updateUser();
                            }
                        });
                    }
                } else {
                    System.out.println("unsuccessful");
                }
            }
        });
        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
        editor.putString("user", id);
        editor.commit();
//        CollectionReference usersWrapped = db.collection("users");
    }

    /**
     * Update user.
     *
     * @param user the user
     */
    public static void updateUser(User user) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("user_data", HANDLE_JSON.exportUser(user).toString());
//        usersWrapped.document(s.substring(0, s.indexOf(";" + SPLITTER))).set(user);
        CollectionReference usersWrapped = MainActivity.db.collection("users");
        usersWrapped.document(user.getId()).set(userMap);
    }

}

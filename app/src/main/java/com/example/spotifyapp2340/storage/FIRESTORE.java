package com.example.spotifyapp2340.storage;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.spotifyapp2340.LoginActivity;
import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.SpotifyCalls.SpotifyCalls;
import com.example.spotifyapp2340.asyncTasks.GetTokenAndRefreshToken;
import com.example.spotifyapp2340.asyncTasks.GetUserAsync;
import com.example.spotifyapp2340.asyncTasks.RefreshAsync;
import com.example.spotifyapp2340.asyncTasks.RefreshProductAsync;
import com.example.spotifyapp2340.handleJSON.HANDLE_JSON;
import com.example.spotifyapp2340.ui.settings.SettingsFragment;
import com.example.spotifyapp2340.ui.timeMachine.TimeMachineFragment;
import com.example.spotifyapp2340.wrappers.User;
import com.example.spotifyapp2340.wrappers.Wrapped;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling interactions with fetching and saving data to Firestore.
 */
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
                        new RefreshProductAsync().execute();
                        System.out.println(document.getData().toString().substring(11));
                        MainActivity.currUser =
                                HANDLE_JSON.createBasicUserFromJSON(document.getId(),
                                        document.getData().toString().substring(11));
                        if (MainActivity.mAccessToken == null) {
                            MainActivity.mAccessToken = MainActivity.currUser.getAccessToken();
                        }
                        if (MainActivity.refreshToken == null) {
                            MainActivity.refreshToken = MainActivity.currUser.getRefreshToken();
                        }
                        MainActivity.db.collection("/users/" + id + "/wraps")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document
                                                    : task.getResult()) {
                                                HANDLE_JSON.addWrappedFromJSON(
                                                        MainActivity.currUser,
                                                        (String) document.getData()
                                                                .get("wrap_data"),
                                                        document.getId());
                                            }
                                        } else {
                                            System.out.println("failed");
                                        }
                                        MainActivity.currActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                System.out.println("MOVING");
                                                if (!MainActivity.comingFromRefresh) {
                                                    MainActivity.navController
                                                            .navigate(R.id.navigation_newWrapped);
                                                }
                                                TimeMachineFragment.updateUser();
                                                SettingsFragment.updateUser();
                                            }
                                        });
                                    }
                                });
                    } else {
                        //make new user
                        //needs to be done
                        System.out.println(MainActivity.userJSON);
                        try {
                            MainActivity.currUser = HANDLE_JSON.createUserFromJSON(
                                    MainActivity.userJSON.toString());
//                        Map<String, String> user = new HashMap<>();
////        usersWrapped.document(s.substring(0, s.indexOf(";" + SPLITTER))).set(user);
//                        CollectionReference usersWrapped = MainActivity.db.collection("users");
//                        usersWrapped.document(id).set(user);
                            exportData(MainActivity.currUser);
                            MainActivity.mAccessToken = MainActivity.currUser.getAccessToken();
                            MainActivity.currActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("MOVING");
                                    if (!MainActivity.comingFromRefresh) {
                                        MainActivity.navController
                                                .navigate(R.id.navigation_newWrapped);
                                    }
                                    TimeMachineFragment.updateUser();
                                    SettingsFragment.updateUser();
                                }
                            });
                        } catch (Exception e) {
//                            SpotifyCalls.getToken(MainActivity.currActivity);
                            SpotifyCalls.getCode(MainActivity.currActivity);
                            GetUserAsync.usedRefresh = true;
                        }
                    }
                } else {
                    System.out.println("unsuccessful");
                }
            }
        });
        SharedPreferences.Editor editor2 = LoginActivity.sharedPreferences.edit();
        editor2.putString("user", id);
        editor2.commit();
//        System.out.println(MainActivity.sharedPreferences.getString("user", ""));
//        CollectionReference usersWrapped = db.collection("users");
    }

    /**
     * Export data.
     *
     * @param user the user
     */
    public static void exportData(User user) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("user_data", HANDLE_JSON.exportUserBasic(user).toString());
        CollectionReference basicUser = MainActivity.db.collection("users");
        basicUser.document(user.getId()).set(userMap);

        //add wraps
        CollectionReference  userWrapped =
                MainActivity.db.collection("users/" + user.getId() + "/wraps");
        for (Wrapped w : user.getWraps()) {
            Map<String, String> wrapMap = new HashMap<>();
            wrapMap.put("wrap_data", HANDLE_JSON.exportWrapped(w).toString());
            userWrapped.document(w.getDate().toString()).set(wrapMap);
        }
    }

    /**
     * Update user.
     *
     * @param user the user
     */
    public static void updateUser(User user) {
//        Map<String, String> userMap = new HashMap<>();
//        userMap.put("user_data", HANDLE_JSON.exportUser(user).toString());
////        usersWrapped.document(s.substring(0, s.indexOf(";" + SPLITTER))).set(user);
//        CollectionReference usersWrapped = MainActivity.db.collection("users");
//        usersWrapped.document(user.getId()).set(userMap);
        updateUserInfo(user);

        //add wraps
        CollectionReference  userWrapped =
                MainActivity.db.collection("users/" + user.getId() + "/wraps");
        for (int i = 0; i < user.getWraps().size(); i++) {
            Wrapped w = user.getWraps().get(i);
            Map<String, String> wrapMap = new HashMap<>();
            wrapMap.put("wrap_data", HANDLE_JSON.exportWrapped(w).toString());
            userWrapped.document(w.getDate().toString()).set(wrapMap);
        }
    }

    /**
     * Updates user's info in Firebase.
     *
     * @param user user that is being updated
     */
    public static void updateUserInfo(User user) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("user_data", HANDLE_JSON.exportUserBasic(user).toString());
        CollectionReference basicUser = MainActivity.db.collection("users");
        basicUser.document(user.getId()).set(userMap);
    }
}

package com.example.spotifyapp2340.wrappers;

import androidx.annotation.NonNull;

import com.example.spotifyapp2340.MainActivity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

/**
 * The type User.
 */
public class User {
    private String id;
    private ArrayList<Wrapped> wraps;

    /**
     * Instantiates a new User.
     *
     * @param id    the id
     * @param wraps the wraps
     */
    public User(String id, ArrayList<Wrapped> wraps) {
        this.id = id;
        this.wraps = wraps;
    }

    /**
     * Instantiates a new User.
     *
     * @param id the id
     */
    public User(String id) {
        this.id = id;
        getUserData();
    }

    /**
     * Add wrapped.
     *
     * @param wrapped the wrapped
     */
    public void addWrapped(Wrapped wrapped) {
        wraps.add(wrapped);
    }

    /**
     * Gets user data.
     */
    public void getUserData() {
        DocumentReference docRef = MainActivity.db.collection("users").document(id);
        final String[] returnStr = new String[1];
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        returnStr[0] = (String) document.getData().get("prior_wrapped");
                    }
                }
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            wraps = objectMapper.readValue(returnStr[0], new TypeReference<ArrayList<Wrapped>>() {
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

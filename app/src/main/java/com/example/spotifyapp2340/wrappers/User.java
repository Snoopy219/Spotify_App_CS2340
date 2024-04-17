package com.example.spotifyapp2340.wrappers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spotifyapp2340.MainActivity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * The type User.
 */
public class User {
    private String id;
    private ArrayList<Wrapped> wraps = new ArrayList<>();
    private String display_name;

    private String email;
    private String accessToken;
    private String refreshToken;
    private String product;

    public User(String id, String display_name, String email, String accessToken, String product) {
        this.id = id;
        this.display_name = display_name;
        this.email = email;
        this.accessToken = accessToken;
        this.product = product;
    }

    public User() {
    }

    public void setWraps(ArrayList<Wrapped> wraps) {
        this.wraps = wraps;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    /**
     * The Fetch task.
     */
    public static Task<DocumentSnapshot> fetchTask;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    public boolean isPremium() {
        return !(product.equals("free")
                || product.equals("open"));
    }

    public String getProduct() {
        return product;
    }

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
     * Add wrapped.
     *
     * @param wrapped the wrapped
     */
    public void addWrapped(Wrapped wrapped) {
        wraps.add(wrapped);
    }

    /**
     * Gets wraps.
     *
     * @return the wraps
     */
    public ArrayList<Wrapped> getWraps() {
        return wraps;
    }

    /**
     * Gets format wraps.
     *
     * @return the format wraps
     */
    public String getFormatWraps() {
        String s = "[";
        for (Wrapped w : wraps) {
            s += "{" + w.toString() + "},";
        }
        s += "]";
        return s;
    }
}

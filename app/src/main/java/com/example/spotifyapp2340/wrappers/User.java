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

    /**
     * Instantiates a new User.
     *
     * @param id           the id
     * @param display_name the display name
     * @param email        the email
     * @param accessToken  the access token
     * @param product      the product
     * @param refreshToken the refresh token
     */
    public User(String id, String display_name, String email,
                String accessToken, String product, String refreshToken) {
        this.id = id;
        this.display_name = display_name;
        this.email = email;
        this.accessToken = accessToken;
        this.product = product;
        this.refreshToken = refreshToken;
    }

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Sets wraps.
     *
     * @param wraps the wraps
     */
    public void setWraps(ArrayList<Wrapped> wraps) {
        this.wraps = wraps;
    }

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets access token.
     *
     * @param accessToken the access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets display name.
     *
     * @return the display name
     */
    public String getDisplay_name() {
        return display_name;
    }

    /**
     * Sets display name.
     *
     * @param display_name the display name
     */
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

    /**
     * Is premium boolean.
     *
     * @return the boolean
     */
    public boolean isPremium() {
        return !(product.equals("free")
                || product.equals("open"));
    }

    /**
     * Gets product.
     *
     * @return the product
     */
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
        if (!wraps.contains(wrapped)) {
            wraps.add(wrapped);
        }
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

    /**
     * Returns refresh token.
     *
     * @return refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }
}

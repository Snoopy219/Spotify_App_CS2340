package com.example.spotifyapp2340.asyncTasks;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifyapp2340.LoginActivity;
import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.SpotifyCalls.SpotifyCalls;
import com.example.spotifyapp2340.handleJSON.HANDLE_JSON;
import com.example.spotifyapp2340.storage.FIRESTORE;
import com.example.spotifyapp2340.ui.newWrapped.NewWrappedFragment;
import com.example.spotifyapp2340.ui.settings.SettingsFragment;
import com.example.spotifyapp2340.ui.timeMachine.TimeMachineFragment;
import com.example.spotifyapp2340.ui.wrapped.WrappedFragment;
import com.example.spotifyapp2340.wrappers.Wrapped;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetTokenAndRefreshToken extends AsyncTask<Void, Void, Void>  {
    Call mCall;
    public static final OkHttpClient mOkHttpClient = new OkHttpClient();


    /**
     * Performs getting artists & tracks in the background.
     * @param params not needed. Void.
     *
     * @return null
     */
    @Override
    protected Void doInBackground(Void... params) {
        final FormBody body = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", MainActivity.mAccessCode)
                .add("redirect_uri", MainActivity.REDIRECT_URI)
                .build();

        final Request request = new Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .post(body)
                .addHeader("Authorization", "Basic NWZjNzAyYzcyZTVkNGM5NzljMDM2ODUwMzdh"
                        + "YjczN2Q6NWQwYjA4YTJiNzYwNDc4ODk1ODQyY2NlY2FmNzA2Nzk=")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        cancelCall();
        System.out.println("let");
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("here654");
                Log.d("HTTP", "Failed to fetch data: " + e);
                //Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details",
                //        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseStre = response.body().string();
                    System.out.println("This is the GetTokenAndRefreshToken response:\n" + responseStre);
                    if (responseStre.contains("401")) {
                        System.out.println("fail");
                    } else {
                        System.out.println(responseStre);
                        final JSONObject jsonObject = new JSONObject(responseStre);
                        MainActivity.mAccessToken = jsonObject.getString("access_token");
                        MainActivity.refreshToken = jsonObject.getString("refresh_token");

                        System.out.println("New mAccessToken: " + jsonObject.getString("access_token"));
                        System.out.println("New refreshToken: " + jsonObject.getString("refresh_token"));
//                        LoginActivity.onCallback();
                    }
                    //MainActivity.currUser = HANDLE_JSON.createUserFromJSON(MainActivity.userJSON.toString());

                    //check if user in database
                    //MainActivity.newUser(MainActivity.currUser);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    //Toast.makeText(MainActivity.this, "Failed to parse data, watch Logcat for more details",
                    // Toast.LENGTH_SHORT).show();
                }
            }
        });
        return null;
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    /**
     * Once all information has been obtained, transfers to next fragment.
     * @param result Void.
     */
    @Override
    protected void onPostExecute(Void result) {
        try {
            FIRESTORE.newUser(MainActivity.userJSON.getString("id"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

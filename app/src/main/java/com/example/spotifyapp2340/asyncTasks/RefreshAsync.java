package com.example.spotifyapp2340.asyncTasks;

import android.app.Activity;
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


public class RefreshAsync extends AsyncTask<Void, Void, Void>  {
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
        Log.d("RefreshAsync Test", "It's running doInBackGround");
        final FormBody formBody = new FormBody.Builder()
                .add("grant_type", "refresh_token")
                .add("refresh_token", MainActivity.refreshToken)
                .build();

        final Request request = new Request.Builder()
                .url("https://api.spotify.com/api/token")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Basic " + MainActivity.CLIENT_ID)
                .post(formBody)
                .build();

        cancelCall();
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
                    System.out.println(responseStre);
                    if (responseStre.contains("401")) {
                        System.out.println("fail");
                    } else {
                        System.out.println(responseStre);
                        final JSONObject jsonObject = new JSONObject(responseStre);
                        MainActivity.mAccessToken = jsonObject.getString("access_token");
                        MainActivity.refreshToken = jsonObject.getString("refresh_token");
                        LoginActivity.onCallback();
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

    /**
     * Cancels the current call on mCall.
     */
    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    /**
     * Does nothing.
     */
    @Override
    protected void onPostExecute(Void result) {
    }
}

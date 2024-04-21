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


/**
 * To refresh the token.
 */
public class RefreshAsync extends AsyncTask<Void, Void, Void>  {
    /**
     * The M call.
     */
    Call mCall;
    /**
     * The constant mOkHttpClient.
     */
    public static final OkHttpClient mOkHttpClient = new OkHttpClient();


    /**
     * Performs getting artists & tracks in the background.
     * @param params not needed. Void.
     *
     * @return null
     */
    @Override
    protected Void doInBackground(Void... params) {
//        Log.d("RefreshAsync Test", "It's running doInBackGround");
        final FormBody formBody = new FormBody.Builder()
                .add("grant_type", "refresh_token")
                .add("refresh_token", MainActivity.refreshToken)
                .build();

        final Request request = new Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Basic NWZjNzAyYzcyZTVkNGM5NzljMDM2ODU"
                        + "wMzdhYjczN2Q6NWQwYjA4YTJiNzYwNDc4ODk1ODQyY2NlY2FmNzA2Nzk=")
                .post(formBody)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("here654");
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseStre = response.body().string();
                    System.out.println("RefreshAsync response: " + responseStre);
                    if (responseStre.contains("401")) {
                        System.out.println("fail");
                    } else {
                        System.out.println(responseStre);
                        final JSONObject jsonObject = new JSONObject(responseStre);
                        MainActivity.mAccessToken = jsonObject.getString("access_token");
//                        new GetUserAsync().execute();
                        //If GetUserAsync triggers refresh async, does GetUserAsync again
                        if (GetUserAsync.usedRefresh) {
                            System.out.println("HERE AT USER");
                            new GetUserAsync().execute();
                            GetUserAsync.usedRefresh = false;
                        //If NewWrappedAsync triggers this async, does it again
                        } else if (RefreshProductAsync.usedRefresh) {
                            System.out.println("Refreshing Product");
                            new RefreshProductAsync().execute();
                            RefreshProductAsync.usedRefresh = false;
                        } else {
                            System.out.println("HERE AT TRIGGER");
                            new NewWrappedAsync(NewWrappedAsync.controller,
                                    NewWrappedAsync.activity).execute();
                            MainActivity.FAILED_CALL = false;
                        }
                        FIRESTORE.updateUserInfo(MainActivity.currUser);
                        LoginActivity.onCallback();
                    }

                    //check if user in database
                    //MainActivity.newUser(MainActivity.currUser);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
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
     * Nothing.
     */
    @Override
    protected void onPostExecute(Void result) {
    }
}
package com.example.spotifyapp2340.asyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetUserAsync extends AsyncTask<Void, Void, Void>  {
    Call mCall;
    public static final OkHttpClient mOkHttpClient = new OkHttpClient();
    public static boolean usedRefresh = false;


    /**
     * Performs getting artists & tracks in the background.
     * @param params not needed. Void.
     *
     * @return null
     */
    @Override
    protected Void doInBackground(Void... params) {
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + MainActivity.mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                //Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details",
                //        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseStre = response.body().string();
                    System.out.println("USER" + responseStre);
                    if (responseStre.contains("401")) {
                        System.out.println("Need to refresh.");
                        if (MainActivity.currUser != null && !usedRefresh) {
                            new RefreshAsync();
                            usedRefresh = true;
                        } else {
                            SpotifyCalls.getToken(MainActivity.currActivity);
                        }
                    } else {
                        System.out.println(responseStre);
                        usedRefresh = false;
                        final JSONObject jsonObject = new JSONObject(responseStre);
                        MainActivity.userJSON = jsonObject;
                    System.out.println("HERE " + MainActivity.userJSON);
                        FIRESTORE.newUser(jsonObject.getString("id"));
//                        new GetTokenAndRefreshToken().execute();
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
    }
}

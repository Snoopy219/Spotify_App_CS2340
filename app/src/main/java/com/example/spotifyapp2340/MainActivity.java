package com.example.spotifyapp2340;

import static com.example.spotifyapp2340.handleJSON.HANDLE_JSON.createUserFromJSON;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotifyapp2340.audioPlayer.AppPlayer;
import com.example.spotifyapp2340.handleJSON.HANDLE_JSON;
import com.example.spotifyapp2340.ui.wrapped.SongAdapter;
import com.example.spotifyapp2340.ui.wrapped.WrappedFragment;
import com.example.spotifyapp2340.wrappers.User;
import com.example.spotifyapp2340.wrappers.Wrapped;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.spotifyapp2340.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    /**
     * The constant db.
     */
    public static FirebaseFirestore db;

    public static JSONObject userJSON;
    public static User currUser;

    private static Button profileBtn;

    public static final String CLIENT_ID = "5fc702c72e5d4c979c03685037ab737d";
    public static final String REDIRECT_URI = "com.example.spotifyapp2340://auth";

    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    public static String mAccessToken;
    public static String mAccessCode;
    private static Call mCall;
    public static JSONObject tracks;
    public static JSONObject artists;

    public static SharedPreferences sharedPreferences;

    public static NavController navController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        play("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = LoginActivity.sharedPreferences;
        if (!sharedPreferences.getString("user", "").equals("")) {
            //get from document with shared prefs
            newUser(sharedPreferences.getString("user", ""));
            //MainActivity.currUser = HANDLE_JSON.createUserFromJSON(sharedPreferences.getString("user", ""));
        } else {
            //check if user exists in firestore or get new user
            onGetUserProfileClicked();
        }
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_timeMachine, R.id.navigation_newWrapped, R.id.navigation_settings)
                .build();
        navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        //setProfileBtn(findViewById(R.id.button))

        //Task<Void> getWrapped = Tasks.whenAll(User.fetchTask);
        //        getWrapped.addOnSuccessListener(new OnSuccessListener<Void>() {
        //            @Override
        //            public void onSuccess(Void unused) {
        //                for (Wrapped w : user.getWraps()) {
        //                    System.out.println("FINAL" + w);
        //                }
        //            }
        //        });
    }

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
                        MainActivity.mAccessToken = MainActivity.currUser.getAccessToken();
                    } else {
                        //make new user
                        MainActivity.currUser = HANDLE_JSON.createUserFromJSON(MainActivity.userJSON.toString());
                        Map<String, String> user = new HashMap<>();
                        user.put("user_data", HANDLE_JSON.exportUser(MainActivity.currUser).toString());
//        usersWrapped.document(s.substring(0, s.indexOf(";" + SPLITTER))).set(user);
                        CollectionReference usersWrapped = db.collection("users");
                        usersWrapped.document(id).set(user);
                        MainActivity.mAccessToken = MainActivity.currUser.getAccessToken();

                    }
                } else {
                    System.out.println("unsuccessful");
                }
            }
        });
        SharedPreferences.Editor editor = sharedPreferences.edit();
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
        CollectionReference usersWrapped = db.collection("users");
        usersWrapped.document(user.getId()).set(userMap);
    }

    public static void play(String sourceURL) {
        if (sourceURL == null) throw new IllegalArgumentException("SourceURL is null.");

        AppPlayer player = new AppPlayer(sourceURL, true);
    }

    /**
     * Get token from Spotify
     * This method will open the Spotify login activity and get the token
     * What is token?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public static void getToken(Activity context) {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(context, AUTH_TOKEN_REQUEST_CODE, request);
    }

    /**
     * Get code from Spotify
     * This method will open the Spotify login activity and get the code
     * What is code?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public static void getCode(Activity context) {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
        AuthorizationClient.openLoginActivity(context, AUTH_CODE_REQUEST_CODE, request);
    }


    /**
     * When the app leaves this activity to momentarily get a token/code, this function
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();
            //setTextAsync(mAccessToken, tokenTextView);

        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
            //setTextAsync(mAccessCode, codeTextView);
        }
    }

    /**
     * Creates & returns a new Wrapped object.
     *
     * @return new Wrapped object.
     */


    public void onNewWrapped(WrappedFragment fragment) {
        Wrapped wrapped = new Wrapped(Calendar.getInstance());
        MainActivity.currUser.addWrapped(wrapped);
        //Getting tracks
        final Request req = new Request.Builder().url("https://api.spotify.com/v1/me/top/tracks")
                .addHeader("Authorization",
                        "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(req);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String track = response.body().string();
                System.out.println(track);
                wrapped.setJSONTrack(track);
                fragment.notifyTrack();
                //setTextAsync(jsonObject.toString(3), profileTextView);
            }
        });

        //Getting artists
        final Request req2 = new Request.Builder().url("https://api.spotify.com/v1/me/top/tracks")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(req2);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String art = response.body().string();
                System.out.println(art);
                wrapped.setJSONArt(art);
                fragment.notifyArt();
                //setTextAsync(jsonObject.toString(3), profileTextView);

            }
        });

        //navigate to new wrap screen
        MainActivity.updateUser(MainActivity.currUser);
    }

    public void onGetUserProfileClicked() {

//         Create a request to get the user profile
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
                    System.out.println(responseStre);
                    final JSONObject jsonObject = new JSONObject(responseStre);
                    MainActivity.userJSON = jsonObject;
                    MainActivity.newUser(jsonObject.getString("id"));
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
    }

    /**
     * Creates a UI thread to update a TextView in the background
     * Reduces UI latency and makes the system perform more consistently
     *
     * @param text the text to set
     * @param textView TextView object to update
     */
//    private void setTextAsync(final String text, TextView textView) {
//        runOnUiThread(() -> textView.setText(text));
//    }

    /**
     * Get authentication request
     *
     * @param type the type of the request
     * @return the authentication request
     */
    private static AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[] { "user-read-email" }) // <--- Change the scope of your requested token here
                .setCampaign("your-campaign-token")
                .build();
    }

    /**
     * Gets the redirect Uri for Spotify
     *
     * @return redirect Uri object
     */
    private static Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

    private static void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        cancelCall();
        super.onDestroy();
    }
}

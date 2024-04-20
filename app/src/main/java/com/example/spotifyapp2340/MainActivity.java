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

import com.example.spotifyapp2340.SpotifyCalls.SpotifyCalls;
import com.example.spotifyapp2340.asyncTasks.GetTokenAndRefreshToken;
import com.example.spotifyapp2340.asyncTasks.GetUserAsync;
import com.example.spotifyapp2340.asyncTasks.NewWrappedAsync;
import com.example.spotifyapp2340.asyncTasks.RefreshAsync;
import com.example.spotifyapp2340.audioPlayer.AppPlayer;
import com.example.spotifyapp2340.handleJSON.HANDLE_JSON;
import com.example.spotifyapp2340.storage.FIRESTORE;
import com.example.spotifyapp2340.ui.newWrapped.NewWrappedFragment;
import com.example.spotifyapp2340.ui.settings.SettingsFragment;
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

    /**
     * The constant userJSON.
     */
    public static JSONObject userJSON;
    /**
     * The constant currUser.
     */
    public static User currUser;

    private static Button profileBtn;

    /**
     * The constant CLIENT_ID.
     */
    public static final String CLIENT_ID = "5fc702c72e5d4c979c03685037ab737d";
    /**
     * The constant REDIRECT_URI.
     */
    public static final String REDIRECT_URI = "com.example.spotifyapp2340://auth";

    /**
     * The constant AUTH_TOKEN_REQUEST_CODE.
     */
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    /**
     * The constant AUTH_CODE_REQUEST_CODE.
     */
    public static final int AUTH_CODE_REQUEST_CODE = 1;
    /**
     * The constant mOkHttpClient.
     */
    public static final OkHttpClient mOkHttpClient = new OkHttpClient();
    /**
     * The constant mAccessToken.
     */
    public static String mAccessToken;
    /**
     * The constant mAccessCode.
     */
    public static String mAccessCode;

    /**
     * The constant navController.
     */
    public static NavController navController;

    /**
     * The constant currActivity.
     */
    public static MainActivity currActivity;

    /**
     * The constant tokenTime.
     */
    public static long tokenTime;

    /**
     * The constant FAILED_CALL.
     */
    public static boolean FAILED_CALL = false;

    /**
     * The constant refreshToken.
     */
    public static String refreshToken;
    /**
     * The constant remindPremium.
     */
    public static boolean remindPremium = true;

    /**
     * The constant comingFromRefresh.
     */
    public static boolean comingFromRefresh = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setNavView(View.GONE);
        setContentView(binding.getRoot());
        currActivity = this;
        System.out.println(LoginActivity.sharedPreferences.getString("user", ""));
        if (!comingFromRefresh) {
            if (!LoginActivity.sharedPreferences.getString("user", "").equals("")) {
                //get from document with shared prefs
                System.out.println("WE ARE GOOOOOOODDDDDDDDDD");
                FIRESTORE.newUser(LoginActivity.sharedPreferences.getString("user", ""));
//            if (MainActivity.tokenTime >= 3600000) {
////                SpotifyCalls.getToken(MainActivity.currActivity);
//                new RefreshAsync();
//            }
            } else {
                //check if user exists in firestore or get new user
                new GetUserAsync().execute();
            }
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * When the app leaves this activity to momentarily get a token/code, this function
     * fetches the result of that external activity to get the response from Spotify.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();
            if (currUser == null) {
                new GetUserAsync().execute();
            }
            //setTextAsync(mAccessToken, tokenTextView);

        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
            new GetTokenAndRefreshToken().execute();
            //setTextAsync(mAccessCode, codeTextView);
        }
    }

    /**
     * On callback.
     */
    public static void onCallback() {
        new NewWrappedAsync(navController, currActivity).execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        if (navController.getCurrentDestination().getId() == R.id.navigation_waiting) {
            navController.navigate(R.id.navigation_newWrapped);
        }
        return true;
    }

    /**
     * Sets nav view.
     *
     * @param visibility the visibility
     */
    public void setNavView(int visibility) {
        if (binding != null) {
            binding.navView.setVisibility(visibility);
        }
    }

    /**
     * Sets back visible.
     *
     * @param visible the visible
     */
    public void setBackVisible(boolean visible) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
    }

    /**
     * After call.
     */
    public void afterCall() {
        super.onPostResume();
        if (navController.getCurrentDestination().getId() == R.id.navigation_waiting) {
            navController.navigate(R.id.action_navigation_waiting_to_wrap);
        } else {
            navController.navigate(R.id.action_navigation_newWrapped_to_wrap);
        }
    }
}

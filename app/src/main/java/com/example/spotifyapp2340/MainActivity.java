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
import com.example.spotifyapp2340.asyncTasks.GetUserAsync;
import com.example.spotifyapp2340.asyncTasks.NewWrappedAsync;
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

    public static JSONObject userJSON;
    public static User currUser;

    private static Button profileBtn;

    public static final String CLIENT_ID = "5fc702c72e5d4c979c03685037ab737d";
    public static final String REDIRECT_URI = "com.example.spotifyapp2340://auth";

    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;
    public static final OkHttpClient mOkHttpClient = new OkHttpClient();
    public static String mAccessToken;
    public static String mAccessCode;

    public static SharedPreferences sharedPreferences;

    public static NavController navController;

    public static MainActivity currActivity;

    public static long tokenTime;

    public static boolean FAILED_CALL = false;

    public static String refreshToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setNavView(View.GONE);
        setContentView(binding.getRoot());
        currActivity = this;
        sharedPreferences = LoginActivity.sharedPreferences;
        if (!sharedPreferences.getString("user", "").equals("")) {
            //get from document with shared prefs
            FIRESTORE.newUser(sharedPreferences.getString("user", ""));
            if (MainActivity.tokenTime >= 3600000) {
                SpotifyCalls.getToken(MainActivity.currActivity);
            }
        } else {
            //check if user exists in firestore or get new user
            new GetUserAsync().execute();
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
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();
            if (FAILED_CALL) {
                FAILED_CALL = false;
                onCallback();
            }
            //setTextAsync(mAccessToken, tokenTextView);

        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
            //setTextAsync(mAccessCode, codeTextView);
        }
    }

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
        return true;
    }

    public void setNavView(int visibility) {
        if (binding != null) {
            binding.navView.setVisibility(visibility);
        }
    }

    public void setBackVisible(boolean visible) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
    }
}

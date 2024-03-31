package com.example.spotifyapp2340;

import static com.example.spotifyapp2340.handleJSON.HANDLE_JSON.createUserFromJSON;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotifyapp2340.audioPlayer.AppPlayer;
import com.example.spotifyapp2340.handleJSON.HANDLE_JSON;
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

    public static User currUser;

    public static final String CLIENT_ID = "5fc702c72e5d4c979c03685037ab737d";
    public static final String REDIRECT_URI = "spotifyapp2340://auth";

    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    private static String mAccessToken, mAccessCode;
    private static Call mCall;

    private JSONObject userJSON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        play("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_timeMachine, R.id.navigation_newWrapped, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        User user = new User("User6", "Megan");
        newUser(user);
        updateUser(user);
        user.addWrapped(new Wrapped(Calendar.getInstance(),"{\n" +
                "  \"external_urls\": {\n" +
                "    \"spotify\": \"string\"\n" +
                "  },\n" +
                "  \"followers\": {\n" +
                "    \"href\": \"string\",\n" +
                "    \"total\": 0\n" +
                "  },\n" +
                "  \"genres\": [\"Prog rock\", \"Grunge\"],\n" +
                "  \"href\": \"string\",\n" +
                "  \"id\": \"string\",\n" +
                "  \"images\": [\n" +
                "    {\n" +
                "      \"url\": \"https://i.scdn.co/image/ab67616d00001e02ff9ca10b55ce82ae553c8228\",\n" +
                "      \"height\": 300,\n" +
                "      \"width\": 300\n" +
                "    }\n" +
                "  ],\n" +
                "  \"name\": \"string\",\n" +
                "  \"popularity\": 0,\n" +
                "  \"type\": \"artist\",\n" +
                "  \"uri\": \"string\"\n" +
                "}", "{\n" +
                "  \"album\": {\n" +
                "    \"album_type\": \"compilation\",\n" +
                "    \"total_tracks\": 9,\n" +
                "    \"available_markets\": [\"CA\", \"BR\", \"IT\"],\n" +
                "    \"external_urls\": {\n" +
                "      \"spotify\": \"string\"\n" +
                "    },\n" +
                "    \"href\": \"string\",\n" +
                "    \"id\": \"2up3OPMp9Tb4dAKM2erWXQ\",\n" +
                "    \"images\": [\n" +
                "      {\n" +
                "        \"url\": \"https://i.scdn.co/image/ab67616d00001e02ff9ca10b55ce82ae553c8228\",\n" +
                "        \"height\": 300,\n" +
                "        \"width\": 300\n" +
                "      }\n" +
                "    ],\n" +
                "    \"name\": \"string\",\n" +
                "    \"release_date\": \"1981-12\",\n" +
                "    \"release_date_precision\": \"year\",\n" +
                "    \"restrictions\": {\n" +
                "      \"reason\": \"market\"\n" +
                "    },\n" +
                "    \"type\": \"album\",\n" +
                "    \"uri\": \"spotify:album:2up3OPMp9Tb4dAKM2erWXQ\",\n" +
                "    \"artists\": [\n" +
                "      {\n" +
                "        \"external_urls\": {\n" +
                "          \"spotify\": \"string\"\n" +
                "        },\n" +
                "        \"href\": \"string\",\n" +
                "        \"id\": \"string\",\n" +
                "        \"name\": \"string\",\n" +
                "        \"type\": \"artist\",\n" +
                "        \"uri\": \"string\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"artists\": [\n" +
                "    {\n" +
                "      \"external_urls\": {\n" +
                "        \"spotify\": \"string\"\n" +
                "      },\n" +
                "      \"followers\": {\n" +
                "        \"href\": \"string\",\n" +
                "        \"total\": 0\n" +
                "      },\n" +
                "      \"genres\": [\"Prog rock\", \"Grunge\"],\n" +
                "      \"href\": \"string\",\n" +
                "      \"id\": \"string\",\n" +
                "      \"images\": [\n" +
                "        {\n" +
                "          \"url\": \"https://i.scdn.co/image/ab67616d00001e02ff9ca10b55ce82ae553c8228\",\n" +
                "          \"height\": 300,\n" +
                "          \"width\": 300\n" +
                "        }\n" +
                "      ],\n" +
                "      \"name\": \"string\",\n" +
                "      \"popularity\": 0,\n" +
                "      \"type\": \"artist\",\n" +
                "      \"uri\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"available_markets\": [\"string\"],\n" +
                "  \"disc_number\": 0,\n" +
                "  \"duration_ms\": 0,\n" +
                "  \"explicit\": false,\n" +
                "  \"external_ids\": {\n" +
                "    \"isrc\": \"string\",\n" +
                "    \"ean\": \"string\",\n" +
                "    \"upc\": \"string\"\n" +
                "  },\n" +
                "  \"external_urls\": {\n" +
                "    \"spotify\": \"string\"\n" +
                "  },\n" +
                "  \"href\": \"string\",\n" +
                "  \"id\": \"string\",\n" +
                "  \"is_playable\": false,\n" +
                "  \"linked_from\": {\n" +
                "  },\n" +
                "  \"restrictions\": {\n" +
                "    \"reason\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"string\",\n" +
                "  \"popularity\": 0,\n" +
                "  \"preview_url\": \"string\",\n" +
                "  \"track_number\": 0,\n" +
                "  \"type\": \"track\",\n" +
                "  \"uri\": \"string\",\n" +
                "  \"is_local\": false\n" +
                "}"));
        updateUser(user);

        Button profileBtn = (Button) findViewById(R.id.button);

        // Set the click listeners for the buttons
        profileBtn.setOnClickListener((v) -> {
            onGetUserProfileClicked();
            currUser = createUserFromJSON(userJSON.toString());
            newUser(currUser);
        });

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
     * @param u User to add
     */
    public static void newUser(User u) {
//        CollectionReference usersWrapped = db.collection("users");
        Map<String, String> user = new HashMap<>();
        user.put("user_data", "");
//        usersWrapped.document(s.substring(0, s.indexOf(";" + SPLITTER))).set(user);
        CollectionReference usersWrapped = db.collection("users");
        usersWrapped.document(u.getId()).set(user);
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
    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(MainActivity.this, AUTH_TOKEN_REQUEST_CODE, request);
    }

    /**
     * Get code from Spotify
     * This method will open the Spotify login activity and get the code
     * What is code?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getCode() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
        AuthorizationClient.openLoginActivity(MainActivity.this, AUTH_CODE_REQUEST_CODE, request);
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
     * Get user profile
     * This method will get the user profile using the token
     */
    public void onGetUserProfileClicked() {
        if (mAccessToken == null) {
            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    userJSON = jsonObject;
                    //setTextAsync(jsonObject.toString(3), profileTextView);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(MainActivity.this, "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
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
    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
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
    private Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

    private void cancelCall() {
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
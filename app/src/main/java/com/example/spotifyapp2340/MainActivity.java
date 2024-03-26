package com.example.spotifyapp2340;

import android.media.MediaPlayer;
import android.os.Bundle;

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

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    /**
     * The constant SPLITTER.
     */
    public static final String SPLITTER = "fdslakjflateuqoptretweroptu54289p495fjkdsa";
    /**
     * The constant db.
     */
    public static FirebaseFirestore db;

    public static User currUser;

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
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        User user = new User("User6", "Megan");
        newUser(user);
        updateUser(user);
        user.addWrapped(new Wrapped(Calendar.getInstance(), Calendar.getInstance(), "{\n" +
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
}
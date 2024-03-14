package com.example.spotifyapp2340;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.spotifyapp2340.databinding.ActivityMainBinding;
import com.example.spotifyapp2340.wrappers.User;
import com.example.spotifyapp2340.wrappers.Wrapped;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.spotifyapp2340.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String SPLITTER = "fdslakjflateuqoptretweroptu54289p495fjkdsa";
    /**
     * The constant db.
     */
    public static FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

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
        User user = new User("User4");

        Task<Void> getWrapped = Tasks.whenAll(User.fetchTask);
        getWrapped.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                for (Wrapped w : user.getWraps()) {
                    System.out.println("FINAL" + w);
                }
            }
        });
    }

    /**
     * Please pass in a formatted string in the following way.
     * "Name: [username]; (JSON OBJECTS OF SERIALIZED SPOTIFY WRAPPED)
     *
     * @param s formatted string
     */
    public static void newUser(String s) {
//        CollectionReference usersWrapped = db.collection("users");
//        Map<String, String> user = new HashMap<>();
//        user.put("prior_wrapped", s.substring(s.indexOf(";" + SPLITTER)));
//        usersWrapped.document(s.substring(0, s.indexOf(";" + SPLITTER))).set(user);
        CollectionReference usersWrapped = db.collection("users");
        usersWrapped.document(s);
    }

}
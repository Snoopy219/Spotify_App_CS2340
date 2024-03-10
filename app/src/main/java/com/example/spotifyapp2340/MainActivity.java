package com.example.spotifyapp2340;

import android.os.Bundle;
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    /**
     * Please pass in a formatted string in the following way:
     * "Name: [username]; [{}] (JSON OBJECTS OF SERIALIZED SPOTIFY WRAPPED)
     *
     * @param s formatted string
     */
    public static void newUser(String s) {
        CollectionReference usersWrapped = db.collection("users");
        Map<String, String> user = new HashMap<>();
        user.put("prior_wrapped", s.substring(s.indexOf("[")));
        usersWrapped.document(s.substring(0, s.indexOf(";[{"))).set(user);
    }


//    public static String getWrappedData(String user) {
//        DocumentReference docRef = db.collection("users").document(user);
//        final String[] returnStr = new String[1];
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        returnStr[0] = (String) document.getData().get("prior_wrapped");
//                    }
//                }
//            }
//        });
//        throw new RuntimeException("Failed to retrieve past wrapped data");
//    }

}
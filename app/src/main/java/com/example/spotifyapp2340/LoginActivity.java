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
import com.example.spotifyapp2340.databinding.ActivityMainBinding;
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

import com.example.spotifyapp2340.databinding.ActivityLoginBinding;
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
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private Call mCall;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    private Activity context;

    public static SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        if (MainActivity.currUser != null) {
//            Intent myIntent = new Intent(this, MainActivity.class);
//            startActivity(myIntent);
//        }

        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("user", "");
//        editor.commit();
        if (!sharedPreferences.getString("user", "").equals("")) {
            //get user with that name from firebase
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(myIntent);
        }
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call login
                System.out.println("here");
                //onGetUserProfileClicked();
                System.out.println("here");
                //if login successful
                getToken();
                System.out.println(MainActivity.mAccessToken);
//                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
//                startActivity(myIntent);
            }
        });
        context = this;
    }

    /**
     * Get token from Spotify
     * This method will open the Spotify login activity and get the token
     * What is token?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(LoginActivity.this, MainActivity.AUTH_TOKEN_REQUEST_CODE, request);
    }

    /**
     * Get code from Spotify
     * This method will open the Spotify login activity and get the code
     * What is code?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getCode() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
        AuthorizationClient.openLoginActivity(LoginActivity.this, MainActivity.AUTH_CODE_REQUEST_CODE, request);
    }


    /**
     * When the app leaves this activity to momentarily get a token/code, this function
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        Log.d("debug", "This is a test");

        // Check which request code is present (if any)
        if (MainActivity.AUTH_TOKEN_REQUEST_CODE == requestCode) {
            MainActivity.mAccessToken = response.getAccessToken();
            Intent myIntent = new Intent(context, MainActivity.class);
            startActivity(myIntent);
            //setTextAsync(mAccessToken, tokenTextView);

        } else if (MainActivity.AUTH_CODE_REQUEST_CODE == requestCode) {
            MainActivity.mAccessCode = response.getCode();
            //setTextAsync(mAccessCode, codeTextView);
        }
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
        return new AuthorizationRequest.Builder(MainActivity.CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[] { "user-read-email", "user-top-read" }) // <--- Change the scope of your requested token here
                .setCampaign("your-campaign-token")
                .build();
    }

    /**
     * Gets the redirect Uri for Spotify
     *
     * @return redirect Uri object
     */
    private Uri getRedirectUri() {
        return Uri.parse(MainActivity.REDIRECT_URI);
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

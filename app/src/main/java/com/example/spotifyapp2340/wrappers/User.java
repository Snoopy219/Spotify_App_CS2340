package com.example.spotifyapp2340.wrappers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.spotifyapp2340.MainActivity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * The type User.
 */
public class User {
    private String id;
    private ArrayList<Wrapped> wraps = new ArrayList<>();
    private String display_name;

    public User(String id, String display_name) {
        this.id = id;
        this.display_name = display_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    /**
     * The Fetch task.
     */
    public static Task<DocumentSnapshot> fetchTask;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Instantiates a new User.
     *
     * @param id    the id
     * @param wraps the wraps
     */
    public User(String id, ArrayList<Wrapped> wraps) {
        this.id = id;
        this.wraps = wraps;
    }

    /**
     * Add wrapped.
     *
     * @param wrapped the wrapped
     */
    public void addWrapped(Wrapped wrapped) {
        wraps.add(wrapped);
    }

    /**
     * Gets wraps.
     *
     * @return the wraps
     */
    public ArrayList<Wrapped> getWraps() {
        return wraps;
    }

    /**
     * Gets user data.
     */
    public void getUserData() {
        DocumentReference docRef = MainActivity.db.collection("users").document(id);
        final String[] returnStr = new String[1];
        final boolean[] completed = new boolean[1];
        completed[0] = false;
        fetchTask = docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    //AsyncTaskRunner runner = new AsyncTaskRunner();
                    if (document.exists()) {
                        returnStr[0] = (String) document.getData().get("prior_wrapped");
                        //System.out.println(returnStr[0]);
                        completed[0] = true;
                    }
                }
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        Task<Void> getWrapped = Tasks.whenAll(fetchTask);
        getWrapped.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                try {
//                    wraps = objectMapper.readValue(returnStr[0],
//                    new TypeReference<ArrayList<Wrapped>>() {
//                    });
                    //System.out.println("RETURN" + returnStr[0]);
                    String[] newWraps = returnStr[0].split(MainActivity.SPLITTER);
//                    for (int i = 1; i < newWraps.length; i++) {
//                        System.out.println(newWraps[i]);
//                    }
                    for (int i = 1; i < newWraps.length; i++) {
                        System.out.println("WRAP" + newWraps[i]);
                       // wraps.add(new Wrapped(newWraps[i]));
                    }
                    for (Wrapped w : wraps) {
                        System.out.println("WRAPPED" + w);
                    }
                } catch (Exception e) {
                    System.out.println("User" + e);
                }
            }
        });
        getWrapped.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("OH NO" + e);
            }
        });
    }

//    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
//
//        private String resp;
//        ProgressDialog progressDialog;
//
//        @Override
//        protected String doInBackground(String... params) {
//            publishProgress("Sleeping..."); // Calls onProgressUpdate()
//            try {
//                int time = Integer.parseInt(params[0])*1000;
//
//                Thread.sleep(time);
//                resp = "Slept for " + params[0] + " seconds";
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                resp = e.getMessage();
//            } catch (Exception e) {
//                e.printStackTrace();
//                resp = e.getMessage();
//            }
//            return resp;
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            // execution of result of Long time consuming operation
//            progressDialog.dismiss();
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(MainActivity.this,
//                    "ProgressDialog",
//                    "Wait for "+ " seconds");
//        }
//
//
//        @Override
//        protected void onProgressUpdate(String... text) {
//
//        }
//    }

    /**
     * Gets format wraps.
     *
     * @return the format wraps
     */
    public String getFormatWraps() {
        String s = "[";
        for (Wrapped w : wraps) {
            s += "{" + w.toString() + "},";
        }
        s += "]";
        return s;
    }
}

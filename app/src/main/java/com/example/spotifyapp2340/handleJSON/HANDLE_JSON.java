package com.example.spotifyapp2340.handleJSON;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.DATE;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.wrappers.ArtistObject;
import com.example.spotifyapp2340.wrappers.ImageObject;
import com.example.spotifyapp2340.wrappers.TrackObject;
import com.example.spotifyapp2340.wrappers.User;
import com.example.spotifyapp2340.wrappers.Wrapped;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HANDLE_JSON {
    public static User createUserFromJSON(String JSON) {
        User user;
        try {
            JSONObject jsonObject = new JSONObject(JSON);
            user = new User((String) jsonObject.get("id"), (String) jsonObject.get("display_name"), jsonObject.getString("email"), MainActivity.mAccessToken);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static User createUserFromFirestore(String JSON) {
        User user;
        try {
            JSONObject jsonObject = new JSONObject(JSON);
            user = new User((String) jsonObject.get("id"), (String) jsonObject.get("display_name"), jsonObject.getString("spotify_account"), jsonObject.getString("access_token"));
            JSONArray jsonArray = jsonObject.getJSONArray("wraps");
            ArrayList<Wrapped> wrappeds = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String JSONArt = jsonObject1.getString("artists");
                String JSONTrack = jsonObject1.getString("tracks");
                String JSONCalendar = jsonObject1.getString("date");
                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date = formatter.parse(JSONCalendar);
                wrappeds.add(createWrappedFromJSON(JSONArt, JSONTrack, date));
            }
        } catch (JSONException | ParseException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static void updateUserFromJSON(String JSON, User user) {
        try {
            JSONObject jsonObject = new JSONObject(JSON);
            user.setDisplay_name((String) jsonObject.get("display_name"));
            user.setId((String) jsonObject.get("id"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Wrapped createWrappedFromJSON(String JSONArtist, String JSONTrack, Date date) {
        Wrapped wrapped = new Wrapped(date, JSONArtist, JSONTrack);
        System.out.println(JSONArtist);
        System.out.println(JSONTrack);
        try {
            JSONObject jsonObjectArt = new JSONObject(JSONArtist);
            JSONArray jsonArrayArt = jsonObjectArt.getJSONArray("items");
            System.out.println("KKKKKKK");
            System.out.println(jsonArrayArt.length());
            for (int i = 0; i < jsonArrayArt.length(); i++) {
                System.out.println("adding art");
                JSONObject jsonObject1 = jsonArrayArt.getJSONObject(i);
                wrapped.getArtists().add(processArtistObject(jsonObject1));
            }
            JSONObject jsonObjectTrack = new JSONObject(JSONTrack);
            JSONArray jsonArrayTrack = (JSONArray) jsonObjectTrack.get("items");
            for (int i = 0; i < jsonArrayTrack.length(); i++) {
                JSONObject jsonObject1 = jsonArrayTrack.getJSONObject(i);
                wrapped.getTracks().add(processTrackObject(jsonObject1));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return wrapped;
    }

    public static ArtistObject processArtistObject(JSONObject jsonObject) {
        ArtistObject artistObject;
        try {
            System.out.println(jsonObject.toString());
            artistObject = new ArtistObject((String) jsonObject.get("name"), processImageArray(jsonObject.getJSONArray("images")));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return artistObject;
    }

    public static ImageObject[] processImageArray(JSONArray jsonArray) {
        if (jsonArray == null) {
            return new ImageObject[0];
        }
        ImageObject[] images = new ImageObject[jsonArray.length()];
        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = processImageObject(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return images;
    }

    public static TrackObject processTrackObject(JSONObject jsonObject) {
        TrackObject trackObject;
        try {
            trackObject = new TrackObject((String) jsonObject.get("name"), processImageArray(jsonObject.getJSONObject("album").getJSONArray("images")));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return trackObject;
    }

    public static ImageObject processImageObject(JSONObject jsonObject) {
        ImageObject imageObject = null;
        try {
            imageObject = new ImageObject(jsonObject.getString("url"), jsonObject.getInt("height"), jsonObject.getInt("width"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return imageObject;
    }

    public static void updateWrappedFromJSON(String JSONArt, String JSONTrack, Wrapped wrapped) {
        wrapped = createWrappedFromJSON(JSONArt, JSONTrack, wrapped.getDate());
    }

    public static JSONObject exportWrapped(Wrapped wrapped) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("date", wrapped.getDate().toString());
            String art = new JSONObject(wrapped.getJSONArt()).getJSONArray("items").toString();
            jsonObject.put("artists", wrapped.getJSONArt());
            String tracks = new JSONObject(wrapped.getJSONTrack()).getJSONArray("items").toString();
            jsonObject.put("tracks", wrapped.getJSONTrack());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public static JSONObject exportUser(User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", user.getId());
            jsonObject.put("display_name", user.getDisplay_name());
            jsonObject.put("spotify_account", user.getEmail());
            jsonObject.put("access_token", user.getAccessToken());
            JSONArray jsonArray = new JSONArray();
            ArrayList<Wrapped> wraps = user.getWraps();
            for (int i = 0; i < wraps.size(); i++) {
                jsonArray.put(i, exportWrapped(wraps.get(i)));
            }
            jsonObject.put("wraps", jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;

    }
}

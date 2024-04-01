package com.example.spotifyapp2340.handleJSON;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.DATE;

import com.example.spotifyapp2340.wrappers.ArtistObject;
import com.example.spotifyapp2340.wrappers.ImageObject;
import com.example.spotifyapp2340.wrappers.TrackObject;
import com.example.spotifyapp2340.wrappers.User;
import com.example.spotifyapp2340.wrappers.Wrapped;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HANDLE_JSON {
    public static User createUserFromJSON(String JSON) {
        User user;
        try {
            JSONObject jsonObject = new JSONObject(JSON);
            user = new User((String) jsonObject.get("id"), (String) jsonObject.get("display_name"), jsonObject.getString("email"));
        } catch (JSONException e) {
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

    public static Wrapped createWrappedFromJSON(String JSONArtist, String JSONTrack, Calendar date) {
        Wrapped wrapped = new Wrapped(date, JSONArtist, JSONTrack);
        try {
            JSONObject jsonObjectArt = new JSONObject(JSONArtist);
            JSONArray jsonArrayArt = new JSONArray(jsonObjectArt.get("items"));
            for (int i = 0; i < jsonArrayArt.length(); i++) {
                JSONObject jsonObject1 = jsonArrayArt.getJSONObject(i);
                wrapped.getArtists().add(processArtistObject(jsonObject1));
            }
            JSONObject jsonObjectTrack = new JSONObject(JSONTrack);
            JSONArray jsonArrayTrack = new JSONArray(jsonObjectTrack.get("items"));
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
            artistObject = new ArtistObject((String) jsonObject.get("name"), processImageArray(new JSONArray(jsonObject.get("images"))));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return artistObject;
    }

    public static ImageObject[] processImageArray(JSONArray jsonArray) {
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
            trackObject = new TrackObject((String) jsonObject.get("name"), processImageArray(new JSONArray(jsonObject.get("images"))));
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
            jsonObject.put("date", wrapped.getDate());
            jsonObject.put("artists", wrapped.getJSONArt());
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

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
    /**
     * Create a basic user from the JSON given by Spotify
     * @param JSON JSON from Spotify
     * @return Generated User
     */
    public static User createUserFromJSON(String JSON) {
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(JSON);
            user = new User((String) jsonObject.get("id"), (String) jsonObject.get("display_name"),
                    jsonObject.getString("email"), MainActivity.mAccessToken,
                    jsonObject.getString("product"), MainActivity.refreshToken);
        } catch (JSONException e) {
            System.out.println(e);
        }
        return user;
    }

    /**
     * Add wraps from the Firestore JSON
     * @param user
     * @param JSON
     * @param date
     */
    public static void addWrappedFromJSON(User user, String JSON, String date) {
        JSONArray jsonArrayArt;
        String artStr = null;
        JSONArray jsonArrayTrack;
        String trackStr = null;
        Date date1 = null;
        try {
            JSONObject jsonObject = new JSONObject(JSON);
            System.out.println(JSON);
            artStr = jsonObject.getString("artists");
//            jsonArrayArt = art.getJSONArray("items");
            trackStr = jsonObject.getString("tracks");
            JSONObject track = new JSONObject(trackStr);
            jsonArrayTrack = track.getJSONArray("items");
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.ENGLISH);
            date1 = formatter.parse(date);
        } catch (ParseException e) {
            System.out.println(e);
        } catch (JSONException e) {
            System.out.println(e);
        }
        if (artStr != null && trackStr != null && date1 != null) {
            user.addWrapped(createWrappedFromJSON(artStr, trackStr, date1));
        }
    }

    /**
     * Creates User from Firestore JSON
     * @param id User id
     * @param JSON JSON of user
     * @return Generated User
     */
    public static User createBasicUserFromJSON(String id, String JSON) {
        JSONObject jsonObject;
        String access_token = null;
        String display_name = null;
        String spotify_account = null;
        String product = null;
        String refresh_token = null;
        try {
            jsonObject = new JSONObject(JSON);
            access_token = jsonObject.getString("access_token");
            display_name = jsonObject.getString("display_name");
            spotify_account = jsonObject.getString("spotify_account");
            product = jsonObject.getString("product");
            refresh_token = jsonObject.getString("refresh_token");
            System.out.println("Refrehs token obtained from JSON is FUCK: " + refresh_token);
        } catch (JSONException e) {
            System.out.println(e);
        }
        User user = null;
        if (id != null && display_name != null && spotify_account != null && access_token != null && product != null) {
            user = new User(id, display_name, spotify_account,
                    access_token, product, refresh_token);
        }
        return user;

    }

    /**
     * Creates new Wrapped Object from JSON
     * @param JSONArtist JSON for artists
     * @param JSONTrack JSON for tracks
     * @param date Date for dates
     * @return GEnerated Wrapped
     */
    public static Wrapped createWrappedFromJSON(String JSONArtist, String JSONTrack, Date date) {
        Wrapped wrapped = new Wrapped(date, JSONArtist, JSONTrack);
//        System.out.println(JSONArtist);
//        System.out.println(JSONTrack);
        try {
            JSONObject jsonObjectArt = new JSONObject(JSONArtist);
            JSONArray jsonArrayArt = jsonObjectArt.getJSONArray("items");
//            System.out.println("KKKKKKK");
//            System.out.println(jsonArrayArt.length());
            for (int i = 0; i < jsonArrayArt.length(); i++) {
//                System.out.println("adding art");
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
            System.out.println(e);
        }
        return wrapped;
    }

    public static ArtistObject processArtistObject(JSONObject jsonObject) {
        ArtistObject artistObject = null;
        try {
//            System.out.println(jsonObject.toString());
            artistObject = new ArtistObject((String) jsonObject.get("name"), processImageArray(jsonObject.getJSONArray("images")), processGenres(jsonObject.getJSONArray("genres")));
        } catch (JSONException e) {
            System.out.println(e);
        }
        return artistObject;
    }

    public static String[] processGenres(JSONArray jsonArray) {
        String[] str = new String[jsonArray.length()];
        for (int i = 0; i < str.length; i++) {
            try {
                str[i] = jsonArray.getString(i);
            } catch (JSONException e) {
                System.out.println(e);
            }
        }
        return str;
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
                System.out.println(e);
            }
        }
        return images;
    }

    public static TrackObject processTrackObject(JSONObject jsonObject) {
        TrackObject trackObject = null;
        try {
            trackObject = new TrackObject((String) jsonObject.get("name"), processImageArray(jsonObject.getJSONObject("album").getJSONArray("images")), jsonObject.getString("preview_url"));
        } catch (JSONException e) {
            System.out.println(e);
        }
        return trackObject;
    }

    public static ImageObject processImageObject(JSONObject jsonObject) {
        ImageObject imageObject = null;
        try {
            imageObject = new ImageObject(jsonObject.getString("url"), jsonObject.getInt("height"), jsonObject.getInt("width"));
        } catch (JSONException e) {
            System.out.println(e);
        }
        return imageObject;
    }

    public static void updateWrappedFromJSON(String JSONArt, String JSONTrack, Wrapped wrapped) {
        wrapped = createWrappedFromJSON(JSONArt, JSONTrack, wrapped.getDate());
    }

    /**
     * Get JSONObject of Wrapped
     * @param wrapped Wrapped to export
     * @return JSONObject
     */
    public static JSONObject exportWrapped(Wrapped wrapped) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("date", wrapped.getDate().toString());
            String art = new JSONObject(wrapped.getJSONArt()).getJSONArray("items").toString();
            jsonObject.put("artists", wrapped.getJSONArt());
            String tracks = new JSONObject(wrapped.getJSONTrack()).getJSONArray("items").toString();
            jsonObject.put("tracks", wrapped.getJSONTrack());
        } catch (JSONException e) {
            System.out.println(e);
        }
        return jsonObject;
    }

    /**
     * GEts JSON representation of User
     * @param user User to get
     * @return JSONObject
     */
    public static JSONObject exportUserBasic(User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", user.getId());
            jsonObject.put("display_name", user.getDisplay_name());
            jsonObject.put("spotify_account", user.getEmail());
            jsonObject.put("access_token", MainActivity.mAccessToken);
            jsonObject.put("refresh_token", MainActivity.refreshToken);
            jsonObject.put("product", user.getProduct());
        } catch (JSONException e) {
            System.out.println(e);
        }
        return jsonObject;
    }
}

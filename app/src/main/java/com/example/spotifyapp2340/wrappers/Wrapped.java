package com.example.spotifyapp2340.wrappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * The type Wrapped.
 */
public class Wrapped {
    private ArrayList<ArtistObject> artists = new ArrayList<>();
    private ArrayList<TrackObject> tracks = new ArrayList<>();
    private Calendar date;
    private Calendar time;

    private String JSONArt;
    private String JSONTrack;

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getJSONArt() {
        return JSONArt;
    }

    public String getJSONTrack() {
        return JSONTrack;
    }

    public Wrapped(Calendar date, Calendar time, String JSONArt, String JSONTrack) {
        this.date = date;
        this.time = time;
        this.JSONArt = JSONArt;
        this.JSONTrack = JSONTrack;
    }

//    public Wrapped(String json) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ArrayList<ArrayList<WrappedItem>> items = new ArrayList<>();
//        ArrayList<OutsideWrap> outsideWraps = new ArrayList<>();
//        try {
//
//            outsideWraps = objectMapper.readValue(json,
//                    new TypeReference<ArrayList<OutsideWrap>>() {});
//            for (OutsideWrap o : outsideWraps) {
////                items.add(objectMapper.readValue(outsideWraps.get(0).getItems().toString(),
////                new TypeReference<ArrayList<WrappedItem>>() {
////                }));
////                for (ArrayList<WrappedItem> i : items) {
////                    for (WrappedItem j : i) {
////                        if (j.getType().equals("artist")) {
////                            artists.add(j);
////                        } else {
////                            tracks.add(j);
////                        }
////                    }
////                }
//                System.out.println(o.getItems());
//                for (WrappedItem w : o.getItems()) {
//                    System.out.println(w);
//                    System.out.println(w.getType());
//
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Items" + e);
//        }
//    }

    public Wrapped() {}

    public ArrayList<ArtistObject> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<ArtistObject> artists) {
        this.artists = artists;
    }

    public ArrayList<TrackObject> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<TrackObject> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        String s = "";

        return s;
    }
}

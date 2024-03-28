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

    private String JSONArt;
    private String JSONTrack;

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }


    public String getJSONArt() {
        return JSONArt;
    }

    public String getJSONTrack() {
        return JSONTrack;
    }

    public Wrapped(Calendar date, String JSONArt, String JSONTrack) {
        this.date = date;
        this.JSONArt = JSONArt;
        this.JSONTrack = JSONTrack;
    }

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

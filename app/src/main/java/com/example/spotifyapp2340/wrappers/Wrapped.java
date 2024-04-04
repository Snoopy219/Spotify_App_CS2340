package com.example.spotifyapp2340.wrappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The type Wrapped.
 */
public class Wrapped {
    private ArrayList<ArtistObject> artists = new ArrayList<>();
    private ArrayList<TrackObject> tracks = new ArrayList<>();
    private Date date;

    private String JSONArt;

    public void setJSONArt(String JSONArt) {
        this.JSONArt = JSONArt;
    }

    public void setJSONTrack(String JSONTrack) {
        this.JSONTrack = JSONTrack;
    }

    private String JSONTrack;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getJSONArt() {
        return JSONArt;
    }

    public String getJSONTrack() {
        return JSONTrack;
    }

    public Wrapped(Date date, String JSONArt, String JSONTrack) {
        this.date = date;
        this.JSONArt = JSONArt;
        this.JSONTrack = JSONTrack;
    }

    public Wrapped(Date date) {
        this.date = date;
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

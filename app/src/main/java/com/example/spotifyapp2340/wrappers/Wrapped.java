package com.example.spotifyapp2340.wrappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * The type Wrapped.
 */
public class Wrapped {
    private ArrayList<ArtistObject> artists = new ArrayList<>();
    private ArrayList<TrackObject> tracks = new ArrayList<>();
    private Date date;

    private String JSONArt;

    /**
     * Sets json art.
     *
     * @param JSONArt the json art
     */
    public void setJSONArt(String JSONArt) {
        this.JSONArt = JSONArt;
    }

    /**
     * Sets json track.
     *
     * @param JSONTrack the json track
     */
    public void setJSONTrack(String JSONTrack) {
        this.JSONTrack = JSONTrack;
    }

    private String JSONTrack;

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets json art.
     *
     * @return the json art
     */
    public String getJSONArt() {
        return JSONArt;
    }

    /**
     * Gets json track.
     *
     * @return the json track
     */
    public String getJSONTrack() {
        return JSONTrack;
    }

    /**
     * Instantiates a new Wrapped.
     *
     * @param date      the date
     * @param JSONArt   the json art
     * @param JSONTrack the json track
     */
    public Wrapped(Date date, String JSONArt, String JSONTrack) {
        this.date = date;
        this.JSONArt = JSONArt;
        this.JSONTrack = JSONTrack;
    }

    /**
     * Instantiates a new Wrapped.
     *
     * @param date the date
     */
    public Wrapped(Date date) {
        this.date = date;
    }

    /**
     * Gets artists.
     *
     * @return the artists
     */
    public ArrayList<ArtistObject> getArtists() {
        return artists;
    }

    /**
     * Sets artists.
     *
     * @param artists the artists
     */
    public void setArtists(ArrayList<ArtistObject> artists) {
        this.artists = artists;
    }

    /**
     * Gets tracks.
     *
     * @return the tracks
     */
    public ArrayList<TrackObject> getTracks() {
        return tracks;
    }

    /**
     * Sets tracks.
     *
     * @param tracks the tracks
     */
    public void setTracks(ArrayList<TrackObject> tracks) {
        this.tracks = tracks;
    }

    /**
     * Gets top genres.
     *
     * @return the top genres
     */
    public ArrayList<String> getTopGenres() {
        HashMap<String, Integer> freq = new HashMap<>();
        for (int i = 0; i < artists.size(); i++) {
            ArtistObject currArt = artists.get(i);
            String[] currGenres = currArt.getGenres();
            for (int j = 0; j < currGenres.length; j++) {
                Integer currCount = freq.getOrDefault(currGenres[j], 0);
                freq.put(currGenres[j], currCount + 1);
            }
        }
        LinkedList<String>[] buckets = new LinkedList[artists.size()];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
        for (Map.Entry<String, Integer> e : freq.entrySet()) {
            buckets[e.getValue()].add(e.getKey());
        }
        ArrayList<String> endGenre = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0; i--) {
            while (!buckets[i].isEmpty()) {
                endGenre.add(buckets[i].remove());
            }
        }
        return endGenre;
    }

    @Override
    public String toString() {
        String s = "";

        return s;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Wrapped o = (Wrapped) other;
        return o.getDate().equals(date);
    }
}

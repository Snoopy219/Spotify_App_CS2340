package com.example.spotifyapp2340.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Album.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {
    private String album_type;
    private int total_tracks;
    private String[] available_markets;
    private ImageObject[] images;
    private Artist[] artists;
}

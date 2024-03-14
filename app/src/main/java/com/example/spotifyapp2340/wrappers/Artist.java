package com.example.spotifyapp2340.wrappers;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Artist.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    private Follower[] followers;
    private String[] genres;
    private ImageObject[] images;
    private String name;
    private int popularity;
    private String type;
}

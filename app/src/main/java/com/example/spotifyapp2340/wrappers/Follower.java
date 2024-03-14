package com.example.spotifyapp2340.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Follower.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Follower {
    private int total;
}

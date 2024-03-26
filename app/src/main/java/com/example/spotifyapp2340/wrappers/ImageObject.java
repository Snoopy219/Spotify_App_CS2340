package com.example.spotifyapp2340.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Image object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageObject {
    private String url;
    private int height;
    private int width;
}

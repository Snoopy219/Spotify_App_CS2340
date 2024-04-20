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

    /**
     * Instantiates a new Image object.
     *
     * @param url    the url
     * @param height the height
     * @param width  the width
     */
    public ImageObject(String url, int height, int width) {
        this.url = url;
        this.height = height;
        this.width = width;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(int width) {
        this.width = width;
    }
}

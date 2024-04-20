package com.example.spotifyapp2340.wrappers;

/**
 * The type Track object.
 */
public class TrackObject {
    private String name;
    private ImageObject[] images;

    private String url;

    /**
     * Instantiates a new Track object.
     *
     * @param name   the name
     * @param images the images
     * @param url    the url
     */
    public TrackObject(String name, ImageObject[] images, String url) {
        this.name = name;
        this.images = images;
        this.url = url;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get images image object [ ].
     *
     * @return the image object [ ]
     */
    public ImageObject[] getImages() {
        return images;
    }

    /**
     * Sets images.
     *
     * @param images the images
     */
    public void setImages(ImageObject[] images) {
        this.images = images;
    }
}

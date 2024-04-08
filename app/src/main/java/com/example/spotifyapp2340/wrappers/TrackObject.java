package com.example.spotifyapp2340.wrappers;

public class TrackObject {
    private String name;
    private ImageObject[] images;

    private String url;

    public TrackObject(String name, ImageObject[] images, String url) {
        this.name = name;
        this.images = images;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageObject[] getImages() {
        return images;
    }

    public void setImages(ImageObject[] images) {
        this.images = images;
    }
}

package com.example.spotifyapp2340.wrappers;

public class TrackObject {
    private String name;
    private ImageObject[] images;

    public TrackObject(String name, ImageObject[] images) {
        this.name = name;
        this.images = images;
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

package com.example.spotifyapp2340.wrappers;

public class ArtistObject {
    private String name;
    private ImageObject[] images;
    private String[] genres;

    public ArtistObject(String name, ImageObject[] images, String[] generes) {
        this.name = name;
        this.images = images;
        this.genres = generes;
    }


    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
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

package com.example.spotifyapp2340.wrappers;

/**
 * The type Artist object.
 */
public class ArtistObject {
    private String name;
    private ImageObject[] images;
    private String[] genres;

    /**
     * Instantiates a new Artist object.
     *
     * @param name    the name
     * @param images  the images
     * @param generes the generes
     */
    public ArtistObject(String name, ImageObject[] images, String[] generes) {
        this.name = name;
        this.images = images;
        this.genres = generes;
    }


    /**
     * Get genres string [ ].
     *
     * @return the string [ ]
     */
    public String[] getGenres() {
        return genres;
    }

    /**
     * Sets genres.
     *
     * @param genres the genres
     */
    public void setGenres(String[] genres) {
        this.genres = genres;
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

package com.example.spotifyapp2340.wrappers;

/**
 * The type Wrapped item.
 */
public class WrappedItem {
    private Follower followers;
    private String[] genres;
    private ImageObject[] images;
    private String name;
    private int popularity;
    private String type;
    private Album album;
    private Artist[] artists;
    private boolean is_playable;
    private String preview_url;

    /**
     * Gets followers.
     *
     * @return the followers
     */
    public Follower getFollowers() {
        return followers;
    }

    /**
     * Sets followers.
     *
     * @param followers the followers
     */
    public void setFollowers(Follower followers) {
        this.followers = followers;
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
     * Get image url image object [ ].
     *
     * @return the image object [ ]
     */
    public ImageObject[] getImageURL() {
        return images;
    }

    /**
     * Sets image url.
     *
     * @param images the images
     */
    public void setImageURL(ImageObject[] images) {
        this.images = images;
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
     * Gets popularity.
     *
     * @return the popularity
     */
    public int getPopularity() {
        return popularity;
    }

    /**
     * Sets popularity.
     *
     * @param popularity the popularity
     */
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets album.
     *
     * @return the album
     */
    public Album getAlbum() {
        return album;
    }

    /**
     * Sets album.
     *
     * @param album the album
     */
    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * Get artists artist [ ].
     *
     * @return the artist [ ]
     */
    public Artist[] getArtists() {
        return artists;
    }

    /**
     * Sets artists.
     *
     * @param artists the artists
     */
    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

    /**
     * Is is playable boolean.
     *
     * @return the boolean
     */
    public boolean isIs_playable() {
        return is_playable;
    }

    /**
     * Sets is playable.
     *
     * @param is_playable the is playable
     */
    public void setIs_playable(boolean is_playable) {
        this.is_playable = is_playable;
    }

    /**
     * Gets preview url.
     *
     * @return the preview url
     */
    public String getPreview_url() {
        return preview_url;
    }

    /**
     * Sets preview url.
     *
     * @param preview_url the preview url
     */
    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }



}

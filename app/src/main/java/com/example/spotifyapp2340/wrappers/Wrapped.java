package com.example.spotifyapp2340.wrappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * The type Wrapped.
 */
public class Wrapped {
    private ArrayList<WrappedItem> artists;
    private ArrayList<WrappedItem> tracks;

    /**
     * Instantiates a new Wrapped.
     *
     * @param json the json
     */
    public Wrapped(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<WrappedItem> items = new ArrayList<>();
        try {
            items = objectMapper.readValue(json, new TypeReference<ArrayList<WrappedItem>>() {
            });
        } catch (Exception e) {
            System.out.println(e);
        }
        for (WrappedItem i : items) {
            if (i.getType().equals("artist")) {
                artists.add(i);
            } else {
                tracks.add(i);
            }
        }
    }
}

package com.example.spotifyapp2340.wrappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * The type Wrapped.
 */
public class Wrapped {
    private ArrayList<WrappedItem> artists = new ArrayList<>();
    private ArrayList<WrappedItem> tracks = new ArrayList<>();

    /**
     * Instantiates a new Wrapped.
     *
     * @param json the json
     */
    public Wrapped(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<ArrayList<WrappedItem>> items = new ArrayList<>();
        ArrayList<OutsideWrap> outsideWraps = new ArrayList<>();
        try {

            outsideWraps = objectMapper.readValue(json, new TypeReference<ArrayList<OutsideWrap>>() {
            });
            for (OutsideWrap o : outsideWraps) {
//                items.add(objectMapper.readValue(outsideWraps.get(0).getItems().toString(), new TypeReference<ArrayList<WrappedItem>>() {
//                }));
//                for (ArrayList<WrappedItem> i : items) {
//                    for (WrappedItem j : i) {
//                        if (j.getType().equals("artist")) {
//                            artists.add(j);
//                        } else {
//                            tracks.add(j);
//                        }
//                    }
//                }
                System.out.println(o.getItems());
                for (WrappedItem w : o.getItems()) {
                    System.out.println(w);
                    System.out.println(w.getType());
                    if (w.getType().equals("artist")) {
                        artists.add(w);
                    } else {
                        tracks.add(w);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Items" + e);
        }
    }

    public Wrapped() {}

    @Override
    public String toString() {
        String s = "";
        for (WrappedItem i : artists) {
            s += "\n" + i;
        }
        for (WrappedItem i : tracks) {
            s += "\n" + i;
        }
        return s;
    }
}

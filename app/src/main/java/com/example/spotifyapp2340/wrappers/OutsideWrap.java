package com.example.spotifyapp2340.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.JSONArray;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OutsideWrap {
    private List<WrappedItem> items;

    public List<WrappedItem> getItems() {
        return items;
    }

    public void setItems(List<WrappedItem> items) {
        this.items = items;
    }
}

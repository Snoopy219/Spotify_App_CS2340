package com.example.spotifyapp2340.ui.newWrapped;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The type Dashboard view model.
 */
public class NewWrappedViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Instantiates a new Dashboard view model.
     */
    public NewWrappedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Create New Wrapped");
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public LiveData<String> getText() {
        return mText;
    }
}
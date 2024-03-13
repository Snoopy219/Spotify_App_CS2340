package com.example.spotifyapp2340.ui.initial;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The type Initial screen view model.
 */
public class InitialScreenViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Instantiates a new Initial screen view model.
     */
    public InitialScreenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
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

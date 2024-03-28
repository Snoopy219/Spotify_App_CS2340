package com.example.spotifyapp2340.ui.timeMachine;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The type TimeMachine view model.
 */
public class TimeMachineViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Instantiates a new TimeMachine view model.
     */
    public TimeMachineViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Time Machine fragment");
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
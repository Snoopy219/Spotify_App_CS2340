package com.example.spotifyapp2340.ui.initial;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InitialScreenViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InitialScreenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

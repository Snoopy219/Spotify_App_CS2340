package com.example.spotifyapp2340.audioPlayer;

import android.media.AudioManager;
import android.media.DrmInitData;
import android.media.MediaPlayer;

import com.example.spotifyapp2340.ui.wrapped.WrappedFragment;

import java.io.IOException;

/**
 * Wrapper class for Android Studio's media player.
 *
 * @author Froilan Trix Sunga
 * @version 11.0.20
 */
public class AppPlayer {
    //URL for the mp3 file being played.
    static String sourceURL;
    //Media player.
    private static MediaPlayer player;

    /**
     * Constructor which instantiates a new MediaPlayer object.
     * Does not play until play() is called.
     */
    public AppPlayer() {
//        if (sourceURL == null) throw new IllegalArgumentException("sourceURL is null; Preview not available.");
        player = new MediaPlayer();
//        try {
//            player.setDataSource(sourceURL);
//            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            if (toPlay) {
//                player.prepare();
//                player.start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
    }

    /**
     * Plays mp3 from the source URL.
     *
     * @return if audio was succesfully played.
     */
    public boolean play(String sourceURL) {
        player = new MediaPlayer();
        try {
            player.setDataSource(sourceURL);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void stop() {
        if (player != null) {
            player.stop();
            System.out.println(player.isPlaying());
            player.release();
            player = null;
            System.out.println("stopped");
        }
    }

    /**
     * To be used when adding pause & play buttons.
     * Pauses the player.
     */
    public void pause() {
        if (player != null) {
            player.pause();
            WrappedFragment.isPaused = true;
        }
    }

    /**
     * To be used when adding pause & play buttons.
     * Unpauses the player.
     */
    public void unpause() {
        if (player != null) {
            player.start();
            WrappedFragment.isPaused = false;
        }
    }
}

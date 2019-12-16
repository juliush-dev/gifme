package com.pyhtag.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Setting {
    private BooleanProperty audio = new SimpleBooleanProperty();
    private BooleanProperty video = new SimpleBooleanProperty();
    private BooleanProperty thumbnail = new SimpleBooleanProperty();
    private IntegerProperty videoId = new SimpleIntegerProperty();

    /**
     * @return the isAudio
     */
    public boolean isAudio() {
        return audio.getValue();
    }

    /**
     * @param audio the audio to set
     */
    public void setAudio(boolean value) {
        this.audio.set(value);
    }

    public BooleanProperty audioProperty() {
        return audio;
    }

    /**
     * @return the isVideo
     */
    public boolean isVideo() {
        return video.getValue();
    }

    /**
     * @param video the video to set
     */
    public void setVideo(boolean value) {
        this.video.set(value);
    }

    public BooleanProperty videoProperty() {
        return video;
    }

    public int getVideoId() {
        return videoId.getValue();
    }

    public void setVideoId(int value) {
        videoId.set(value);
    }

    public IntegerProperty videoIdProperty() {
        return videoId;
    }

    /**
     * @return the isThumbnail
     */
    public boolean isThumbnail() {
        return thumbnail.getValue();
    }

    /**
     * @param audio the audio to set
     */
    public void setThumbnail(boolean value) {
        thumbnail.set(value);
    }

    public BooleanProperty thumbnailProperty() {
        return thumbnail;
    }

}

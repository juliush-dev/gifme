package com.pyhtag.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Setting {
    private BooleanProperty audio = new SimpleBooleanProperty();
    private BooleanProperty video = new SimpleBooleanProperty();
    private BooleanProperty thumbnail = new SimpleBooleanProperty();
    private StringProperty gifmeId = new SimpleStringProperty();
    private ObservableList<Format> availableVideoFormats = FXCollections.observableArrayList();

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

    public String getGifmeId() {
        return this.gifmeId.get();
    }

    public void setGifmeId(String gifmeId) {
        this.gifmeId.set(gifmeId);
    }

    public StringProperty gifmeIdProperty() {
        return this.gifmeId;
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

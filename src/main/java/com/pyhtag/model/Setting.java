package com.pyhtag.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Setting {
    private BooleanProperty audio = new SimpleBooleanProperty(false);
    private BooleanProperty video = new SimpleBooleanProperty(false);
    private BooleanProperty videoThumbnail = new SimpleBooleanProperty(false);
    private BooleanProperty audioThumbnail = new SimpleBooleanProperty();
    private StringProperty videoId = new SimpleStringProperty();
    private StringProperty audioFormat = new SimpleStringProperty();

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

    public String getVideoId() {
        return this.videoId.get();
    }

    public void setVideoId(String id) {
        this.videoId.set(id);
    }

    public StringProperty gifmeIdProperty() {
        return this.videoId;
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

    public boolean getVideoThumbnail() {
        return this.videoThumbnail.get();
    }

    public void setVideoThumbnail(boolean videoThumbnail) {
        this.videoThumbnail.set(videoThumbnail);
    }

    public BooleanProperty videoThumbnailProperty() {
        return this.videoThumbnail;
    }

    public boolean getAudioThumbnail() {
        return this.audioThumbnail.get();
    }

    public void setAudioThumbnail(boolean audioThumbnail) {
        this.audioThumbnail.set(audioThumbnail);
    }

    public BooleanProperty audioThumbnailProperty() {
        return this.audioThumbnail;
    }

    public String getAudioFormat() {
        return this.audioFormat.get();
    }

    public void setAudioFormat(String audioFormat) {
        this.audioFormat.set(audioFormat);
    }

    public StringProperty audioFormatProperty() {
        return this.audioFormat;
    }

	@Override
	public String toString() {
		return "Setting [audio=" + audio + ", \nvideo=" + video + ", \nvideoThumbnail=" + videoThumbnail
				+ ", \naudioThumbnail=" + audioThumbnail + ", \nvideoId=" + videoId + ", \naudioFormat=" + audioFormat + "]";
	}
    
    

}

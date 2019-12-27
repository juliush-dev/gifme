package com.pyhtag.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 * LinkSampleViewController
 */
public class LinkSampleViewController{

    @FXML
    private TitledPane url;
    @FXML
    private TabPane innerRoot;
    @FXML
    private CheckBox video;
    @FXML
    private CheckBox audio;
    @FXML
    private CheckBox videoThumbnail;
    @FXML
    private CheckBox audioThumbnail;
    @FXML
    private ComboBox<String> videoIdSelection;
    @FXML
    private Button knowMore;
    @FXML
    private ComboBox<String> audioFormatSelection;
    @FXML
    private AnchorPane videoSettingView;
    @FXML
    private AnchorPane audioSettingView;

    @FXML
    private void initialize() {

    }

    public TitledPane getUrl() {
        return this.url;
    }

    public void setUrl(TitledPane url) {
        this.url = url;
    }

    public TabPane getInnerRoot() {
        return this.innerRoot;
    }

    public void setInnerRoot(TabPane innerRoot) {
        this.innerRoot = innerRoot;
    }

    public CheckBox getVideo() {
        return this.video;
    }

    public void setVideo(CheckBox video) {
        this.video = video;
    }

    public CheckBox getAudio() {
        return this.audio;
    }

    public void setAudio(CheckBox audio) {
        this.audio = audio;
    }

    public CheckBox getVideoThumbnail() {
        return this.videoThumbnail;
    }

    public void setVideoThumbnail(CheckBox videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public CheckBox getAudioThumbnail() {
        return this.audioThumbnail;
    }

    public void setAudioThumbnail(CheckBox audioThumbnail) {
        this.audioThumbnail = audioThumbnail;
    }

    public ComboBox<String> getVideoIdSelection() {
        return this.videoIdSelection;
    }

    public void setVideoIdSelection(ComboBox<String> videoIdSelection) {
        this.videoIdSelection = videoIdSelection;
    }

    public Button getKnowMore() {
        return this.knowMore;
    }

    public void setKnowMore(Button knowMore) {
        this.knowMore = knowMore;
    }

    public ComboBox<String> getAudioFormatSelection() {
        return this.audioFormatSelection;
    }

    public void setAudioFormatSelection(ComboBox<String> audioFormatSelection) {
        this.audioFormatSelection = audioFormatSelection;
    }

    public AnchorPane getVideoSettingView() {
        return this.videoSettingView;
    }

    public void setSettingView(AnchorPane settingView) {
        this.videoSettingView = settingView;
    }

    public AnchorPane getAudioSettingView() {
        return this.audioSettingView;
    }

    public void setAudioSettingView(AnchorPane audioSettingView) {
        this.audioSettingView = audioSettingView;
    }

	public static LinkSampleViewController getInstance() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LinkSampleViewController.class.getResource("LinkSampleView.fxml"));
		TitledPane root = loader.load();
		return loader.getController();
	}

}
package com.pyhtag.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import com.pyhtag.model.Link;
import com.pyhtag.model.LinkAndViewList;
import com.pyhtag.util.BindingInitializator.LinkAndView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

/**
 * LinkSampleViewController
 */
public class LinkSampleViewController {

	/**
	 * root node
	 */
	@FXML
	private TitledPane titledPane;

	/**
	 * control fields;
	 */
	@FXML
	private TextField title;
	
	@FXML
	private Label badgeContent;
	@FXML
	private JFXButton edit;
	@FXML
	private JFXButton delete;
	/**
	 * video fields
	 */
	@FXML
	private JFXToggleButton video;
	@FXML
	private JFXToggleButton videoThumbnail;
	@FXML
	private JFXComboBox<String> videoComboSelection;

	/**
	 * audio fields
	 */
	@FXML
	private JFXToggleButton audio;
	@FXML
	private JFXToggleButton audioThumbnail;
	@FXML
	private JFXComboBox<String> audioComboSelection;
	
	private LinkAndView linkAndView = new LinkAndView(null, titledPane);

	@FXML
	private void initialize() {
		linkAndView.setViewController(this);
	}
	
	@FXML
	private void handleEdit() {
		
	}
	
	@FXML
	private void handleDelete() {
		LinkAndViewList.delete(this);
	}

	public TitledPane getTitledPane() {
		return titledPane;
	}

	public void setTitledPane(TitledPane titledPane) {
		this.titledPane = titledPane;
	}

	public TextField getTitle() {
		return title;
	}

	public void setTitle(TextField title) {
		this.title = title;
	}

	public Label getBadgeContent() {
		return badgeContent;
	}

	public void setBadgeContent(Label badgeContent) {
		this.badgeContent = badgeContent;
	}

	public JFXButton getEdit() {
		return edit;
	}

	public void setEdit(JFXButton edit) {
		this.edit = edit;
	}

	public JFXButton getDelete() {
		return delete;
	}

	public void setDelete(JFXButton delete) {
		this.delete = delete;
	}

	public JFXToggleButton getVideo() {
		return video;
	}

	public void setVideo(JFXToggleButton video) {
		this.video = video;
	}

	public JFXToggleButton getVideoThumbnail() {
		return videoThumbnail;
	}

	public void setVideoThumbnail(JFXToggleButton videothumbnail) {
		this.videoThumbnail = videothumbnail;
	}

	public JFXComboBox<String> getVideoComboSelection() {
		return videoComboSelection;
	}

	public void setVideoComboSelection(JFXComboBox<String> videoIdSelection) {
		this.videoComboSelection = videoIdSelection;
	}

	public JFXToggleButton getAudio() {
		return audio;
	}

	public void setAudio(JFXToggleButton audio) {
		this.audio = audio;
	}

	public JFXToggleButton getAudioThumbnail() {
		return audioThumbnail;
	}

	public void setAudioThumbnail(JFXToggleButton audiothumbnail) {
		this.audioThumbnail = audiothumbnail;
	}

	public JFXComboBox<String> getAudioComboSelection() {
		return audioComboSelection;
	}

	public void setAudioComboSelection(JFXComboBox<String> audioIdSelection) {
		this.audioComboSelection = audioIdSelection;
	}

	public void setLink(Link link) {
		linkAndView.setLink(link);
	}
	public LinkAndView link() {
		return linkAndView;
	}

}
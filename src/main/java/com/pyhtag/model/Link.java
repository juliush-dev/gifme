package com.pyhtag.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Link {
	private StringProperty url = new SimpleStringProperty();
	private StringProperty title = new SimpleStringProperty();
	private Setting setting = new Setting();
	private ObservableList<String> availableVideoFormats = FXCollections.observableArrayList();
	private BooleanBinding toGet = Bindings.or(setting.videoProperty(), setting.audioProperty());
	
	public Link(String url) {
		this.url.set(url);
	}

	public StringProperty urlProperty() {
		return url;
	}
	
	public String getUrl() {
		return url.get();
	}

	public void setUrl(String url) {
		this.url.set(url);
	}

	public StringProperty titleProperty() {
		return title;
	}
	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public ObservableList<String> getAvailableVideoFormats() {
		return availableVideoFormats;
	}


	public BooleanBinding toGetBinding() {
		return toGet;
	}
	
	public Boolean isToGet() {
		return toGet.get();
	}
	
	public ObservableList<String> getAvailableAudioFormats(){
		ObservableList<String> audioFormatList = FXCollections.observableArrayList();
		audioFormatList.addAll("best", "aac", "flac", "mp3", "m4a", "opus", "vorbis", "wav");
		return audioFormatList;
	}

	@Override
	public String toString() {
		return "Link [url=" + url + ", \ntitle=" + title + ", \nsetting=" + setting + ", \navailableVideoFormats="
				+ availableVideoFormats + ", \ntoGet=" + toGet + "]";
	}
	
	
	
}
package com.pyhtag.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Link {
    private StringProperty url = new SimpleStringProperty();
    private StringProperty title = new SimpleStringProperty();
    private Setting downloadSettings = new Setting();

    public Link(String url) {
        this.url.set(url);
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public StringProperty urlProperty() {
        return url;
    }

    public void setTitel(String title) {
        this.title.set(title);
    }

    public StringProperty titelProperty() {
        return title;
    }

    public String getUrl() {
        return url.get();
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty(){
        return title;
    }


    public Setting getDownloadSettings() {
        return this.downloadSettings;
    }

    public void setDownloadSettings(Setting downloadSettings) {
        this.downloadSettings = downloadSettings;
    }


    @Override
    public String toString() {
        return "\n{" +
            " url='" + getUrl() + "'\n" +
            " title='" + getTitle() + "'\n" +
            " downloadSettings='" + getDownloadSettings() + "'" +
            "}\n";
    }

    

}
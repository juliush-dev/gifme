package com.pyhtag.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Link {
    private StringProperty url = new SimpleStringProperty();
    private StringProperty titel = new SimpleStringProperty();
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

    public void setTitel(String titel) {
        this.titel.set(titel);
    }

    public StringProperty titelProperty() {
        return titel;
    }

    public StringProperty getUrl() {
        return url;
    }

    public StringProperty getTitel() {
        return titel;
    }

    public void setSetting(Setting downloadSettings) {
        this.downloadSettings = downloadSettings;
    }

    public Setting setSetting() {
        return downloadSettings;
    }

}
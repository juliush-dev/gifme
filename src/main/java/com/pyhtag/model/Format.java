package com.pyhtag.model;

import javafx.beans.property.StringProperty;

public class Format {

    private StringProperty ext;
    private StringProperty formatId;
    private StringProperty formatDescription;

    public Format(StringProperty ext, StringProperty formatId, StringProperty formatDescription) {
        this.ext = ext;
        this.formatId = formatId;
        this.formatDescription = formatDescription;
    }

    public Format() {

    }

    public final String getExt() {
        return ext.get();
    }

    public void setExt(String ext) {
        this.ext.set(ext);
    }

    public StringProperty extProperty() {
        return ext;
    }

    public final String getFormatId() {
        return formatId.get();
    }

    public void setFormatId(String formatId) {
        this.formatId.set(formatId);
    }

    public StringProperty formatIdProperty() {
        return formatId;
    }

    public final String getFormatDescription() {
        return formatDescription.get();
    }

    public void setFormatDescription(String formatDescription) {
        this.formatDescription.set(formatDescription);
    }

    public StringProperty formatDescriptionProperty() {
        return formatDescription;
    }

}

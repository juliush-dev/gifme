module com.pyhtag {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    opens com.pyhtag.model;
    opens com.pyhtag.util;
    opens com.pyhtag.view;
    opens com.pyhtag.util.service;
    opens com.pyhtag;
}
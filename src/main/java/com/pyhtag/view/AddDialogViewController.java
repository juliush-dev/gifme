package com.pyhtag.view;

import com.pyhtag.model.Link;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * AddDialogViewController
 */
public class AddDialogViewController {

    @FXML
    private TextArea textArea;
    @FXML
    private Button done;
    private ObservableList<Link> linksList = FXCollections.observableArrayList();
    private Stage dialoStage;
    private StringProperty links = new SimpleStringProperty();
    private BooleanProperty doneClicked = new SimpleBooleanProperty(false);
    private ListChangeListener.Change<Link> changeInLinksList;

    @FXML
    public void initialize() {

        linksList.addListener((ListChangeListener<Link>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    System.out.println("Was Added");
                }
            }
        });

    }

    @FXML
    private void handleDone() {
        addToLinksList();
        for (Link link : linksList) {
            System.out.println("in Add dialog " + link.getUrl().getValue());
        }
        doneClicked.set(true);
        dialoStage.close();
    }

    private boolean addToLinksList() {
        if (!textArea.getText().isEmpty()) {
            String[] formated = textArea.getText().split("[;]");
            for (String url : formated) {
                Link link = new Link(url);
                linksList.add(link);
            }
            return true;

        }
        return false;

    }

    public ObservableList<Link> getLinksList() {
        return linksList;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialoStage = dialogStage;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public Button getDone() {
        return done;
    }

    public void setLinks(String links) {
        this.links.set(links);
    }

    public boolean isDoneClicked() {
        return doneClicked.get();
    }

    public void setDoneCliecked(boolean value) {
        doneClicked.set(value);
    }

    public BooleanProperty doneClickedProperty() {
        return doneClicked;
    }

}
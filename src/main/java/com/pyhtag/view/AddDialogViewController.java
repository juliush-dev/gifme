package com.pyhtag.view;

import java.io.IOException;

import com.pyhtag.model.Link;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
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
    private LinksViewController linksViewController;

    @FXML
    public void initialize() {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("^(https:).+(;;)$")) {
                AddDialogViewController.LinkViewRelationBuilder relation = genrateNewLinkFrom(newValue);
                // https://www.youtube.com/watch?v=Rb-dTP37QVU&t=1871s
                if (linksViewController != null) {
                    linksViewController.getLinksGroup().getPanes().add(relation.getView());
                    System.out.println("genrateNewLinkFrom: link added in linksView");
                } else {
                    throw new NullPointerException();
                }
                Platform.runLater(() -> {
                    textArea.clear();
                });
                handleDone();
                initializeTheBinding(relation.getLink(), relation.getView(), relation.getViewController());
            }
        });

    }

    class LinkViewRelationBuilder {
        Link link;
        TitledPane view;
        LinkSampleViewController controller;

        LinkViewRelationBuilder(Link link, TitledPane view, LinkSampleViewController controller) {
            this.link = link;
            this.view = view;
            this.controller = controller;
        }

        Link getLink() {
            return link;
        }

        TitledPane getView() {
            return view;
        }

        void setLink(Link link) {
            this.link = link;
        }

        void setView(TitledPane view) {
            this.view = view;
        }

        LinkSampleViewController getViewController() {
            return controller;
        }

    }

    private AddDialogViewController.LinkViewRelationBuilder genrateNewLinkFrom(String value) {
        String url = value.substring(0, value.length() - 2);
        Link link = new Link(url);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LinkSampleView.fxml"));
        TitledPane t = new TitledPane();
        LinkSampleViewController controller = loader.getController();
        try {
            t = (TitledPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        t.setText(link.getUrl());
        if (linksViewController != null) {
            linksViewController.getLinksList().add(link);
            System.out.println("genrateNewLinkFrom: link added in linksList");
        } else {
            System.err.println("genrateNewLinkFrom: linksViewController points to null");
        }
        return this.new LinkViewRelationBuilder(link, t, loader.getController());
    }

    private void initializeTheBinding(Link link, TitledPane view, LinkSampleViewController controller)
            throws NullPointerException {
        // link.getTitel().addListener((observableValue, oldValue, newValue) -> {
        // view.setText(newValue);
        // });
        controller.getSettingView().setDisable(true);
        controller.getAudioSettingView().setDisable(true);
        view.textProperty().bind(link.urlProperty());
    }

    @FXML
    private void handleDone() {
        try {
            addToLinksList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        doneClicked.set(true);
        dialoStage.close();
    }

    private void addToLinksList() throws IOException {
        if (!textArea.getText().isEmpty() && textArea.getText().matches(".+[^;];")) {

            String[] formated = textArea.getText().split("[;]");
            for (String url : formated) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("LinkSampleView.fxml"));
                Link link = new Link(url);
                TitledPane t = loader.load();
                t.setText(link.getUrl());
                if (linksViewController != null) {
                    linksViewController.getLinksGroup().getPanes().add(t);
                    linksViewController.getLinksList().add(link);
                    // System.out.println("Sure i was here");
                } else {
                    System.err.println("linksViewController points to null");
                }
                initializeTheBinding(link, t, loader.getController());
            }
        }
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

    public void setControllerSource(LinksViewController linksViewController) {
        this.linksViewController = linksViewController;
    }

}
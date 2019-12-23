package com.pyhtag.view;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pyhtag.model.Format;
import com.pyhtag.model.Link;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

    }

    /**
     * Initializes a binding between a link and its representation in the ViewList
     * ui.
     * 
     * @param link       The link to bind the view to.
     * @param view       The ui representation of the Link.
     * @param controller The controller to get access to more Field of the view.
     * @throws NullPointerException
     */
    private void initializeTheBinding(Link link, TitledPane view, LinkSampleViewController controller)
            throws NullPointerException {
        // controller.getAudioSettingView().setDisable(true);
        controller.getVideo().setIndeterminate(false);
        controller.getAudio().setIndeterminate(false);
        view.textProperty().bind(link.rangProperty().concat(link.urlProperty()));
        controller.getVideoSettingView().setDisable(true);
        controller.getAudioSettingView().setDisable(true);
        controller.getVideo().selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                controller.getVideoThumbnail().setSelected(newValue);
                controller.getVideoSettingView().setDisable(!newValue);
            } else {
                controller.getVideoSettingView().setDisable(!newValue);
            }
        });
        controller.getAudio().selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                controller.getAudioThumbnail().setSelected(newValue);
                controller.getAudioSettingView().setDisable(!newValue);
            } else {
                controller.getAudioSettingView().setDisable(!newValue);
            }
        });
        link.getDownloadSettings().videoProperty().bind(controller.getVideo().selectedProperty());
        link.getDownloadSettings().audioProperty().bind(controller.getAudio().selectedProperty());
        /**
         * binds the boolean videoThumbnailProperty in the link to the boolean value
         * from the conjuction of the boolean selectedProperty in the view with its
         * boolean indeterminatePropery.
         */
        link.getDownloadSettings().videoThumbnailProperty().bind(controller.getVideoThumbnail().selectedProperty());
        /**
         * binds the boolean audioThumbnailProperty in the link to the boolean value
         * from the conjuction of the boolean selectedProperty in the view with its
         * boolean indeterminatePropery.
         */
        link.getDownloadSettings().audioThumbnailProperty().bind(controller.getAudioThumbnail().selectedProperty());
        /**
         * adds a listener on the ObservableList of video format in the link to keep the
         * combobox video id selection in the view automatically updated.
         */
        link.getDownloadSettings().getAvailableVideoFormats().addListener((ListChangeListener<Format>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    String item = null;
                    for (int i = c.getFrom(); i < c.getTo(); i++) {
                        item = "(" + i + ") "
                                + link.getDownloadSettings().getAvailableVideoFormats().get(i).getFormatDescription()
                                + " (" + link.getDownloadSettings().getAvailableVideoFormats().get(i).getExt() + ")";
                        controller.getVideoIdSelection().getItems().add(item);
                    }
                }
            }
        });

        StringProperty idValue = new SimpleStringProperty();
        /**
         * adds a listener on the selectedItemProperty from the video combox id
         * selectionModel of the view in order to set a new StringProperty that will
         * server as bindings dependency to the gifmeIdProperty in the link. the
         * gifmeIdProperty in the link specify which video format the user wants to get.
         */
        controller.getVideoIdSelection().getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    System.out.println("the new value is: " + newValue);
                    Pattern pattern = Pattern.compile("\\(\\d+\\)");
                    Matcher matcher = pattern.matcher(newValue);
                    if (matcher.find()) {
                        String matching = matcher.group();
                        System.out.println("Pattern found: " + matching);
                        idValue.set(matching.substring(1, matching.length() - 1));
                        System.out.println(link);
                    } else
                        System.out.println("Pattern not found");
                });
        /**
         * binds the gifmeIdProperty in the link to the new selected value in the
         * combobox video id selection in the view.
         */
        link.getDownloadSettings().gifmeIdProperty().bind(idValue);
        /**
         * makes a list all available audio format in order to populate the combobox
         * audio format selection in the view to give the choice to the user to choose
         * from.
         */
        String[] audioFormats = { "best", "aac", "flac", "mp3", "m4a", "opus", "vorbis", "wav" };
        controller.getAudioFormatSelection().getItems().addAll(audioFormats);
        /**
         * adds a listener on the selectedItemProperty of the audio format selection in
         * the view in order to keep the audioFormatProperty in the link uptodate with
         * every new choice made by the user.
         */
        controller.getAudioFormatSelection().getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    link.getDownloadSettings().audioFormatProperty().set(newValue);
                    System.out.println(link);
                });
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

    /**
     * Adds each new Link in the LinkList, in the ViewList and initializes a binding
     * between each Link in the LinkList and its pair in the ViewList.
     * 
     * @throws IOException
     */
    private void addToLinksList() throws IOException {
        if (!textArea.getText().isEmpty()) {
            String userInput = textArea.getText().trim();
            Pattern pattern = Pattern.compile("^(https://).+[^;];");
            Matcher matcher = pattern.matcher(userInput);
            if (matcher.find()) {
                String[] formated = userInput.split("[;]");
                for (String url : formated) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("LinkSampleView.fxml"));
                    Link link = new Link(url);
                    TitledPane t = loader.load();
                    initializeTheBinding(link, t, loader.getController());
                    // t.setText(link.getUrl());
                    if (linksViewController != null) {
                        linksViewController.getLinksGroup().getPanes().add(t);
                        linksViewController.getLinksList().add(link);
                        // System.out.println("Sure i was here");
                    } else {
                        System.err.println("linksViewController points to null");
                    }
                }
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
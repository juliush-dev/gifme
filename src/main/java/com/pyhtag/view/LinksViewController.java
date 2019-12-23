package com.pyhtag.view;

import java.io.IOException;

import com.pyhtag.model.Link;
import com.pyhtag.util.EmployeeGetMoreInformation;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * LinksViewController
 */
public class LinksViewController {

    @FXML
    private Accordion linksGroup;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button process;
    private AddDialogViewController addDialogViewController;
    private ObservableList<Link> linksList = FXCollections.observableArrayList();

    public Accordion getLinksGroup() {
        return linksGroup;
    }

    @FXML
    private void handleAddNewLinks() {
        String links = "";
        showAddDialogView(links);
    }

    public void initialize() {
        linksList.addListener((ListChangeListener<Link>) c -> {
            EmployeeGetMoreInformation getMoreInformation = new EmployeeGetMoreInformation();
            while (c.next()) {
                if (c.wasAdded()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        getMoreInformation.setLink(linksList.get(i));
                        getMoreInformation.setIndex(i);
                        Thread threadInfoFinder = new Thread(EmployeeGetMoreInformation.getGroup(), getMoreInformation,
                                getMoreInformation.getName() + "-" + i);
                        threadInfoFinder.setDaemon(true);
                        System.out.println(getMoreInformation.getName() + " Daemon? " + threadInfoFinder.isDaemon());
                        threadInfoFinder.start();
                    }
                }
            }
        });
    }

    public boolean showAddDialogView(String links) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddDialogView.fxml"));
        VBox page = new VBox();
        try {
            page = (VBox) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Links");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(add.getScene().getWindow());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        addDialogViewController = loader.getController();
        addDialogViewController.setDialogStage(dialogStage);
        addDialogViewController.setLinks(links);
        addDialogViewController.setControllerSource(this);
        dialogStage.showAndWait();
        return addDialogViewController.isDoneClicked();
    }

    public AddDialogViewController getAddDialogViewController() {
        return addDialogViewController;
    }

    @FXML
    private void handleDelteLinks() {

    }

    @FXML
    private void handleProcessLinks() {

    }

    public ObservableList<Link> getLinksList() {
        return linksList;
    }

}
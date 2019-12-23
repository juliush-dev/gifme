package com.pyhtag.view;

import java.io.IOException;

import com.pyhtag.model.Link;

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

    /**
     * EmployeeThread1 extends {@link Thread}
     */
    class EmployeeThread1 extends Thread {
        private ObservableList<Link> list;
        private int index;

        public EmployeeThread1(ObservableList<Link> list, int index) {
            this.list = list;
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void run() {
            System.out.println(index + " " + list.get(index).getUrl());
            EmployeeGetMoreInformationThread e = new EmployeeGetMoreInformationThread(list.get(index), index);
            e.start();
        }
    }

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
            EmployeeThread1 t = new EmployeeThread1(linksList, 0);
            while (c.next()) {
                if (c.wasAdded()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        t.setIndex(i);
                        t.start();
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
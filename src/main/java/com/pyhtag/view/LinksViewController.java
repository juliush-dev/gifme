package com.pyhtag.view;

import java.io.IOException;

import com.pyhtag.model.Link;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
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


    public Accordion getLinksGroup() {
        return linksGroup;
    }

    @FXML
    private void handleAddNewLinks() {
        String links = "";
        boolean doneClicked = showAddDialogView(links);
        if(doneClicked){
            AddDialogViewController controller = getAddDialogViewController();
            for (Link l : controller.getLinksList()) {
                // System.out.println("in link view controller " + l.getUrl().getValue());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("LinkSampleView.fxml"));
                TitledPane t = new TitledPane();
                try {
                    t = (TitledPane) loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                t.setText(l.getUrl().getValue());
                linksGroup.getPanes().add(t);
            }
        }
        
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
        dialogStage.showAndWait();
        return addDialogViewController.isDoneClicked();
    }

    public AddDialogViewController getAddDialogViewController(){
        return addDialogViewController;
    }

    @FXML
    private void handleDelteLinks() {
        
        
    }

    @FXML
    private void handleProcessLinks() {
        
        
    }

}
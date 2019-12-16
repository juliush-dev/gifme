package com.pyhtag.view;

import com.pyhtag.App;
import com.pyhtag.model.Link;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
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
    private App app;


    public Accordion getLinksGroup() {
        return linksGroup;
    }

    @FXML
    private void handleAddNewLinks() {
        String links = "";
        boolean doneClicked = app.showAddDialogView(links);
        if(doneClicked){
            AddDialogViewController controller = app.getAddDialogViewController();
            for (Link l : controller.getLinksList()) {
                // System.out.println("in link view controller " + l.getUrl().getValue());
                TitledPane t = new TitledPane();
                t.setContent(linksGroup.getPanes().get(0).getContent());
                t.setAnimated(true);
                t.setText(l.getUrl().getValue());
                linksGroup.getPanes().add(t);
            }
        }
        
    }

    @FXML
    private void handleDelteLinks() {
        
        
    }

    @FXML
    private void handleProcessLinks() {
        
        
    }

    public void setApp(App app){
        this.app = app;
    }



}
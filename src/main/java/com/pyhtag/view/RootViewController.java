package com.pyhtag.view;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class RootViewController {
    @FXML
    private BorderPane root;
    @FXML
    private BorderPane linksView;
    @FXML
    private LinksViewController linksViewController;


    public BorderPane getRoot(){
        return root;
    }

    public BorderPane getLinksView() {
        return linksView;
    }

    public LinksViewController getLinksViewController() {
        return linksViewController;
    }




}
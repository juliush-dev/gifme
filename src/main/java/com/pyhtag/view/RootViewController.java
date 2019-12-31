package com.pyhtag.view;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class RootViewController {
	@FXML
	private BorderPane root;
	@FXML
	private BorderPane uiView;
	@FXML
	private LinkListViewController uiViewController;

	public BorderPane getRoot() {
		return root;
	}

	public BorderPane getLinkListView() {
		return uiView;
	}

	public LinkListViewController getuiViewController() {
		return uiViewController;
	}

//	public static RootViewController getInstance() throws IOException {
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(RootViewController.class.getResource("view/RrootView.fxml"));
//		TitledPane root = loader.load();
//		return loader.getController();
//	}

}
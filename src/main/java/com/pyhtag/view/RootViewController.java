package com.pyhtag.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

public class RootViewController {
	@FXML
	private BorderPane root;
	@FXML
	private BorderPane linkListView;
	@FXML
	private LinkListViewController linkListViewController;

	public BorderPane getRoot() {
		return root;
	}

	public BorderPane getLinkListView() {
		return linkListView;
	}

	public LinkListViewController getLinkListViewController() {
		return linkListViewController;
	}

	public static RootViewController getInstance() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(RootViewController.class.getResource("view/RrootView.fxml"));
		TitledPane root = loader.load();
		return loader.getController();
	}

}
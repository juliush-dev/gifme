package com.pyhtag.view;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.pyhtag.model.Link;
import com.pyhtag.model.LinkList;
import com.pyhtag.util.BindingInitializator;
import com.pyhtag.util.BindingInitializator.LinkAndView;
import com.pyhtag.util.Downloader;
import com.pyhtag.util.Filter;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * LinksViewController
 */
public class LinkListViewController {

	@FXML
	private BorderPane root;

	@FXML
	private Accordion linkListView;
	@FXML
	private Button add;
	@FXML
	private Button delete;
	@FXML
	private Button process;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Label progressLabel;
	private AddDialogViewController addDialogViewController;

	public Accordion getLinkListView() {
		return linkListView;
	}

	@FXML
	private void handleAddNewLinks() {
		showAddDialogView();
	}

	public void initialize() {
	}

	public void showAddDialogView() {
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
		AddDialogViewController dialogController = loader.getController();
		dialogController.getRoot().clear();
		dialogController.initializeBy(this);
		dialogStage.showAndWait();
	}

	public AddDialogViewController getAddDialogViewController() {
		return addDialogViewController;
	}

	@FXML
	private void handleDelteLinks() {

	}

	@FXML
	private void handleProcessLinks() {
		Filter.filter(LinkList.getLinkList(), linkListView.getPanes());
		Downloader downloader = new Downloader();
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				for (Link link : LinkList.getLinkList()) {
					downloader.download(link);
				}
				return null;
			}
		};
		progressBar.progressProperty().bind(downloader.progressProperty());
		Thread th = new Thread(t, "ferere");
		th.start();
		

	}

	public void addToLinkList(String[] urls) {
		BindingInitializator bindingInit = new BindingInitializator();
		for (CompletableFuture<LinkAndView> future : bindingInit.process(urls)) {
			addTask(future);
		}
	}

	private void addTask(CompletableFuture<LinkAndView> future) {
		LinkAndView linkAndView;
		try {
			linkAndView = future.get();
			TitledPane view = linkAndView.getPane();
			Link link = linkAndView.getLink();
			LinkList.addLink(link);
			int index = LinkList.getLinkList().indexOf(link);
			String t = "(" + index + 1 + ") " + view.getText();
			view.textProperty().unbind();
			view.setText(t);
			this.getLinkListView().getPanes().add(view);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	public BorderPane getRoot() {
		return root;
	}

	public void setRoot(BorderPane root) {
		this.root = root;
	}

	public Button getAdd() {
		return add;
	}

	public void setAdd(Button add) {
		this.add = add;
	}

	public Button getDelete() {
		return delete;
	}

	public void setDelete(Button delete) {
		this.delete = delete;
	}

	public Button getProcess() {
		return process;
	}

	public void setProcess(Button process) {
		this.process = process;
	}

	public void setLinkListView(Accordion linkListView) {
		this.linkListView = linkListView;
	}

	public void setAddDialogViewController(AddDialogViewController addDialogViewController) {
		this.addDialogViewController = addDialogViewController;
	}

}
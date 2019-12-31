package com.pyhtag.view;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.pyhtag.model.Link;
import com.pyhtag.model.LinkList;
import com.pyhtag.util.BindingInitializator.LinkAndView;
import com.pyhtag.util.Filter;
import com.pyhtag.util.service.AddLinkService;
import com.pyhtag.util.service.DownloadService;

import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
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
	private JFXButton add;
	@FXML
	private JFXButton delete;
	@FXML
	private JFXButton process;
	@FXML
	private JFXProgressBar progressBar;
	@FXML
	private Label progressLabel;
	@FXML
	private Label badge;

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
		for (Link link : LinkList.getLinkList()) {
			System.out.println("to download: " + link.getTitle());
		}
		System.out.println("*******************\n");
		if (!LinkList.getLinkList().isEmpty()) {
			for (Link link : LinkList.getLinkList()) {
				DownloadService downloadService = new DownloadService(link);
				progressBar.progressProperty().bind(downloadService.progressProperty());
				downloadService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@Override
					public void handle(WorkerStateEvent t) {
						System.out.println("done:" + t.getSource().getValue());
						int i = downloadService.getStatus() + 1;
						downloadService.setStatus(i);
						if (downloadService.getStatus() == LinkList.getLinkList().size()) {
							progressLabel.setText("Done");
						} else {
							progressLabel.setText(i + "%");
						}
					}
				});
				downloadService.start();
			}

		} else {
			progressBar.progressProperty().unbind();
			progressBar.setProgress(0);
			System.err.println("No Link to download");

		}
	}

	public void addToLinkList(String[] urls) {
		AddLinkService addLinkService = new AddLinkService(urls);
		addLinkService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
//				ObservableList<LinkAndView> linkAndViewList = addLinkService.getValue();
				for (TitledPane t : addLinkService.getValue()) {
					linkListView.getPanes().add(t);
				}
			}
		});
		addLinkService.start();
	}

	public BorderPane getRoot() {
		return root;
	}

	public void setRoot(BorderPane root) {
		this.root = root;
	}

	public JFXButton getAdd() {
		return add;
	}

	public void setAdd(JFXButton add) {
		this.add = add;
	}

	public JFXButton getDelete() {
		return delete;
	}

	public void setDelete(JFXButton delete) {
		this.delete = delete;
	}

	public JFXButton getProcess() {
		return process;
	}

	public void setProcess(JFXButton process) {
		this.process = process;
	}

	public void setLinkListView(Accordion linkListView) {
		this.linkListView = linkListView;
	}

	public void setAddDialogViewController(AddDialogViewController addDialogViewController) {
		this.addDialogViewController = addDialogViewController;
	}

}
package com.pyhtag.view;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.pyhtag.model.LinkAndViewList;
import com.pyhtag.util.BindingInitializator.LinkAndView;
import com.pyhtag.util.Filter;
import com.pyhtag.util.InvalidInput;
import com.pyhtag.util.ValidInputList;
import com.pyhtag.util.service.AddLinkService;
import com.pyhtag.util.service.DownloadService;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
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
	private Accordion uiView;
	@FXML
	private JFXButton add;
	@FXML
	private JFXButton delete;
	@FXML
	private JFXButton process;
	@FXML
	private ProgressIndicator progressBar;
	@FXML
	private Label message;
	@FXML
	private Label badge;
	@FXML
	private TextField deleteSelectionField;
	@FXML
	private AnchorPane dataRetrievingProgressView;
	@FXML
	private ProgressIndicator retrievingProgress;
	@FXML
	private Label requestStatus;

	private AddDialogViewController addDialogViewController;
	private BooleanProperty deleteToggle = new SimpleBooleanProperty(false);

	public Accordion getUiView() {
		return uiView;
	}

	@FXML
	private void handleAddNewLinks() {
		showAddDialogView();
	}

	public LinkListViewController() {
		LinkAndViewList.setUi(this);
	}

	public void initialize() {
		dataRetrievingProgressView.setVisible(false);
		deleteSelectionField.visibleProperty().bind(deleteToggle);
		message.setText("RAS");
		updateFrontAndBackEnd();
	}

	private void updateFrontAndBackEnd() {
		uiView.getPanes().addListener((ListChangeListener<TitledPane>) c -> {
			if (!uiView.getPanes().isEmpty()) {
				while (c.next()) {
					if (c.wasRemoved()) {
						for (int i = c.getFrom(); i < c.getTo(); i++) {
							if (c.getRemoved().get(i) == LinkAndViewList.get().get(i).getPane()) {
								LinkAndViewList.get().remove(i);
							}
						}
					}
				}
				for (int i = 0; i < uiView.getPanes().size(); i++) {
					if (uiView.getPanes().get(i) == LinkAndViewList.get().get(i).getPane()) {
						LinkAndViewList.get().get(i).getViewController().getBadgeContent().setText("" + (i + 1));
					}
				}
				badge.setText(String.valueOf(LinkAndViewList.get().size()));
			}
		});
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
	private void handleDeleteSelected() {
		deleteToggle.set(!deleteToggle.get());
		if (!deleteToggle.get()) {
			if (!(deleteSelectionField.getText().isEmpty() || LinkAndViewList.get().isEmpty())) {
				String text = deleteSelectionField.getText();
				deleteSelectionField.setText("");
				try {
					int[] range = ValidInputList.processSelectedRange(text);
					if (range[1] < LinkAndViewList.get().size()) {
						for (int i = range[0]; i <= range[1]; i++) {
							Filter.removeOne(range[0]);
						}
					}
				} catch (InvalidInput e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Deletion opened");
		}

	}

	public void addToLinkList(String[] urls) {
		AddLinkService addLinkService = new AddLinkService(urls);
		addLinkService.setOnRunning(event -> {
			retrievingProgress.progressProperty().bind(addLinkService.progressProperty());
			message.textProperty().bind(addLinkService.messageProperty());
			dataRetrievingProgressView.setVisible(true);
		});
		addLinkService.setOnSucceeded(event -> {
			for (LinkAndView linkAndView : LinkAndViewList.get()) {
				uiView.getPanes().add(linkAndView.getPane());
			}
			badge.setText(String.valueOf(LinkAndViewList.get().size()));
			dataRetrievingProgressView.setVisible(false);
		});
		addLinkService.setOnFailed(event -> {
			System.out.println("----- Failed");
			System.out.println("----- " + event.getSource());
			System.out.println("----- " + event.getSource().getMessage());
			event.getSource().getException().printStackTrace();
		});

		addLinkService.start();
	}

	@FXML
	private void handleProcessLinks() {
		Filter.filter();
		if (!LinkAndViewList.get().isEmpty()) {
			DownloadService.setSize(LinkAndViewList.get().size());
			for (LinkAndView linkAndView : LinkAndViewList.get()) {
				DownloadService downloadService = new DownloadService(linkAndView.getLink());
				downloadService.setOnRunning(event -> {
					message.textProperty().bind(downloadService.messageProperty());
					progressBar.progressProperty().bind(downloadService.progressProperty());
				});
				downloadService.start();
				downloadService.setOnSucceeded(event -> {
					uiView.getPanes().clear();
					LinkAndViewList.get().clear();
					badge.setText("0");
					message.textProperty().unbind();
					message.setText("Download done!");
				});
				
				downloadService.setOnFailed(event -> {
					event.getSource().getException().printStackTrace();
				});
			}
			


		} else {
			progressBar.progressProperty().unbind();
			progressBar.setProgress(0);
			message.textProperty().unbind();
			message.setText("No Link to download");
		}
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

	public void setUiView(Accordion linkListView) {
		this.uiView = linkListView;
	}

	public void setAddDialogViewController(AddDialogViewController addDialogViewController) {
		this.addDialogViewController = addDialogViewController;
	}

}
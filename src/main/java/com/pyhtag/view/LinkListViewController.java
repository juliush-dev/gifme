package com.pyhtag.view;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.pyhtag.model.LinkAndViewList;
import com.pyhtag.util.BindingInitializator.LinkAndView;
import com.pyhtag.util.Filter;
import com.pyhtag.util.InvalidInput;
import com.pyhtag.util.ValidInputList;
import com.pyhtag.util.service.AddLinkService;
import com.pyhtag.util.service.DownloadService;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	private Accordion uiView;
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
	@FXML
	private TextField deleteSelectionField;

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
		deleteSelectionField.visibleProperty().bind(deleteToggle);
		uiView.getPanes().addListener((ListChangeListener<TitledPane>) c -> {
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

//	@FXML
//	private void handleDelteLinks() {
//	}
	
	@FXML
	private void handleDeleteSelected() {
		deleteToggle.set(!deleteToggle.get());
		if (!deleteToggle.get()) {
			if(!(deleteSelectionField.getText().isEmpty() || LinkAndViewList.get().isEmpty())) {
				String text = deleteSelectionField.getText();
				deleteSelectionField.setText("");
				try {
					int[] range = ValidInputList.processSelectedRange(text);
					if(range[1] < LinkAndViewList.get().size()) {
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

	@FXML
	private void handleProcessLinks() {
		Filter.filter();
		for (LinkAndView linkAndView : LinkAndViewList.get()) {
			System.out.println("to download: " + linkAndView.getLink().getTitle());
		}
		System.out.println("*******************\n");
		if (!LinkAndViewList.get().isEmpty()) {
			for (LinkAndView linkAndView : LinkAndViewList.get()) {
				DownloadService downloadService = new DownloadService(linkAndView.getLink());
				progressBar.progressProperty().bind(downloadService.progressProperty());
				downloadService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@Override
					public void handle(WorkerStateEvent t) {
						System.out.println("done:" + t.getSource().getValue());
						int i = downloadService.getStatus() + 1;
						downloadService.setStatus(i);
						if (downloadService.getStatus() == LinkAndViewList.get().size()) {
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
				for (LinkAndView linkAndView : LinkAndViewList.get()) {
					System.out.println("Adding panes in the ui");
					uiView.getPanes().add(linkAndView.getPane());
				}
				System.out.println("Resume: ");
				System.out.println("LinkAndViewList Size: " + LinkAndViewList.get().size());
				System.out.println("UiView Size: " + uiView.getPanes().size());
				
			}
		});
		addLinkService.setOnRunning(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				System.out.println("+++++ Running");
			}
		});
		addLinkService.setOnCancelled(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				System.out.println("///// Cancelled");
				System.out.println("----- " + event.getSource());
				System.out.println("----- " + event.getSource().getMessage());
				event.getSource().getException().printStackTrace();
			}
		});
		addLinkService.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				System.out.println("----- Failed");
				System.out.println("----- " + event.getSource());
				System.out.println("----- " + event.getSource().getMessage());
				event.getSource().getException().printStackTrace();
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

	public void setUiView(Accordion linkListView) {
		this.uiView = linkListView;
	}

	public void setAddDialogViewController(AddDialogViewController addDialogViewController) {
		this.addDialogViewController = addDialogViewController;
	}

}
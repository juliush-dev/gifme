package com.pyhtag.view;

import com.jfoenix.controls.JFXButton;
import com.pyhtag.util.InvalidInput;
import com.pyhtag.util.ValidInputList;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * AddDialogViewController
 */
public class AddDialogViewController {

	@FXML
	private TextArea textArea;
	@FXML
	private JFXButton done;
	private LinkListViewController linkListViewController;

	public TextArea getRoot() {
		return textArea;
	}
	
	@FXML
	public void handleDone() {
		((Stage)textArea.getScene().getWindow()).close();
		try {
			LinkListViewController mainController = linkListViewController;
			String[] newLinks = ValidInputList.processInput(textArea.getText());
			mainController.addToLinkList(newLinks);
		} catch (InvalidInput e) {
			System.err.println(e.getMessage());
			e.getCause();
		}
	}

	@FXML
	public void initialize() {

	}


	public void initializeBy(LinkListViewController linkListViewController) {
		this.linkListViewController = linkListViewController;
	}
	

}
package com.pyhtag.util.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.pyhtag.model.Link;
import com.pyhtag.model.LinkList;
import com.pyhtag.util.BindingInitializator;
import com.pyhtag.util.BindingInitializator.LinkAndView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TitledPane;

public class AddLinkService extends Service<ObservableList<TitledPane>> {

	BindingInitializator b = new BindingInitializator();
	String[] urls;

	public AddLinkService(String[] urls) {
		this.urls = urls;
	}

	ObservableList<TitledPane> panes = FXCollections.observableArrayList();

	@Override
	protected Task<ObservableList<TitledPane>> createTask() {
		return new Task<ObservableList<TitledPane>>() {
			@Override
			protected ObservableList<TitledPane> call() throws Exception {
				for (CompletableFuture<LinkAndView> future : b.process(urls)) {
					getLinkFrom(future, panes);
				}
				return panes;
			}
		};
	}

	public ObservableList<TitledPane> getPanes() {
		return panes;
	}

	private void getLinkFrom(CompletableFuture<LinkAndView> future, ObservableList<TitledPane> panes) {
		LinkAndView linkAndView;
		try {
			linkAndView = future.get();
			TitledPane view = linkAndView.getPane();
			Link link = linkAndView.getLink();
			LinkList.addLink(link);
			int index = LinkList.getLinkList().indexOf(link);
			String t = "(" + (index + 1) + ") " + view.getText();
			view.textProperty().unbind();
			view.setText(t);
			panes.add(view);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
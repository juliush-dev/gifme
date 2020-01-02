package com.pyhtag.util.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.pyhtag.model.LinkAndViewList;
import com.pyhtag.util.BindingInitializator;
import com.pyhtag.util.BindingInitializator.LinkAndView;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TitledPane;

public class AddLinkService extends Service<Void> {

	BindingInitializator b = new BindingInitializator();
	String[] urls;
	int futureSize = 0;

	public AddLinkService(String[] urls) {
		this.urls = urls;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				List<CompletableFuture<LinkAndView>> futures = b.process(urls);
				int progress = 0;
				futureSize = futures.size();
				for (CompletableFuture<LinkAndView> future : futures) {
					if(progress < futureSize) {
						updateMessage("Retrieving data from youtube...");
					}
					getFrom(future);
				}
				updateMessage("All done!");
				return null;
			}
		};
	}

	private void getFrom(CompletableFuture<LinkAndView> future) {
		LinkAndView linkAndView;
		try {
			linkAndView = future.get();
			System.out.println("Added " + linkAndView);
			TitledPane view = linkAndView.getPane();
			linkAndView.getViewController().setLink(linkAndView.getLink());
			LinkAndViewList.add(linkAndView);
			int index = LinkAndViewList.get().indexOf(linkAndView);
			String t = view.getText();
			view.textProperty().unbind();
			linkAndView.getViewController().getBadgeContent().setText(String.valueOf(index + 1));
			view.setText(t);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	public int getFutureSize() {
		return futureSize;
	}
	
	
}
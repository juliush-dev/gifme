package com.pyhtag.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.pyhtag.model.Link;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.TitledPane;

public class BindingInitializator {

	public static class LinkAndView {
		private Link link;
		private TitledPane linkView;

		public LinkAndView(Link link, TitledPane linkView) {
			this.link = link;
			this.linkView = linkView;
		}

		public TitledPane getPane() {
			return linkView;
		}

		public Link getLink() {
			return link;
		}

	}

	public List<CompletableFuture<LinkAndView>> process(String[] urls) {
		ObservableSet<String> urlsList = FXCollections.observableSet(urls);
		List<CompletableFuture<LinkAndView>> list = new ArrayList<CompletableFuture<LinkAndView>>();
		urlsList.parallelStream().forEach(url -> {
			Link link = new Link(url);
			CompletableFuture<Link> moreInfo = CompletableFuture.supplyAsync(new LinkOnlineInformation(link));
			CompletableFuture<LinkAndView> linkAndView = moreInfo.thenApply(new LinkAndViewFactory());
			list.add(linkAndView);
		});
		return list;
	}

}

package com.pyhtag.util;

import com.pyhtag.model.Link;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TitledPane;

public class Filter {

	public static ObservableList<Link> filter(ObservableList<Link> linksList, ObservableList<TitledPane> panes) {
		ObservableList<Link> toRemove = FXCollections.observableArrayList();
		linksList.stream().filter(link -> !link.isToGet()).forEach(link -> {
			toRemove.add(link);
		});
		toRemove.parallelStream().forEach(link -> {
			int index = linksList.indexOf(link);
			linksList.remove(index);
			panes.remove(index);
		});
		toRemove.clear();
		return linksList;
	}
}

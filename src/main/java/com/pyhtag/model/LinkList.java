package com.pyhtag.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LinkList {
	
	private static ObservableList<Link> linkList = FXCollections.observableArrayList();
	public static void addLink(Link link) {
		linkList.add(link);
	}
	
	public static ObservableList<Link> getLinkList(){
		return linkList;
	}

}

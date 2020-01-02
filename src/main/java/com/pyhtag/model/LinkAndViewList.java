package com.pyhtag.model;

import com.pyhtag.util.BindingInitializator.LinkAndView;
import com.pyhtag.util.Filter;
import com.pyhtag.view.LinkListViewController;
import com.pyhtag.view.LinkSampleViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LinkAndViewList {

	private static LinkListViewController linkListViewController;
	private static ObservableList<LinkAndView> linkAndViewList = FXCollections.observableArrayList();

	public static void add(LinkAndView linkAndView) {
		linkAndViewList.add(linkAndView);
	}

	public static ObservableList<LinkAndView> get() {
		return linkAndViewList;
	}

	public static void delete(LinkSampleViewController linkSampleViewController) {
		int index = 0;
		for (LinkAndView linkAndView : linkAndViewList) {
			if(linkSampleViewController.getTitledPane() == linkAndView.getPane()) {
				index = linkAndViewList.indexOf(linkAndView);
				break;
			}
		}
		boolean removed = Filter.removeOne(index);
		System.out.println("removed " + linkSampleViewController.getTitle().getText() + " : " + removed);
	}

	public static void setUi(LinkListViewController controller) {
		linkListViewController = controller;
	}
	
	public static LinkListViewController getUi() {
		return linkListViewController;
	}
}

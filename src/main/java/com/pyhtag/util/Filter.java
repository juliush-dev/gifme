package com.pyhtag.util;

import com.pyhtag.model.LinkAndViewList;
import com.pyhtag.util.BindingInitializator.LinkAndView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;

public class Filter {

	private static Accordion view;

	public static ObservableList<LinkAndView> filter() {
		ObservableList<LinkAndView> list = LinkAndViewList.get();
		ObservableList<LinkAndView> toRemove = FXCollections.observableArrayList();
		/**
		 * get the Elements to remove from the view and from the LinkAndViewList
		 */
		if(!LinkAndViewList.get().isEmpty()) {
			view = (Accordion) LinkAndViewList.get().get(0).getViewController().getTitledPane().getParent();
			System.out.println("Same size: " + (LinkAndViewList.get().size() == view.getPanes().size()));
			list.stream().filter(linkAndView -> !linkAndView.getLink().isToGet()).forEach(linkAndView -> {
				System.out.println("to remove " + linkAndView);
				toRemove.add(linkAndView);
			});
		}
		/**
		 * remove these Elements from both the uiView and the LinkAndViewList
		 */
		if (!toRemove.isEmpty()) {
			toRemove.stream().forEach(linkAndView -> {
				int index = list.indexOf(linkAndView);
				view.getPanes().remove(index);
				list.remove(index);
			});
		}
		System.out.println("Same size: " + (LinkAndViewList.get().size() == view.getPanes().size()));
		toRemove.clear();
		return list;
	}
	public static boolean removeOne(int index) {
		Accordion a = (Accordion) LinkAndViewList.get().get(index).getViewController().getTitledPane().getParent();
		LinkAndViewList.get().remove(index);
		a.getPanes().remove(index);
		return true;
	}

//	public static void setView(Accordion uiView) {
//		view = uiView;
//	}
}

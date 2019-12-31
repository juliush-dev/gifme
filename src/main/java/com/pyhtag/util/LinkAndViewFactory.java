package com.pyhtag.util;

import java.io.IOException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pyhtag.model.Link;
import com.pyhtag.util.BindingInitializator.LinkAndView;
import com.pyhtag.view.LinkSampleViewController;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;

public class LinkAndViewFactory implements Function<Link, LinkAndView> {

	@Override
	public LinkAndView apply(Link link) {
		MapControllerView mapControllerView = createViewOf(link);
		LinkAndView linkView = new LinkAndView(link, mapControllerView.getView());
		linkView.setViewController(mapControllerView.getController());
		return linkView;
	}
	class MapControllerView{
		TitledPane view;
		LinkSampleViewController controller;
		public MapControllerView(TitledPane view, LinkSampleViewController controller) {
			this.view = view;
			this.controller = controller;
		}
		public TitledPane getView() {
			return view;
		}
		public LinkSampleViewController getController() {
			return controller;
		}
		
	}
	private MapControllerView createViewOf(Link link) {
		TitledPane viewSample = null;
		MapControllerView mapControllerView = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LinkSampleViewController.class.getResource("RlinkSampleView.fxml"));
			viewSample = loader.load();
			LinkSampleViewController viewSampleController = loader.getController();
			initializeBinding(link, viewSampleController);
			mapControllerView = new MapControllerView(viewSample, viewSampleController);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapControllerView;
	}
	
	private void initializeBinding(Link link, LinkSampleViewController controller) throws IOException {
		controller.getTitle().textProperty().bind(link.titleProperty());
		link.getSetting().audioThumbnailProperty().bind(controller.getAudioThumbnail().selectedProperty());
		link.getSetting().videoThumbnailProperty().bind(controller.getVideoThumbnail().selectedProperty());
		StringProperty videoFormat = new SimpleStringProperty();
		controller.getVideoComboSelection().setItems(link.getAvailableVideoFormats());
		controller.getAudioComboSelection().getItems().addAll(link.getAvailableAudioFormats());
		
		link.getSetting().videoProperty().bind(controller.getVideo().selectedProperty());
		link.getSetting().audioProperty().bind(controller.getAudio().selectedProperty());
		controller.getVideoComboSelection().getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldValue, newValue) -> {
					Pattern pattern = Pattern.compile("^\\d+");
					Matcher matcher = pattern.matcher(newValue);
					if (matcher.find()) {
						String group = matcher.group();
						link.getSetting().setVideoId(group);
					}
					pattern = Pattern.compile("\\(\\w+\\)$");
					matcher = pattern.matcher(newValue);
					if (matcher.find()) {
						StringProperty group = new SimpleStringProperty(matcher.group().trim());
						group.set(group.get().substring(1, group.get().length() - 1));
						videoFormat.bind(group);
						controller.getVideoThumbnail().disableProperty().bind((Bindings.notEqual("mp4", videoFormat)));
						if (controller.getVideoThumbnail().isSelected()) {
							controller.getVideoThumbnail().setSelected(false);
						}
					}
				});
		controller.getAudioComboSelection().getSelectionModel().selectedItemProperty()
				.addListener((observableValue, oldValue, newValue) -> {
					link.getSetting().setAudioFormat(newValue);
					if (!"mp3".equals(newValue)) {
						if (controller.getAudioThumbnail().isSelected()) {
							controller.getAudioThumbnail().setSelected(false);
						}
					}
				});
	}

}

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
		TitledPane linkView = createViewOf(link);
		return new LinkAndView(link, linkView);
	}

	private TitledPane createViewOf(Link link) {
		TitledPane linkView = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/LinkSampleView.fxml"));
			linkView = loader.load();
			initializeBinding(link, loader.getController());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linkView;
	}
	
	private void initializeBinding(Link link, LinkSampleViewController controller) throws IOException {
		controller.getUrl().textProperty().bind(link.titleProperty());
		controller.getVideo().setAllowIndeterminate(false);
		controller.getAudio().setAllowIndeterminate(false);
		controller.getVideoSettingView().setDisable(true);
		controller.getAudioSettingView().setDisable(true);
		controller.getVideoThumbnail().disableProperty().bind((controller.getVideoSettingView().disableProperty()));
		controller.getAudioThumbnail().disableProperty().bind((controller.getAudioSettingView().disableProperty()));
		link.getSetting().audioThumbnailProperty().bind(controller.getAudioThumbnail().selectedProperty());
		link.getSetting().videoThumbnailProperty().bind(controller.getVideoThumbnail().selectedProperty());
		controller.getVideoSettingView().disableProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue) {
				controller.getVideoThumbnail().setSelected(false);
			}
		});
		controller.getAudioSettingView().disableProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue) {
				controller.getAudioThumbnail().setSelected(false);
			}
		});
		StringProperty videoFormat = new SimpleStringProperty();
		controller.getVideo().selectedProperty().addListener((observableValue, oldValue, newValue) -> {
			controller.getVideoSettingView().setDisable(!newValue.booleanValue());

		});
		controller.getAudio().selectedProperty().addListener((observableValue, oldValue, newValue) -> {
			controller.getAudioSettingView().setDisable(!newValue.booleanValue());
		});
		controller.getVideoIdSelection().setItems(link.getAvailableVideoFormats());
		controller.getAudioFormatSelection().getItems().addAll(link.getAvailableAudioFormats());
		link.getSetting().videoProperty().bind(controller.getVideo().selectedProperty());
		link.getSetting().audioProperty().bind(controller.getAudio().selectedProperty());
		controller.getVideoIdSelection().getSelectionModel().selectedItemProperty()
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
		controller.getAudioFormatSelection().getSelectionModel().selectedItemProperty()
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

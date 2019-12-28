package com.pyhtag.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pyhtag.model.Link;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Downloader {

	private Path downloadTo = Paths.get(System.getProperty("user.home"), "ForYou");
	private static DoubleProperty progress = new SimpleDoubleProperty();
	public Downloader() {
		try {
			if (Files.notExists(downloadTo)) {
				Files.createDirectory(downloadTo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<String> initProcess() {
		List<String> linkListToProcessOn = new ArrayList<String>();
		linkListToProcessOn.add("youtube-dl");
		linkListToProcessOn.add("-o");
		linkListToProcessOn.add(downloadTo.toString() + "/%(title)s.%(ext)s");
		return linkListToProcessOn;
	}

	public void download(Link link) {
		progress.set(0);
		List<String> commandForVideo = gifmeVideo(link);
		List<String> commandForAudio = gifmeAudio(link);
		if (!commandForVideo.isEmpty()) {
			begin(commandForVideo);
		}
		if (!commandForAudio.isEmpty()) {
			begin(commandForAudio);
		}
	}

	private List<String> gifmeVideo(Link link) {
		List<String> command = initProcess();
		String videoFormat = link.getSetting().getVideoId();
		if (link.getSetting().isVideo()) {
			command.add("-f");
			if (Pattern.matches("\\d+", videoFormat)) {
				command.add(videoFormat);
				if (link.getSetting().getVideoThumbnail()) {
					command.add("--embed-thumbnail");
				}
				command.add(link.getUrl());
				begin(command);
			} else {
				System.err.println("No valid video format selected");
			}

		}
		if (videoFormat == null) {
			command.clear();
		}
		return command;

	}

	private List<String> gifmeAudio(Link link) {
		List<String> command = initProcess();
		String audioFormat = link.getSetting().getAudioFormat();
		if (link.getSetting().isAudio()) {
			command.add("-x");
			if (link.getAvailableAudioFormats().contains(audioFormat)) {
				command.add("--audio-format");
				command.add(audioFormat);
				System.out.println("Thumnail " + link.getSetting().getAudioThumbnail());
				if (link.getSetting().getAudioThumbnail()) {
					command.add("--embed-thumbnail");
				}
				System.out.println("the url of this link is " + link.getUrl());
				command.add(link.getUrl());
			} else {
				System.err.println("No valid Audio format selected");
			}
		}
		if (audioFormat == null) {
			command.clear();
		}
		return command;
	}

	private void begin(List<String> command) {
		ProcessBuilder pb = new ProcessBuilder(command);
		pb.redirectErrorStream(true);
		Process process = null;
		try {
			process = pb.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			Pattern pattern = Pattern.compile("(?<counter>\\d+\\.\\d+)(%)");

			String line;
			while ((line = br.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					String group = matcher.group("counter");
					double d = Double.parseDouble(group);
					progress.set(d/100);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public final DoubleProperty progressProperty() {
		return progress;
	}

	public final double getProgress() {
		return progress.get();
	}

	public final void setProgress(final double value) {
		progress.set(value);
	}

}

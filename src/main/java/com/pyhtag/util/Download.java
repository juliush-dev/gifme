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

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Download extends Service<Boolean> {

	class LinkProperty implements ReadOnlyProperty<Link> {

		private Link link;

		public LinkProperty(Link link) {
			this.link = link;
		}

		@Override
		public void addListener(ChangeListener<? super Link> listener) {
		}

		@Override
		public void removeListener(ChangeListener<? super Link> listener) {

		}

		@Override
		public Link getValue() {
			return link;
		}

		@Override
		public void addListener(InvalidationListener listener) {
		}

		@Override
		public void removeListener(InvalidationListener listener) {
		}

		@Override
		public Object getBean() {
			return null;
		}

		@Override
		public String getName() {
			return link.getUrl();
		}

	}

	private Path downloadPath = Paths.get(System.getProperty("user.home"), "ForYou");
	private static DoubleProperty progress = new SimpleDoubleProperty();
	private final LinkProperty link;
	private BooleanProperty alreadyDownloaded = new SimpleBooleanProperty(false);
	private static IntegerProperty status = new SimpleIntegerProperty();

	public Download(Link link) {
		try {
			if (Files.notExists(downloadPath)) {
				Files.createDirectory(downloadPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.link = new LinkProperty(link);
	}

	private List<String> prepareCommand() {
		List<String> linkListToProcessOn = new ArrayList<String>();
		linkListToProcessOn.add("youtube-dl");
		linkListToProcessOn.add("-o");
		linkListToProcessOn.add(downloadPath.toString() + "/%(title)s.%(ext)s");
		return linkListToProcessOn;
	}

	

	private List<String> videoCommand(Link link) {
		List<String> command = prepareCommand();
		String videoFormat = link.getSetting().getVideoId();
		if (link.getSetting().isVideo()) {
			command.add("-f");
			if (Pattern.matches("\\d+", videoFormat)) {
				command.add(videoFormat);
				if (link.getSetting().getVideoThumbnail()) {
					command.add("--embed-thumbnail");
				}
				command.add(link.getUrl());
			} else {
				System.err.println("No valid video format selected");
			}

		}
		if (videoFormat == null) {
			command.clear();
		}
		return command;

	}

	private List<String> audioCommand(Link link) {
		List<String> command = prepareCommand();
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

	

	public final void setProgress(final double value) {
		progress.set(value);
	}

	public final BooleanProperty alreadyDownloadedProperty() {
		return this.alreadyDownloaded;
	}

	public final boolean isAlreadyDownloaded() {
		return this.alreadyDownloadedProperty().get();
	}

	public final void setAlreadyDownloaded(final boolean alreadyDownloaded) {
		this.alreadyDownloadedProperty().set(alreadyDownloaded);
	}

	@Override
	protected Task<Boolean> createTask() {
		final Link _link = getLink();
		return new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				if (this.isCancelled()) {
					System.err.println(this.getMessage());
					return false;
				}
				return download(_link);
			}
			public boolean download(Link link) throws IOException {
				progress.set(0);
				List<String> commandForVideo = videoCommand(link);
				List<String> commandForAudio = audioCommand(link);
				boolean accomplisched = true;
				if (!commandForVideo.isEmpty()) {
					accomplisched = start(commandForVideo);
				}
				if (!commandForAudio.isEmpty()) {
					accomplisched = start(commandForAudio);
				}
				return accomplisched;
			}
			private boolean start(List<String> command) throws IOException {
				boolean accomplisched = false;
				ProcessBuilder pb = new ProcessBuilder(command);
				pb.redirectErrorStream(true);
				Process process = null;
				process = pb.start();
				try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
					Pattern pattern = Pattern.compile("(?<counter>\\d+\\.\\d+)(%)");
					String line;
					while ((line = br.readLine()) != null) {
						Matcher matcher = pattern.matcher(line);
						System.out.println(line);
						if (matcher.find()) {
							String group = matcher.group("counter");
							synchronized (this) {
								double newValue = Double.parseDouble(group) / 100;
								updateProgress(newValue, 1);
							}
						} else if (Pattern.matches("has already been downloaded", line)) {
							alreadyDownloaded.set(true);
						}
					}
					accomplisched = true;
				}
				return accomplisched;
			}
		};
	}

	public final LinkProperty linkProperty() {
		return this.link;
	}

	public final Link getLink() {
		return this.linkProperty().getValue();
	}

	public final IntegerProperty statusProperty() {
		return this.status;
	}
	

	public final int getStatus() {
		return this.statusProperty().get();
	}
	

	public final void setStatus(final int status) {
		this.statusProperty().set(status);
	}

}

package com.pyhtag.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyhtag.model.Format;
import com.pyhtag.model.Link;

public class LinkOnlineInformation implements Supplier<Link> {

	private Link link;
	private final String skipDownload = "--skip-download";
	private final String dumpJson = "--dump-json";
	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonNode root;
	private static ThreadGroup infoFinder = new ThreadGroup("moreInfo");

	public LinkOnlineInformation(Link link) {
		this.link = link;
	}

	public LinkOnlineInformation() {
	}

	@Override
	public Link get() {
		System.out.println("+++ Start +++");
		try {
			initField();
		} catch (IOException e) {
			e.printStackTrace();
		}
		link.setTitle(root.get("title").asText());
		JsonNode availableFormats = root.get("formats");
		for (JsonNode jsonNode : availableFormats) {
			addFormat(jsonNode);
		}
		return link;
	}

	private void addFormat(JsonNode jsonNode) {
		Format format = new Format();
		format.setExt(jsonNode.get("ext").asText());
		format.setFormatId(jsonNode.get("format_id").asText());
		format.setFormatDescription(jsonNode.get("format").asText());
		String text = format.getFormatDescription() + "(" + format.getExt() + ")";
		link.getAvailableVideoFormats().add(text);
		System.out.println(format);
	}

	private void initField() throws IOException {

		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		ProcessBuilder youtubeDl = new ProcessBuilder("youtube-dl", skipDownload, dumpJson, link.getUrl());
		youtubeDl.redirectErrorStream(true);
		// TODO: add a try catch bloc here surrounding youtubeDl.start
		Process youtubDlProcess = youtubeDl.start();
		InputStream jsonData = youtubDlProcess.getInputStream();
		// TODO: add a try catch bloc here surrounding objectMapper.readTree(jsonData)
		root = objectMapper.readTree(jsonData);
	}

	public static ThreadGroup getGroup() {
		return infoFinder;
	}

	public void setLink(Link link) {
		this.link = link;
	}

}

package com.pyhtag.view;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyhtag.model.Format;
import com.pyhtag.model.Link;

public class EmployeeGetMoreInformationThread extends Thread {
    private Link link;
    private int index;
    private final String skipDownload = "--skip-download";
    private final String dumpJson = "--dump-json";
    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode root;
    private static ThreadGroup infoFinder = new ThreadGroup("coor");

    public EmployeeGetMoreInformationThread(Link link, int index) {
        super(infoFinder, "infoFinder");
        this.link = link;
        this.index = index;
    }

    public void run() {
        System.out.println("+++ Start working on " + index + " : " + link.getUrl() + " +++");
        try {
            initField();
        } catch (IOException e) {
            e.printStackTrace();
        }
        link.setTitel(root.get("title").asText());
        JsonNode availableFormats = root.get("formats");
        for (JsonNode jsonNode : availableFormats) {
            addFormat(jsonNode);
        }
        System.out.println(link);
        System.out.println("--- Achieved work on " + index + " : " + link.getUrl() + " ---");
    }

    private void addFormat(JsonNode jsonNode) {
        Format format = new Format();
        format.setExt(jsonNode.get("ext").asText());
        format.setFormatId(jsonNode.get("format_id").asText());
        format.setFormatDescription(jsonNode.get("format").asText());
        link.getDownloadSettings().getAvailableVideoFormats().add(format);
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

    public ThreadGroup getInfoFinder() {
        return infoFinder;
    }
}

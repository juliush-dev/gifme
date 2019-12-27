package com.pyhtag.util;

import java.util.regex.Pattern;

public interface ValidInputList {

	static String[] processInput(String text) throws InvalidInput {
		text = text.trim();
		String[] urls = text.split(";");
		for (String url : urls) {
			if (!Pattern.matches("https://w{3}\\.youtube\\.com/.+", url)) {
				throw new InvalidInput(text + " is not a valid list of youtube link");
			}
		}
		return urls;
	}

}

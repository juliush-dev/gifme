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
	
	static int[] processSelectedRange(String text) throws InvalidInput {
		text = text.trim();
		int[] range = new int[2];
		try {
			String[] rangeString = text.split("-");
			System.out.println("Range to delete from " + rangeString[0] + " to " + rangeString[1]);
			range[0] = Integer.valueOf(rangeString[0])-1;
			range[1] = Integer.valueOf(rangeString[1])-1;
		} catch (Exception e) {
			throw new InvalidInput(e.getMessage());
		}
		return range;
	}

}

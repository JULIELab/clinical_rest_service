package de.julielab.restservice;
public class Main {
	static final String DEFAULT_CHARSET = "UTF-8";
	static final String HEADER_CHAR_SET = "Accept-Charset";

	public static void main(String[] args) {
		Server.startServer(Arguments.parseArguments(args));
	}
}

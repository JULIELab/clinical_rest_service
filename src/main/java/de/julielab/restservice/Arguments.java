package de.julielab.restservice;

import java.util.Map;

import org.docopt.Docopt;

public class Arguments {

	static final int DEFAULT_PORT = 4567;

	static final String DOC_OPT = "JCREST\n\n" + "Usage:\n"
			+ " JCREST start <configurationFile> [--port=<x>] \n"
			+ " JCREST --help\n\n" + "Options:\n"
			+ " --help     Show this screen.\n"
			+ " --port=<x>    Port to use [default: " + DEFAULT_PORT + "].\n";

	public static Arguments parseArguments(final String[] args) {
		final Docopt docOpt = new Docopt(DOC_OPT);
		final Map<String, Object> arguments = docOpt.parse(args);
		return new Arguments(Integer.parseInt((String) arguments.get("--port")),
				(String) arguments.get("<configurationFile>"));
	}

	final int port;

	final String configurationFile;

	public Arguments(final int port, final String configurationFile) {
		this.port = port;
		this.configurationFile = configurationFile;
	}

}
package de.julielab.restservice;
import java.util.Map;
import org.docopt.Docopt;

public class Arguments {

	static final int DEFAULT_PORT = 4567;
	public static final String DEFAULT_MODEL_FOLDER = "src/main/resources/models"; //for testing / IDE

	static final String DOC_OPT = "JCREST\n\n" + "Usage:\n"
			+ " JCREST start <model_folder> [--port=<x>] \n"
			+ " JCREST --help\n\n" + "Options:\n"
			+ " --help     Show this screen.\n"
			+ " --port=<x>    Port to use [default: " + DEFAULT_PORT + "].\n";

	final int port;
	final String modelFolder;

	public Arguments(int port, String modelFolder) {
		this.port = port;
		this.modelFolder = modelFolder;
	}

	Arguments() {
		this(DEFAULT_PORT, DEFAULT_MODEL_FOLDER);
	}

	public static Arguments parseArguments(String[] args) {
		Docopt docOpt = new Docopt(DOC_OPT);
		Map<String, Object> arguments = docOpt.parse(args);
		return new Arguments(Integer.parseInt((String) arguments.get("--port")), (String) arguments.get("<model_folder>"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Arguments))
			return false;
		Arguments other = (Arguments) obj;
		if (this.port != other.port)
			return false;
		if (!this.modelFolder.equals(other.modelFolder))
			return false;
		return true;
	}

}
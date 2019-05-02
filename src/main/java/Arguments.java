import java.util.Map;
import org.docopt.Docopt;

public class Arguments {

	static final int DEFAULT_PORT = 4567;
	static final int DEFAULT_TIMEOUT = 30000;
	static final int DEFAULT_MIN_THREADS = 2;
	static final int DEFAULT_MAX_THREADS = 8;

	static final String DOC_OPT = "JCREST\n\n" + "Usage:\n"
			+ " JCREST start [--port=<x>] [--timeout=<x>] [--minThreads=<x>] [--maxThreads=<x>]\n"
			+ " JCREST --help\n\n"
			+ "Options:\n" + " --help     Show this screen.\n"
			+ " --port=<x>    Port to use [default: " + DEFAULT_PORT + "].\n"
			+ " --timeout=<x>    Timeout in miliseconds [default: "
			+ DEFAULT_TIMEOUT + "].\n"
			+ " --minThreads=<x>    Minimum threads to use for webserver [default: "
			+ DEFAULT_MIN_THREADS + "].\n"
			+ " --maxThreads=<x>    Maximum threads to use for webserver [default: "
			+ DEFAULT_MAX_THREADS + "].\n";

	final int port;
	final int maxThreads;
	final int minThreads;
	final int timeOutMillis;

	public Arguments(int port, int maxThreads, int minThreads,
			int timeOutMillis) {
		this.port = port;
		this.maxThreads = maxThreads;
		this.minThreads = minThreads;
		this.timeOutMillis = timeOutMillis;
	}
	
	Arguments(){
		this(DEFAULT_PORT, DEFAULT_MAX_THREADS, DEFAULT_MIN_THREADS, DEFAULT_TIMEOUT);
	}

	public static Arguments parseArguments(String[] args) {
		Docopt docOpt = new Docopt(DOC_OPT);
		Map<String, Object> arguments = docOpt.parse(args);
		return new Arguments(Integer.parseInt((String) arguments.get("--port")),
				Integer.parseInt((String) arguments.get("--maxThreads")),
				Integer.parseInt((String) arguments.get("--minThreads")),
				Integer.parseInt((String) arguments.get("--timeout")));
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
		if (this.maxThreads != other.maxThreads)
			return false;
		if (this.minThreads != other.minThreads)
			return false;
		if (this.port != other.port)
			return false;
		if (this.timeOutMillis != other.timeOutMillis)
			return false;
		return true;
	}

}
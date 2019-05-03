import static spark.Spark.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.google.gson.Gson;

import annotation.UIMAWrapper;

public class Main {
	private static final String DEFAULT_CHARSET = "UTF-8";
	static final String HEADER_CHAR_SET = "Accept-Charset";

	public static void main(String[] args) {
		Main m = new Main();
		m.routing();
	}

	Main(Arguments a) {
		this(a.port, a.maxThreads, a.minThreads, a.timeOutMillis);
	}

	Main(int port, int maxThreads, int minThreads, int timeOutMillis) {
		port(port);
		threadPool(maxThreads, minThreads, timeOutMillis);
	}

	Main() {
		this(Arguments.DEFAULT_PORT, Arguments.DEFAULT_MAX_THREADS,
				Arguments.DEFAULT_MIN_THREADS, Arguments.DEFAULT_TIMEOUT);
	}

	void routing() {
		post("/entities", (req, res) -> {
			String charEncoding = req.headers(HEADER_CHAR_SET);
			try {
				String text = reEncode(req.body(), charEncoding,
						DEFAULT_CHARSET);
				return new Gson().toJson(new UIMAWrapper(10, 100).annotate(
						reEncode(text, DEFAULT_CHARSET, charEncoding)));
			} catch (UnsupportedEncodingException e) {
				res.status(406);
				res.body("Not Acceptable");
				return res;
			}
		});
		//TODO make sure UIMA pipeline is returned when no longer needed

		get("/supported-encodings",
				(req, res) -> Charset.availableCharsets().keySet());
	}

	static String reEncode(String text, String fromEncoding, String toEncoding)
			throws UnsupportedEncodingException {
		if (fromEncoding == null || toEncoding == null
				|| (fromEncoding.equals(toEncoding)))
			return text;
		return new String(text.getBytes(fromEncoding), toEncoding);
	}
}

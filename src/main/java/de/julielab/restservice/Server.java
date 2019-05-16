package de.julielab.restservice;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.threadPool;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.google.gson.Gson;

import de.julielab.restservice.annotation.AnalysisEngineConfiguration;
import de.julielab.restservice.annotation.UIMAWrapper;

public class Server {

	static void start(int port, int maxThreads, int minThreads,
			int timeOutMillis) {
		port(port);
		threadPool(maxThreads, minThreads, timeOutMillis);
		routing();
	}

	static void startServer() {
		start(Arguments.DEFAULT_PORT, Arguments.DEFAULT_MAX_THREADS,
				Arguments.DEFAULT_MIN_THREADS, Arguments.DEFAULT_TIMEOUT);
	}

	static void startServer(Arguments a) {
		start(a.port, a.maxThreads, a.minThreads, a.timeOutMillis);
	}

	private static void routing() {
		post("/entities", (req, res) -> {
			String charEncoding = req.headers(Main.HEADER_CHAR_SET);
			try {
				String text = EncodingUtils.reEncode(req.body(), charEncoding,
						Main.DEFAULT_CHARSET);
				return new Gson().toJson(new UIMAWrapper(10, 100, new AnalysisEngineConfiguration(false))
						.annotate(EncodingUtils.reEncode(text,
								Main.DEFAULT_CHARSET, charEncoding)));
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
}

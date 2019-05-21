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

	static void start(int port, String modelFolder) {
		port(port);
		routing(modelFolder);
	}

	static void startServer() {
		start(Arguments.DEFAULT_PORT, Arguments.DEFAULT_MODEL_FOLDER);
	}

	static void startServer(Arguments a) {
		start(a.port, a.modelFolder);
	}

	private static void routing(String modelFolder) {
		post("/entities", (req, res) -> {
			String charEncoding = req.headers(Main.HEADER_CHAR_SET);
			try {
				String text = EncodingUtils.reEncode(req.body(), charEncoding,
						Main.DEFAULT_CHARSET);
				return new Gson().toJson(new UIMAWrapper(1, 100, new AnalysisEngineConfiguration(false, modelFolder))
						.annotate(text, charEncoding));
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

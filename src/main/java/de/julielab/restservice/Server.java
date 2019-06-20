package de.julielab.restservice;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.google.gson.Gson;

import de.julielab.restservice.annotation.UIMAWrapper;
import de.julielab.restservice.annotation.configuration.AnalysisEngineConfiguration;

public class Server {

	private static void routing(final UIMAWrapper uima) {
		final Gson gson = new Gson();
		post("/entities", (req, res) -> {
			final String charEncoding = req.headers(Main.HEADER_CHAR_SET);
			try {
				final String text = EncodingUtils.reEncode(req.body(),
						charEncoding, Main.DEFAULT_CHARSET);
				return gson.toJson(uima.annotate(text, charEncoding));
			} catch (final UnsupportedEncodingException e) {
				res.status(406);
				res.body("Not Acceptable");
				return res;
			}
		});
		//TODO make sure UIMA pipeline is returned when no longer needed

		get("/supported-encodings",
				(req, res) -> Charset.availableCharsets().keySet());
	}

	static void startServer(final Arguments a) throws Exception {
		startServer(a.port, a.modelFolder);
	}

	static void startServer(final int port, final String modelFolder)
			throws Exception {
		port(port);
		final UIMAWrapper uima = new UIMAWrapper(1, 100,
				new AnalysisEngineConfiguration(false, modelFolder));
		routing(uima);
	}
}

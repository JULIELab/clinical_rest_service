import static spark.Spark.*;

import java.util.Map;

import org.docopt.Docopt;

import com.google.gson.Gson;

import annotation.UIMAWrapper;

public class Main {
	private static final String DOC_OPT = null;

	public static void main(String[] args) {
		
		Map<String,Object> arguments = new Docopt(DOC_OPT).parse(args);
		
		post("/entities", (req, res) -> {
			String text = req.body();
			return new Gson().toJson(new UIMAWrapper().annotate(text));
		});
	}
}

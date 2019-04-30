import static spark.Spark.*;


import com.google.gson.Gson;

import annotation.UIMAWrapper;

public class Main {
	public static void main(String[] args) {
		post("/entities", (req, res) -> {
			String text = req.body();
			return new Gson().toJson(new UIMAWrapper().annotate(text));
		});
	}
}

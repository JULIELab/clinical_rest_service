import static spark.Spark.*;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class Main {
	public static void main(String[] args) {
		post("/entities", (req, res) -> {
			String text = req.body();
			return new Gson().toJson(findEntities(text));
		});
	}

	static List<Entity> findEntities(String text) {
		return Arrays.asList(new Entity("Hase", 0, 666, "fooo"));
	}
	
	
//	Code for generating a UIMA CAS Pool
//
//	final TypeSystemDescription tsDesc = TypeSystemDescriptionFactory.createTypeSystemDescription("de.julielab.jcore.types.jcore-all-types");
//	final ProcessingResourceMetaData_impl metaData = new ProcessingResourceMetaData_impl();
//	metaData.setTypeSystem(tsDesc);
//	try {
//	    final CasPool casPool = new CasPool(24, metaData);
//	} catch (ResourceInitializationException e) {
//	    e.printStackTrace();
//	}
}

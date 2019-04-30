package annotation;

import java.util.Arrays;
import java.util.List;

public class UIMAWrapper {
	
	//TODO: CAS Pool (see below), read pipeline descriptors, map uima types -> entities

	public List<Entity> annotate(String text){
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

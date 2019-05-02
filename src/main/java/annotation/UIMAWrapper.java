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
//	try {
//	    // Create a CasPool.
//	    String[] typeSystemDescriptorNames;
//	    try (BufferedReader br = FileUtilities.getReaderFromFile(new File(TrecConfig.UIMA_TYPES_DESCRIPTORNAMES))) {
//	        typeSystemDescriptorNames = br.lines().filter(Predicate.not(String::isBlank)).map(String::trim).toArray(String[]::new);
//	    }
//	    final TypeSystemDescription tsDesc = TypeSystemDescriptionFactory.createTypeSystemDescription(typeSystemDescriptorNames);
//	    final ProcessingResourceMetaData_impl metaData = new ProcessingResourceMetaData_impl();
//	    metaData.setTypeSystem(tsDesc);
//	    try {
//	        casPool = new CasPool(10, metaData, new ResourceManager_impl());
//	    } catch (ResourceInitializationException e) {
//	        log.error("Could not create CAS pool", e);
//	    }
//	} catch (IOException e) {
//	    log.error("The CAS pool could not be created because the file with the UIMA types to load could not be read", e);
//	}
}

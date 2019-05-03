package annotation;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.util.CasPool;

public class UIMAPipelineAndCAS {
	
	private CAS cas;
	private AnalysisEngine[] engines;
	private Class[] classesToAnnotate;
	
	private static CasPool createCasPool() {
		return null;
		//		try {
		//		    // Create a CasPool.
		//		    String[] typeSystemDescriptorNames;
		//		    try (BufferedReader br = FileUtilities.getReaderFromFile(new File(TrecConfig.UIMA_TYPES_DESCRIPTORNAMES))) {
		//		        typeSystemDescriptorNames = br.lines().filter(Predicate.not(String::isBlank)).map(String::trim).toArray(String[]::new);
		//		    }
		//		    final TypeSystemDescription tsDesc = TypeSystemDescriptionFactory.createTypeSystemDescription(typeSystemDescriptorNames);
		//		    final ProcessingResourceMetaData_impl metaData = new ProcessingResourceMetaData_impl();
		//		    metaData.setTypeSystem(tsDesc);
		//		    try {
		//		        casPool = new CasPool(10, metaData, new ResourceManager_impl());
		//		    } catch (ResourceInitializationException e) {
		//		        log.error("Could not create CAS pool", e);
		//		    }
		//		} catch (IOException e) {
		//		    log.error("The CAS pool could not be created because the file with the UIMA types to load could not be read", e);
		//		}
	}

	public void process(String text) throws AnalysisEngineProcessException, CASException {
		// TODO Auto-generated method stub
		cas.reset();
		cas.setDocumentText(text);
		SimplePipeline.runPipeline(cas, engines);
		JCas jcas = cas.getJCas();
		List<Entity> results = new ArrayList<>();
		for(Class c: classesToAnnotate){
			AnnotationIndex<Annotation> index = jcas.getAnnotationIndex(c);
			for(Annotation a: index){
				;
				;
				
				results.add(new Entity(a.getType().toString(), a.getBegin(), a.getEnd(), a.getCoveredText()));
				//TODO offsets would need to be fixed in different encodings are really required 🤷
				// StringUtils might help...
			}
		}
	}
}

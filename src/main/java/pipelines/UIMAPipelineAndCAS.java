package pipelines;

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

import annotation.Entity;

public class UIMAPipelineAndCAS implements IPipelineAndCAS {

	private final CAS cas;
	private final AnalysisEngine[] engines;
	private final Class[] classesToAnnotate;

	public UIMAPipelineAndCAS(AnalysisEngine[] engines, Class[] classesToAnnotate) {
		this.engines = engines;
		this.classesToAnnotate = classesToAnnotate;
		this.cas = null; //TODO initialize
	}
	
	@Override
	public List<Entity> process(String text)
			throws AnalysisEngineProcessException, CASException {
		// TODO Auto-generated method stub
		cas.reset();
		cas.setDocumentText(text);
		SimplePipeline.runPipeline(cas, engines);
		JCas jcas = cas.getJCas();
		List<Entity> results = new ArrayList<>();
		for (Class c : classesToAnnotate) {
			AnnotationIndex<Annotation> index = jcas.getAnnotationIndex(c);
			for (Annotation a : index) {
				;;

				results.add(new Entity(a.getType().toString(), a.getBegin(),
						a.getEnd(), a.getCoveredText()));
				//TODO offsets would need to be fixed if different encodings are really required 🤷
				// StringUtils might help...
			}
		}
		return results;
	}
}

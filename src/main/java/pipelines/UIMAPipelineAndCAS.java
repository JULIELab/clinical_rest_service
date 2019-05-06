package pipelines;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContextAdmin;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import annotation.Entity;

public class UIMAPipelineAndCAS implements IPipelineAndCAS {

	private final CAS cas;
	private final AnalysisEngine[] engines;
	private final Type[] typesToAnnotate;

	public UIMAPipelineAndCAS(AnalysisEngine[] engines, Type[] typesToAnnotate) {
		this.engines = engines;
		this.typesToAnnotate = typesToAnnotate;
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
		for (Type t : typesToAnnotate) {
			AnnotationIndex<Annotation> index = jcas.getAnnotationIndex(t);
			for (Annotation a : index) {
				results.add(new Entity(t.getShortName(), a.getBegin(),
						a.getEnd(), a.getCoveredText()));
				//TODO offsets would need to be fixed if different encodings are really required 🤷
				// StringUtils might help...
			}
		}
		return results;
	}
}

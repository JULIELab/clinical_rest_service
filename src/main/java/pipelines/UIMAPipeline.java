package pipelines;

import static org.apache.uima.fit.factory.JCasFactory.createJCas;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;

import annotation.Entity;
import de.julielab.jcore.types.EntityMention;

public class UIMAPipeline implements IPipeline {

	private final JCas jcas;
	private final AnalysisEngine[] engines;

	public UIMAPipeline(AnalysisEngine[] engines) throws UIMAException {
		this.engines = engines;
		this.jcas = createJCas();
	}

	@Override
	public List<Entity> process(String text)
			throws AnalysisEngineProcessException, CASException {
		// TODO Auto-generated method stub
		jcas.reset();
		jcas.setDocumentText(text);
		runPipeline(jcas, engines);
		List<Entity> results = new ArrayList<>();
			for (EntityMention a : jcas.getAnnotationIndex(EntityMention.class)) {
				results.add(new Entity(a.getSpecificType(), a.getBegin(),
						a.getEnd(), a.getCoveredText()));
				//TODO offsets would need to be fixed if different encodings are really required 🤷
				// StringUtils might help...
			}
		return results;
	}
}

package de.julielab.restservice.pipelines;

import static org.apache.uima.fit.factory.JCasFactory.createJCas;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.jcas.JCas;

import de.julielab.restservice.annotation.Entity;
import de.julielab.jcore.types.EntityMention;
import de.julielab.restservice.EncodingUtils;

public class UIMAPipeline implements IPipeline {

	private final JCas jcas;
	private final String internalEncoding;
	private final AnalysisEngine[] engines;

	public UIMAPipeline(String internalEncoding, AnalysisEngine[] engines)
			throws UIMAException {
		this.internalEncoding = internalEncoding;
		this.engines = engines;
		this.jcas = createJCas();
	}

	
	//Re-encode Input to match internal format, Entity offsets match input but all Strings are UTF8
	@Override
	public List<Entity> process(String text, String fromEncoding)
			throws Exception {
		jcas.reset();
		String internalText = EncodingUtils.reEncode(text, fromEncoding,
				internalEncoding);
		jcas.setDocumentText(internalText);
		runPipeline(jcas, engines);
		List<Entity> results = new ArrayList<>();
		for (EntityMention a : jcas.getAnnotationIndex(EntityMention.class)) {
			results.add(new Entity(a.getSpecificType(), a.getBegin(),
					a.getEnd(), a.getCoveredText()));
		}
		EncodingUtils.correctOffsetsInPlace(results, internalText,
				internalEncoding, fromEncoding);
		return results;
	}
}

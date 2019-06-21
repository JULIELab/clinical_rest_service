package de.julielab.restservice.annotation.pipelines;

import static org.apache.uima.fit.factory.JCasFactory.createJCas;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;

import de.julielab.jcore.types.EntityMention;
import de.julielab.restservice.EncodingUtils;
import de.julielab.restservice.annotation.Entity;

public class UIMAPipeline implements IPipeline {

	private static final String INTERNAL_ENCODING = "utf8";
	private final JCas jcas;
	private final AnalysisEngine[] engines;

	public UIMAPipeline(final AnalysisEngine[] engines) throws UIMAException {
		this.engines = engines;
		jcas = createJCas();
	}

	//Re-encode Input to match internal format, Entity offsets match input but all Strings are UTF8
	@Override
	public List<Entity> process(final String text, final String fromEncoding)
			throws Exception {
		jcas.reset();
		final String internalText = EncodingUtils.reEncode(text, fromEncoding,
				INTERNAL_ENCODING);
		jcas.setDocumentText(internalText);
		runPipeline(jcas, engines);
		final List<Entity> results = new ArrayList<>();
		for (final EntityMention a : jcas
				.getAnnotationIndex(EntityMention.class))
			results.add(new Entity(a.getSpecificType(), a.getBegin(),
					a.getEnd(), a.getCoveredText()));
		EncodingUtils.correctOffsetsInPlace(results, internalText,
				INTERNAL_ENCODING, fromEncoding);
		return results;
	}
}

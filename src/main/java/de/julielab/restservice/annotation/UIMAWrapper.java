package de.julielab.restservice.annotation;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.resource.ResourceInitializationException;

import de.julielab.jcore.ae.jnet.uima.EntityAnnotator;
import de.julielab.jcore.ae.jpos.postagger.POSAnnotator;
import de.julielab.jcore.ae.jsbd.main.SentenceAnnotator;
import de.julielab.jcore.ae.jtbd.main.TokenAnnotator;
import de.julielab.restservice.annotation.pipelines.AutoClosingPipeline;
import de.julielab.restservice.annotation.pipelines.IPipeline;
import de.julielab.restservice.annotation.pipelines.UIMAPipeline;

public class UIMAWrapper {

	static AnalysisEngine[] createAnalysisEngines(
			final AnalysisEngineConfiguration config)
			throws ResourceInitializationException {
		final ArrayList<AnalysisEngine> aes = new ArrayList<>(4);
		aes.add(createEngine(SentenceAnnotator.class,
				config.getSentenceSplitterConfig()));
		aes.add(createEngine(TokenAnnotator.class,
				config.getTokenizerConfig()));
		if (config.usePOS)
			aes.add(createEngine(POSAnnotator.class,
					config.getPosTaggerParams()));
		for(Object[] params : config.getEntityTaggerParams())
			aes.add(createEngine(EntityAnnotator.class, params));
		return aes.toArray(new AnalysisEngine[aes.size()]);
	}

	private static IPipeline[] makePipelines(final int numThreads,
			final AnalysisEngineConfiguration analysisEngineConfiguration)
			throws Exception {
		if (numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		final IPipeline[] pipelines = new IPipeline[numThreads];
		for (int i = 0; i < numThreads; ++i)
			pipelines[i] = new UIMAPipeline(
					createAnalysisEngines(analysisEngineConfiguration));
		return pipelines;
	}

	private final int numThreads;

	final ArrayBlockingQueue<IPipeline> pipelines;

	public UIMAWrapper(final int numThreads, final int timeout,
			final AnalysisEngineConfiguration analysisEngineConfiguration)
			throws Exception {
		this(makePipelines(numThreads, analysisEngineConfiguration));
	}

	UIMAWrapper(final IPipeline... pipelines)
			throws IllegalAccessException, InterruptedException {
		numThreads = pipelines.length;
		if (numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		this.pipelines = new ArrayBlockingQueue<>(numThreads);
		for (final IPipeline p : pipelines)
			this.pipelines.put(p);
	}

	public List<Entity> annotate(final String text, final String fromEncoding)
			throws Exception {
		try (AutoClosingPipeline pipeline = new AutoClosingPipeline(
				pipelines)) {
			return pipeline.process(text, fromEncoding);
		}
	}

	public int getNumThreads() {
		return numThreads;
	}
}

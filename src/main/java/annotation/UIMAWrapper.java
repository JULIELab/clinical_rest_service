package annotation;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.resource.ResourceInitializationException;

import de.julielab.jcore.ae.jnet.uima.EntityAnnotator;
import de.julielab.jcore.ae.jpos.postagger.POSAnnotator;
import de.julielab.jcore.ae.jsbd.main.SentenceAnnotator;
import de.julielab.jcore.ae.jtbd.main.TokenAnnotator;
import pipelines.AutoClosingPipeline;
import pipelines.IPipeline;
import pipelines.UIMAPipeline;

public class UIMAWrapper {

	static AnalysisEngine[] createAnalysisEngines(
			final AnalysisEngineConfiguration config)
			throws ResourceInitializationException {
		final AnalysisEngine sentenceSplitter = createEngine(
				SentenceAnnotator.class, config.getSentenceSplitterConfig()); //TODO: model name
		final AnalysisEngine tokenizer = createEngine(TokenAnnotator.class,
				config.getTokenizerConfig()); //TODO: model name
		final AnalysisEngine posTagger = createEngine(POSAnnotator.class,
				config.getPosTaggerParams());
		final AnalysisEngine entityTagger = createEngine(EntityAnnotator.class,
				config.getEntityTaggerParams());
		return new AnalysisEngine[] { sentenceSplitter, tokenizer, posTagger,
				entityTagger };
	}

	private static IPipeline[] makePipelines(final int numThreads)
			throws Exception {
		if (numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		final IPipeline[] pipelines = new IPipeline[numThreads];
		for (int i = 0; i < numThreads; ++i)
			pipelines[i] = new UIMAPipeline(
					createAnalysisEngines(new AnalysisEngineConfiguration()));
		return pipelines;
	}

	private final int numThreads;

	private final int timeout; //TODO: honor!

	private final ArrayBlockingQueue<IPipeline> pipelines;

	public UIMAWrapper(final int numThreads, final int timeout)
			throws Exception {
		this(timeout, makePipelines(numThreads));
	}

	UIMAWrapper(final int timeout, final IPipeline... pipelines)
			throws IllegalAccessException, InterruptedException {
		numThreads = pipelines.length;
		this.timeout = timeout;
		if (numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		this.pipelines = new ArrayBlockingQueue<>(numThreads);
		for (final IPipeline p : pipelines)
			this.pipelines.put(p);
	}

	public List<Entity> annotate(final String text) throws Exception {
		try (AutoClosingPipeline pipeline = new AutoClosingPipeline(
				pipelines)) {
			return pipeline.process(text);
		}
	}

	public int getNumThreads() {
		return numThreads;
	}
}

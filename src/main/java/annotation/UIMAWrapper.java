package annotation;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.resource.ResourceInitializationException;

import pipelines.AutoClosingPipeline;
import pipelines.IPipeline;
import pipelines.UIMAPipeline;
import de.julielab.jcore.ae.jnet.uima.EntityAnnotator;
import de.julielab.jcore.ae.jpos.postagger.POSAnnotator;
import de.julielab.jcore.ae.jsbd.main.SentenceAnnotator;
import de.julielab.jcore.ae.jtbd.main.TokenAnnotator;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;

public class UIMAWrapper {

	private static final Object[] ENTITY_TAGGER_PARAMS = new Object[] {
			"EntityTypes", "LABEL=de.julielab.jcore.types.EntityMention", //TODO add labels, all should use de.julielab.jcore.types.EntityMention
			"ExpandAbbreviations", "false", "ConsistencyPreservation", "",
			"ModelFilename", "TODO" //TODO get real name
	};

	private static final Object[] POS_TAGGER_PARAMS = new Object[] { "tagset",
			"full.qualified.name", //TODO get real set
			"ModelFilename", "TODO" //TODO get real name
	};

	private final int numThreads;
	private final int timeout; //TODO: honor!
	private final ArrayBlockingQueue<IPipeline> pipelines;

	public List<Entity> annotate(String text) throws Exception {
		try (AutoClosingPipeline pipeline = new AutoClosingPipeline(
				pipelines)) {
			return pipeline.process(text);
		}
	}

	public UIMAWrapper(int numThreads, int timeout) throws Exception {
		this(timeout, makePipelines(numThreads));
	}

	UIMAWrapper(int timeout, IPipeline... pipelines)
			throws IllegalAccessException, InterruptedException {
		this.numThreads = pipelines.length;
		this.timeout = timeout;
		if (numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		this.pipelines = new ArrayBlockingQueue<>(numThreads);
		for (IPipeline p : pipelines)
			this.pipelines.put(p);
	}

	private static AnalysisEngine[] createAnalysisEngines()
			throws ResourceInitializationException {
		AnalysisEngine sentenceSplitter = createEngine(SentenceAnnotator.class,
				SentenceAnnotator.PARAM_MODEL_FILE, "todo"); //TODO: model name
		AnalysisEngine tokenizer = createEngine(TokenAnnotator.class,
				TokenAnnotator.PARAM_MODEL, "todo"); //TODO: model name
		AnalysisEngine posTagger = createEngine(POSAnnotator.class,
				POS_TAGGER_PARAMS);
		AnalysisEngine entityTagger = createEngine(EntityAnnotator.class,
				ENTITY_TAGGER_PARAMS);
		return new AnalysisEngine[] { sentenceSplitter, tokenizer, posTagger,
				entityTagger };
	}

	private static IPipeline[] makePipelines(int numThreads) throws Exception {
		if (numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		IPipeline[] pipelines = new IPipeline[numThreads];
		for (int i = 0; i < numThreads; ++i)
			pipelines[i] = new UIMAPipeline(createAnalysisEngines());
		return pipelines;
	}

	public int getNumThreads() {
		return numThreads;
	}
}

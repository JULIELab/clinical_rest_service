package annotation;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.Type;
import org.apache.uima.resource.ResourceInitializationException;

import pipelines.AutoClosingPipeline;
import pipelines.IPipeline;
import pipelines.UIMAPipeline;
import de.julielab.jcore.ae.jnet.uima.EntityAnnotator;
import de.julielab.jcore.ae.jpos.postagger.POSAnnotator;
import de.julielab.jcore.ae.jsbd.main.SentenceAnnotator;
import de.julielab.jcore.ae.jtbd.main.TokenAnnotator;
import de.julielab.jcore.types.EntityMention;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;

public class UIMAWrapper {

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
	
	private static AnalysisEngine[] createAnalysisEngines() throws ResourceInitializationException{
		AnalysisEngine sentenceSplitter = createEngine(SentenceAnnotator.class); //TODO: key-value pairs of additional parameters
		AnalysisEngine tokenizer = createEngine(TokenAnnotator.class); //TODO: key-value pairs of additional parameters
		AnalysisEngine posTagger = createEngine(POSAnnotator.class); //TODO: key-value pairs of additional parameters
		AnalysisEngine entityTagger = createEngine(EntityAnnotator.class); //TODO: key-value pairs of additional parameters
		return new AnalysisEngine[]{sentenceSplitter, tokenizer, posTagger, entityTagger};
	}
	
	@SuppressWarnings("unchecked")
	private static Class<EntityMention>[] getTypesToAnnotate() throws ResourceInitializationException{
		return new Class[]{de.julielab.jcore.types.Disease.class}; //TODO: other entities to annotate
	}

	private static IPipeline[] makePipelines(int numThreads) throws Exception {
		if (numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		IPipeline[] pipelines = new IPipeline[numThreads];
		for (int i = 0; i < numThreads; ++i)
			pipelines[i] = new UIMAPipeline(createAnalysisEngines(), getTypesToAnnotate());
		return pipelines;
	}

	public int getNumThreads() {
		return numThreads;
	}
}

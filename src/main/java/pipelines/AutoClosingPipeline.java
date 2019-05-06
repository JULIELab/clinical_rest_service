package pipelines;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;

import annotation.Entity;

public class AutoClosingPipeline implements AutoCloseable, IPipeline {

	private final IPipeline pipeline;
	private final ArrayBlockingQueue<IPipeline> pipelines;

	public AutoClosingPipeline(ArrayBlockingQueue<IPipeline> pipelines)
			throws InterruptedException {
		this.pipelines = pipelines;
		this.pipeline = pipelines.take();
	}

	@Override
	public void close() throws Exception {
		pipelines.put(pipeline);
	}

	@Override
	public List<Entity> process(String text)
			throws AnalysisEngineProcessException, CASException {
		return pipeline.process(text);
	}

}
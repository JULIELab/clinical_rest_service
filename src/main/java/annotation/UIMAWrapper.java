package annotation;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import pipelines.AutoClosingPipeline;
import pipelines.IPipelineAndCAS;
import pipelines.UIMAPipelineAndCAS;

public class UIMAWrapper {

	//TODO: CAS Pool (see below), read pipeline descriptors, map uima types -> entities

	private final int numThreads;
	private final int timeout;
	private final ArrayBlockingQueue<IPipelineAndCAS> pipelines;

	public List<Entity> annotate(String text) throws Exception {
		try (AutoClosingPipeline pipeline = new AutoClosingPipeline(
				pipelines)) {
			return pipeline.process(text);
		}
	}

	public UIMAWrapper(int numThreads, int timeout) throws Exception {
		this(timeout, makePipelines(numThreads));
	}

	UIMAWrapper(int timeout, IPipelineAndCAS... pipelines)
			throws IllegalAccessException, InterruptedException {
		//		AnalysisEngineFactory.createEngine(MyAEImpl.class, myTypeSystem, 
		//				  paramName1, paramValue1,
		//				  paramName2, paramValue2,
		//				  ...);
		//		
		this.numThreads = pipelines.length;
		this.timeout = timeout;
		if (numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		this.pipelines = new ArrayBlockingQueue<>(numThreads);
		for (IPipelineAndCAS p : pipelines)
			this.pipelines.put(p);
	}

	static IPipelineAndCAS[] makePipelines(int numThreads) throws Exception {
		if (numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		IPipelineAndCAS[] pipelines = new IPipelineAndCAS[numThreads];
		for (int i = 0; i < numThreads; ++i)
			pipelines[i] = new UIMAPipelineAndCAS(null, null); //TODO intilaize properly
		return pipelines;
	}

	public int getNumThreads() {
		return numThreads;
	}
}

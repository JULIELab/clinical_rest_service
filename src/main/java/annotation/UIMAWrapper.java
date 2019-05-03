package annotation;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.util.CasPool;

public class UIMAWrapper {

	//TODO: CAS Pool (see below), read pipeline descriptors, map uima types -> entities

	private final int numThreads;
	private final int timeout;
	private final ArrayBlockingQueue<UIMAPipelineAndCAS> pipelines;

	public List<Entity> annotate(String text) throws AnalysisEngineProcessException, CASException, InterruptedException {
		UIMAPipelineAndCAS pipeline = pipelines.take();
		pipeline.process(text);
		//		SimplePipeline.runPipeline(cas, engines);
		return Arrays.asList(new Entity("Hase", 0, 666, "fooo"));
	}
	
	public String foo() throws InterruptedException{
		UIMAPipelineAndCAS i = pipelines.take();
		String s = i.toString();
		pipelines.put(i);
		return s;
	}

	public UIMAWrapper(int numThreads, int timeout) throws IllegalAccessException, InterruptedException {
		//		AnalysisEngineFactory.createEngine(MyAEImpl.class, myTypeSystem, 
		//				  paramName1, paramValue1,
		//				  paramName2, paramValue2,
		//				  ...);
		//		
		this.numThreads = numThreads;
		this.timeout = timeout;
		if(numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		pipelines = new ArrayBlockingQueue<>(numThreads);
		for(int i = 0; i<numThreads; ++i)
			pipelines.put(new UIMAPipelineAndCAS());
	}


	public int getNumThreads() {
		return numThreads;
	}
}

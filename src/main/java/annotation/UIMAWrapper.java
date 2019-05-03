package annotation;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;

public class UIMAWrapper {

	//TODO: CAS Pool (see below), read pipeline descriptors, map uima types -> entities

	private final int numThreads;
	private final int timeout;
	private final ArrayBlockingQueue<IPipelineAndCAS> pipelines;

	public List<Entity> annotate(String text) throws AnalysisEngineProcessException, CASException, InterruptedException {
		IPipelineAndCAS pipeline = pipelines.take();
		return pipeline.process(text);
	}
	
	public String foo() throws InterruptedException{
		IPipelineAndCAS i = pipelines.take();
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
	
	public UIMAWrapper(int timeout, IPipelineAndCAS... pipelines) throws IllegalAccessException, InterruptedException {
		//		AnalysisEngineFactory.createEngine(MyAEImpl.class, myTypeSystem, 
		//				  paramName1, paramValue1,
		//				  paramName2, paramValue2,
		//				  ...);
		//		
		this.numThreads = pipelines.length;
		this.timeout = timeout;
		if(numThreads < 1)
			throw new IllegalAccessException("Need at least 1 Thread");
		this.pipelines = new ArrayBlockingQueue<>(numThreads);
		for(IPipelineAndCAS p : pipelines)
			this.pipelines.put(p);
	}


	public int getNumThreads() {
		return numThreads;
	}
}

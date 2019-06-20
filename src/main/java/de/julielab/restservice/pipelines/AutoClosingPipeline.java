package de.julielab.restservice.pipelines;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import de.julielab.restservice.annotation.Entity;

public class AutoClosingPipeline implements AutoCloseable, IPipeline {

	private final IPipeline pipeline;
	private final ArrayBlockingQueue<IPipeline> pipelines;

	public AutoClosingPipeline(final ArrayBlockingQueue<IPipeline> pipelines)
			throws InterruptedException {
		this.pipelines = pipelines;
		pipeline = pipelines.take();
	}

	@Override
	public void close() throws Exception {
		pipelines.put(pipeline);
	}

	@Override
	public List<Entity> process(final String text, final String fromEncoding)
			throws Exception {
		return pipeline.process(text, fromEncoding);
	}

}
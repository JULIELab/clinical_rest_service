package de.julielab.restservice.pipelines;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;

import de.julielab.restservice.annotation.Entity;

public interface IPipeline {
	public List<Entity> process(String text)
			throws AnalysisEngineProcessException, CASException;
}
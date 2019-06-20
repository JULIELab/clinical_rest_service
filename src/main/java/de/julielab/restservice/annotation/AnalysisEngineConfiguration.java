package de.julielab.restservice.annotation;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;

public class AnalysisEngineConfiguration {

	public static AnalysisEngineConfiguration readConfiguration(
			final String configurationFile) throws Exception {
		final AnalysisEngineConfiguration config = new Gson().fromJson(
				Files.readString(Paths.get(configurationFile)),
				AnalysisEngineConfiguration.class);
		return config;
	}

	public boolean usePOS;
	public Object[] entityTaggerParams;
	public Object[] posTaggerParams;
	public Object[] sentenceSplitterParams;

	public Object[] tokenizerParams;

	@SuppressWarnings("unchecked")
	public Object[] getEntityTaggerParams() {
		//fixes mismatch between expected types, avoids modification of tagger classes
		final Object[] transformedEntityTaggerParams = entityTaggerParams
				.clone();
		for (int i = 0; i < transformedEntityTaggerParams.length; ++i)
			if (transformedEntityTaggerParams[i] instanceof ArrayList)
				transformedEntityTaggerParams[i] = ((ArrayList<String>) transformedEntityTaggerParams[i])
						.toArray(new String[0]);
		return transformedEntityTaggerParams;
	}

	public String getInternalEncoding() {
		return "utf-8";
	}

	public Object[] getPosTaggerParams() {
		return posTaggerParams;
	}

	public Object[] getSentenceSplitterConfig() {
		return sentenceSplitterParams;
	}

	public Object[] getTokenizerConfig() {
		return tokenizerParams;
	}

}
package de.julielab.restservice.annotation;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;

public class AnalysisEngineConfiguration {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static AnalysisEngineConfiguration readConfiguration(
			final String configurationFile) throws Exception {
		final AnalysisEngineConfiguration config = new Gson().fromJson(
				Files.readString(Paths.get(configurationFile)),
				AnalysisEngineConfiguration.class);
		//fixes mismatch between expected types, avoids modification of tagger classes
		for (int i = 0; i < config.entityTaggerParams.length; ++i)
			if (config.entityTaggerParams[i] instanceof ArrayList)
				config.entityTaggerParams[i] = ((ArrayList) config.entityTaggerParams[i])
						.toArray(new String[0]);
		return config;
	}

	private AnalysisEngineConfiguration() {
		//should use readConfiguration instead	
	}

	public boolean usePOS;
	//Object[] sadly needed by UIMA API 🙄
	public Object[] entityTaggerParams;
	public Object[] posTaggerParams;
	public Object[] sentenceSplitterParams;
	public Object[] tokenizerParams;

	public Object[] getEntityTaggerParams() {
		return entityTaggerParams;
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
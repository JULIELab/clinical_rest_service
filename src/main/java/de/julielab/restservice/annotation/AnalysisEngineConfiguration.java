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
		for (Object[] params : config.entityTaggerParams)
			for (int i = 0; i < params.length; ++i)
				if (params[i] instanceof ArrayList)
					params[i] = ((ArrayList) params[i])
							.toArray(new String[0]);
		return config;
	}

	public boolean usePOS;

	//Object[] sadly needed by UIMA API 🙄
	public ArrayList<Object[]> entityTaggerParams;
	public Object[] posTaggerParams;
	public Object[] sentenceSplitterParams;
	public Object[] tokenizerParams;

	private AnalysisEngineConfiguration() {
		//should use readConfiguration instead
	}

	public ArrayList<Object[]> getEntityTaggerParams() {
		return entityTaggerParams;
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
package de.julielab.restservice.annotation;

import de.julielab.jcore.ae.jsbd.main.SentenceAnnotator;
import de.julielab.jcore.ae.jtbd.main.TokenAnnotator;

public class AnalysisEngineConfiguration {
	private static final Object[] ENTITY_TAGGER_PARAMS = new Object[] {
			"EntityTypes",
			new String[] { "gene-protein=de.julielab.jcore.types.EntityMention",
					"gene-generic=de.julielab.jcore.types.EntityMention",
					"gene-rna=de.julielab.jcore.types.EntityMention" }, 
			"ExpandAbbreviations", false, "ConsistencyPreservation", null,
			"ModelFilename", "src/main/resources/models/jnet-pennbio.gz" //TODO get real name
	};

	private static final Object[] POS_TAGGER_PARAMS = new Object[] { "tagset",
			"de.julielab.jcore.types.STTSMedPOSTag", "ModelFilename",
			"src/main/resources/models/jpos-framed.gz" };

	public final boolean usePOS;
	
	public AnalysisEngineConfiguration(boolean usePOS) {
		this.usePOS = usePOS;
	}
	
	/**
	 * @return the entityTaggerParams
	 */
	public Object[] getEntityTaggerParams() {
		return ENTITY_TAGGER_PARAMS;
	}

	/**
	 * @return the posTaggerParams
	 */
	public Object[] getPosTaggerParams() {
		return POS_TAGGER_PARAMS;
	}

	public Object[] getSentenceSplitterConfig() {
		return new Object[] { SentenceAnnotator.PARAM_MODEL_FILE,
				"src/main/resources/models/jsbd-framed.gz" };
	}

	public Object[] getTokenizerConfig() {
		return new Object[] { TokenAnnotator.PARAM_MODEL,
				"src/main/resources/models/jtbd-framed.gz" };
	}

}

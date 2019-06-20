package de.julielab.restservice.annotation;

import de.julielab.jcore.ae.jsbd.main.SentenceAnnotator;
import de.julielab.jcore.ae.jtbd.main.TokenAnnotator;

public class AnalysisEngineConfiguration {

	public final boolean usePOS;
	public final String modelFolder;

	public AnalysisEngineConfiguration(final boolean usePOS,
			final String modelFolder) {
		this.usePOS = usePOS;
		this.modelFolder = modelFolder;
	}

	public Object[] getEntityTaggerParams() {
		return new Object[] { "EntityTypes",
				new String[] {
						"gene-protein=de.julielab.jcore.types.EntityMention",
						"gene-generic=de.julielab.jcore.types.EntityMention",
						"gene-rna=de.julielab.jcore.types.EntityMention" },
				"ExpandAbbreviations", false, "ConsistencyPreservation", null,
				"ModelFilename", modelFolder + "/jnet-pennbio.gz" };
	}

	public String getInternalEncoding() {
		return "utf-8";
	}

	public Object[] getPosTaggerParams() {
		return new Object[] { "tagset", "de.julielab.jcore.types.STTSMedPOSTag",
				"ModelFilename", modelFolder + "/jpos-framed.gz" };
	}

	public Object[] getSentenceSplitterConfig() {
		return new Object[] { SentenceAnnotator.PARAM_MODEL_FILE,
				modelFolder + "/jsbd-framed.gz" };
	}

	public Object[] getTokenizerConfig() {
		return new Object[] { TokenAnnotator.PARAM_MODEL,
				modelFolder + "/jtbd-framed.gz" };
	}

}

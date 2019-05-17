package de.julielab.restservice.annotation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.julielab.restservice.annotation.AnalysisEngineConfiguration;
import de.julielab.restservice.annotation.Entity;
import de.julielab.restservice.annotation.UIMAWrapper;
import de.julielab.restservice.pipelines.IPipeline;
import de.julielab.restservice.Arguments;

public class UIMAWrapperTest {

	class DummyPipeline implements IPipeline {

		@Override
		public List<Entity> process(String text, String fromEncoding)
				throws Exception {
			return Arrays.asList(new Entity("foo", 1, 111, "bar"));
		}

	}

	@Test
	public void testAnnotateWithDummy() throws Exception {
		UIMAWrapper uimaWrapper = new UIMAWrapper(100, new DummyPipeline());
		List<Entity> actual = uimaWrapper.annotate("does not matter", "utf-8");
		assertEquals(1, actual.size());
	}
	
//	@Test
//	public void testCreatePipelines() throws Exception {
//		AnalysisEngine[] aes = UIMAWrapper.createAnalysisEngines(new AnalysisEngineConfiguration());
//		assertEquals(4, aes.length);
//	}
	
	@Test
	public void testAnnotate() throws Exception {
		UIMAWrapper wrapper = new UIMAWrapper(1,1, new AnalysisEngineConfiguration(false,Arguments.DEFAULT_MODEL_FOLDER));
		List<Entity> entities = wrapper.annotate("IL2 and igf oder mouse cancer", "utf-8");
		assertEquals(1, entities.size());
		assertEquals(new Entity("gene-protein", 0, 3, "IL2"), entities.get(0));
	}

}

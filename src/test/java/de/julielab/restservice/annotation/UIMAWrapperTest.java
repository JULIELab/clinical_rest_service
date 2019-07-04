package de.julielab.restservice.annotation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.julielab.restservice.annotation.pipelines.IPipeline;
import de.julielab.restservice.annotation.pipelines.UIMAPipeline;

public class UIMAWrapperTest {

	class DummyPipeline implements IPipeline {

		@Override
		public List<Entity> process(final String text,
				final String fromEncoding) throws Exception {
			return Arrays.asList(new Entity("foo", 1, 111, "bar"));
		}

	}

	@Test
	public void testAnnotate() throws Exception {
		final UIMAWrapper wrapper = new UIMAWrapper(1, 1,
				AnalysisEngineConfiguration.readConfiguration(
						"src/test/resources/uima.config.json"));

		final List<Entity> entities = wrapper
				.annotate("IL2 and igf oder mouse cancer", "utf-8");
		assertEquals(1, entities.size());
		assertEquals(new Entity("gene-protein", 0, 3, "IL2"), entities.get(0));
	}

	@Test
	public void loadMultipleEntityAnnotators() throws Exception {
		final UIMAWrapper wrapper = new UIMAWrapper(1, 1,
				AnalysisEngineConfiguration.readConfiguration(
						"src/test/resources/uima.config.2entities.json"));

		assertEquals(4, ((UIMAPipeline) wrapper.pipelines.peek()).engines.length);
	}

	//	@Test
	//	public void testCreatePipelines() throws Exception {
	//		AnalysisEngine[] aes = UIMAWrapper.createAnalysisEngines(new AnalysisEngineConfiguration());
	//		assertEquals(4, aes.length);
	//	}

	@Test
	public void testAnnotateWithDummy() throws Exception {
		final UIMAWrapper uimaWrapper = new UIMAWrapper(new DummyPipeline());
		final List<Entity> actual = uimaWrapper.annotate("does not matter",
				"utf-8");
		assertEquals(1, actual.size());
	}

}

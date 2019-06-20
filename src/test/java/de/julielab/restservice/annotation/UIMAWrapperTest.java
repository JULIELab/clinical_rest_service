package de.julielab.restservice.annotation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.julielab.restservice.ServerTest;
import de.julielab.restservice.pipelines.IPipeline;

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
				new AnalysisEngineConfiguration(false, ServerTest.TEST_MODELS));
		final List<Entity> entities = wrapper
				.annotate("IL2 and igf oder mouse cancer", "utf-8");
		assertEquals(1, entities.size());
		assertEquals(new Entity("gene-protein", 0, 3, "IL2"), entities.get(0));
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

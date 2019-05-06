package annotation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.junit.Test;

import pipelines.IPipeline;

public class UIMAWrapperTest {

	class DummyPipeline implements IPipeline {

		@Override
		public List<Entity> process(String text)
				throws AnalysisEngineProcessException, CASException {
			return Arrays.asList(new Entity("foo", 1, 111, "bar"));
		}

	}

	@Test
	public void test() throws Exception {
		UIMAWrapper uimaWrapper = new UIMAWrapper(100, new DummyPipeline());
		List<Entity> actual = uimaWrapper.annotate("does not matter");
		assertEquals(1, actual.size());
	}

}

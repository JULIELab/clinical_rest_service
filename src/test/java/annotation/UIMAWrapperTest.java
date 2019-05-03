package annotation;

import static org.junit.Assert.*;

import org.junit.Test;

public class UIMAWrapperTest {

	@Test
	public void test() throws Exception {
		UIMAWrapper uimaWrapper = new UIMAWrapper(10, 100);
		for(int i=0; i<100; ++i)
			System.out.println(uimaWrapper.foo());
	}

}

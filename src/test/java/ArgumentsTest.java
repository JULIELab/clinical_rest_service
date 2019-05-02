import static org.junit.Assert.*;

import org.junit.Test;

public class ArgumentsTest {

	@Test
	public void test() {
		Arguments actual = Arguments.parseArguments("start".split(" "));
		assertEquals(new Arguments(), actual);
	}

}

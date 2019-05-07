import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import annotation.Entity;

public class EncodingUtilsTest {
	
	@Test
	public void testCorrectOffsetsInPlace() throws UnsupportedEncodingException {
		List<Entity> entities = Arrays.asList(new Entity("foo", 6, 10, "füße"),new Entity("bar", 0, 3, "bär"));
		EncodingUtils.correctOffsetsInPlace(entities, "bär x füße", "utf-8", "cp1251");
	}

}

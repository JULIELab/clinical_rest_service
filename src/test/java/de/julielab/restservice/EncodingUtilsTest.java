package de.julielab.restservice;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.julielab.restservice.annotation.Entity;

public class EncodingUtilsTest {

	@Test
	public void testCorrectOffsetsInPlaceLonger()
			throws UnsupportedEncodingException {
		final List<Entity> entities = Arrays.asList(
				new Entity("foo", 6, 10, "füße"),
				new Entity("bar", 0, 3, "bär"));
		EncodingUtils.correctOffsetsInPlace(entities, "bär x füße", "utf-8",
				"cp1251", false);
		assertEquals(new Entity("foo", 7, 13, "fГјГџe"), entities.get(0));
		assertEquals(new Entity("bar", 0, 4, "bГ¤r"), entities.get(1));
	}

	@Test
	public void testCorrectOffsetsInPlaceLongerUTF8()
			throws UnsupportedEncodingException {
		final List<Entity> entities = Arrays.asList(
				new Entity("foo", 6, 10, "füße"),
				new Entity("bar", 0, 3, "bär"));
		EncodingUtils.correctOffsetsInPlace(entities, "bär x füße", "utf-8",
				"cp1251");
		assertEquals(new Entity("foo", 7, 13, "füße"), entities.get(0));
		assertEquals(new Entity("bar", 0, 4, "bär"), entities.get(1));
	}

	@Test
	public void testCorrectOffsetsInPlaceShorter()
			throws UnsupportedEncodingException {
		final List<Entity> entities = Arrays.asList(
				new Entity("foo", 7, 13, "fГјГџe"),
				new Entity("bar", 0, 4, "bГ¤r"));
		EncodingUtils.correctOffsetsInPlace(entities, "bГ¤r x fГјГџe", "cp1251",
				"utf-8", false);
		assertEquals(new Entity("foo", 6, 10, "füße"), entities.get(0));
		assertEquals(new Entity("bar", 0, 3, "bär"), entities.get(1));
	}

	@Test
	public void testCorrectOffsetsInPlaceShorterUTF8()
			throws UnsupportedEncodingException {
		final List<Entity> entities = Arrays.asList(
				new Entity("foo", 7, 13, "fГјГџe"),
				new Entity("bar", 0, 4, "bГ¤r"));
		EncodingUtils.correctOffsetsInPlace(entities, "bГ¤r x fГјГџe", "cp1251",
				"utf-8");
		assertEquals(new Entity("foo", 6, 10, "füße"), entities.get(0));
		assertEquals(new Entity("bar", 0, 3, "bär"), entities.get(1));
	}

}

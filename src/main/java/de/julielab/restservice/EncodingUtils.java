package de.julielab.restservice;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import de.julielab.restservice.annotation.Entity;

public class EncodingUtils {

	private static final String UTF8 = StandardCharsets.UTF_8.name();

	static String reEncode(String text, String fromEncoding, String toEncoding)
			throws UnsupportedEncodingException {
		if (fromEncoding == null || toEncoding == null
				|| (fromEncoding.equals(toEncoding)))
			return text;
		return new String(text.getBytes(fromEncoding), toEncoding);
	}

	static void correctOffsetsInPlace(List<Entity> entities, String text,
			String fromEncoding, String toEncoding, boolean textInUTF8)
			throws UnsupportedEncodingException {
		if (fromEncoding == null || toEncoding == null
				|| (fromEncoding.equals(toEncoding))) {
			if (textInUTF8 && !fromEncoding.equals(UTF8))
				for (Entity e : entities)
					e.text = reEncode(e.text, fromEncoding, UTF8);
		} else {
			for (Entity e : entities) {
				if (e.start != 0) {
					String s = text.substring(0, e.start);
					s = reEncode(s, fromEncoding, toEncoding);
					e.start = s.length();
				}
				String s = text.substring(0, e.end);
				s = reEncode(s, fromEncoding, toEncoding);
				e.end = s.length();
				e.text = textInUTF8 ? reEncode(e.text, fromEncoding, UTF8)
						: reEncode(e.text, fromEncoding, toEncoding);
			}
		}
	}

	public static void correctOffsetsInPlace(List<Entity> entities, String text,
			String fromEncoding, String toEncoding)
			throws UnsupportedEncodingException {
		correctOffsetsInPlace(entities, text, fromEncoding, toEncoding, true);
	}
}
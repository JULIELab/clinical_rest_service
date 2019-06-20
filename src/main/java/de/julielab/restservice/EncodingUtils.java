package de.julielab.restservice;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import de.julielab.restservice.annotation.Entity;

public class EncodingUtils {

	private static final String UTF8 = StandardCharsets.UTF_8.name();

	public static void correctOffsetsInPlace(final List<Entity> entities,
			final String text, final String fromEncoding,
			final String toEncoding) throws UnsupportedEncodingException {
		correctOffsetsInPlace(entities, text, fromEncoding, toEncoding, true);
	}

	static void correctOffsetsInPlace(final List<Entity> entities,
			final String text, final String fromEncoding,
			final String toEncoding, final boolean textInUTF8)
			throws UnsupportedEncodingException {
		if ((fromEncoding == null) || (toEncoding == null)
				|| (fromEncoding.equals(toEncoding))) {
			if (textInUTF8 && !fromEncoding.equals(UTF8))
				for (final Entity e : entities) {
					e.type = reEncode(e.type, fromEncoding, UTF8);
					e.text = reEncode(e.text, fromEncoding, UTF8);
				}
		} else
			for (final Entity e : entities) {
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
				e.type = textInUTF8 ? reEncode(e.type, fromEncoding, UTF8)
						: reEncode(e.type, fromEncoding, toEncoding);
			}
	}

	public static String reEncode(final String text, final String fromEncoding,
			final String toEncoding) throws UnsupportedEncodingException {
		if ((fromEncoding == null) || (toEncoding == null)
				|| (fromEncoding.equals(toEncoding)))
			return text;
		return new String(text.getBytes(fromEncoding), toEncoding);
	}
}
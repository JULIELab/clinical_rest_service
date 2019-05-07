import java.io.UnsupportedEncodingException;
import java.util.List;

import annotation.Entity;

public class EncodingUtils {
	static String reEncode(String text, String fromEncoding, String toEncoding)
			throws UnsupportedEncodingException {
		if (fromEncoding == null || toEncoding == null
				|| (fromEncoding.equals(toEncoding)))
			return text;
		return new String(text.getBytes(fromEncoding), toEncoding);
	}

	static void correctOffsetsInPlace(List<Entity> entities, String text,
			String fromEncoding, String toEncoding)
			throws UnsupportedEncodingException {
		if (fromEncoding == null || toEncoding == null
				|| (fromEncoding.equals(toEncoding)))
			return; //nothing to do
		
		for (Entity e : entities) {
			if (e.start != 0) {
				String s = text.substring(0, e.start);
				s = reEncode(s, fromEncoding, toEncoding);
				e.start = s.length();
			}
			String s = text.substring(0, e.end);
			s = reEncode(s, fromEncoding, toEncoding);
			e.end = s.length();
			e.text = reEncode(e.text, fromEncoding, toEncoding);
		}

	}
}

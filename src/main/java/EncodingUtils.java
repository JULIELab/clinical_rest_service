import java.io.UnsupportedEncodingException;

public class EncodingUtils {
	static String reEncode(String text, String fromEncoding, String toEncoding)
			throws UnsupportedEncodingException {
		if (fromEncoding == null || toEncoding == null
				|| (fromEncoding.equals(toEncoding)))
			return text;
		return new String(text.getBytes(fromEncoding), toEncoding);
	}
}

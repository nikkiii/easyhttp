package org.nikkii.http.data;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * A utility class for parsing and construction of Query strings/URL Encoded Data
 *
 * @author Nikki
 */
public class QueryString {
	/**
	 * Implode a map of key -> value pairs to a URL safe string
	 *
	 * @param values
	 *            The values to implode
	 * @return The imploded string
	 * @throws IOException
	 *             If an error occurred while encoding any values.
	 */
	public static String implode(Map<String, Object> values) throws IOException {
		StringBuilder builder = new StringBuilder();
		Iterator<Map.Entry<String, Object>> iterator = values.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			builder.append(entry.getKey());

			if (entry.getValue() != null) {
				builder.append("=").append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			}

			if (iterator.hasNext()) {
				builder.append("&");
			}
		}
		return builder.toString();
	}

	/**
	 * Parse an http query string
	 * @param string
	 * 			The string to parse
	 * @return
	 * 			The parsed string in a map.
	 */
	public static RequestData parse(String string) {
		RequestData values = new RequestData();
		String[] split = string.split("&");

		for(String s : split) {
			if(s.indexOf('=') != -1) {
				values.put(s.substring(0, s.indexOf('=')), s.substring(s.indexOf('=')+1));
			} else {
				values.put(s, null);
			}
		}

		return values;
	}
}

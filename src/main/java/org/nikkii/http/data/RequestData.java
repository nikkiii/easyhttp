package org.nikkii.http.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A simple wrapper for a Map which contains a QueryString or POST data
 *
 * @author Nikki
 *
 */
public class RequestData {

	/**
	 * The data map used to store values
	 */
	private Map<String, Object> data = new HashMap<String, Object>();

	/**
	 * Set a key to a value
	 * @param key
	 * 			The key to set
	 * @param value
	 * 			The value to set
	 * @return
	 * 			The RequestData instance for chaining
	 */
	public RequestData put(String key, Object value) {
		data.put(key, value);
		return this;
	}

	/**
	 * Get a value
	 * @param key
	 * 			The key to get the value for
	 * @return
	 * 			The value, or null if not found
	 */
	public Object get(String key) {
		return data.get(key);
	}


	public boolean isEmpty() {
		return data.isEmpty();
	}

	public Set<Map.Entry<String, Object>> entrySet() {
		return data.entrySet();
	}

	/**
	 * Transform this data into a URLEncoded string
	 * @return
	 * 			The URL Encoded String
	 * @throws IOException
	 * 			If an encoding error occurs
	 */
	public String toURLEncodedString() throws IOException {
		return QueryString.implode(data);
	}
}
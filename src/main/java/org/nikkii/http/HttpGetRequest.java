package org.nikkii.http;

import org.nikkii.http.data.QueryString;
import org.nikkii.http.data.RequestData;

import java.io.IOException;
import java.util.Map;

/**
 * An HttpRequest representing a standard GET request.
 * This does not need any special treatment except for query parameters, which it adds on to
 *
 * @author Nikki
 */
public class HttpGetRequest extends HttpRequest {

	public HttpGetRequest(String url) throws IOException {
		super(url);
	}

	@Override
	public void execute() throws IOException {
		// GET only needs to add headers and possibly a query string
		if (!parameters.isEmpty()) {
			String newUrl = url.toString();
			int firstIdx = newUrl.indexOf('?');
			if (firstIdx != -1) {
				RequestData query = QueryString.parse(newUrl.substring(firstIdx + 1));
				for (Map.Entry<String, Object> e : parameters.entrySet()) {
					query.put(e.getKey(), e.getValue().toString());
				}
				newUrl = newUrl.substring(0, firstIdx);
				newUrl += '?' + query.toURLEncodedString();
			}

			url = newUrl;
		}
		openConnection();
	}
}

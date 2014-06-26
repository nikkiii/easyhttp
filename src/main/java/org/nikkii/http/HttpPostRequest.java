package org.nikkii.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An HttpRequest for a POST request (using form-urlencoded)
 *
 * @author Nikki
 */
public class HttpPostRequest extends HttpRequest {

	public HttpPostRequest(String url) {
		super(url);
	}

	@Override
	public void execute() throws IOException {
		openConnection();

		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		try (Writer writer = new OutputStreamWriter(connection.getOutputStream())) {
			writer.write(parameters.toURLEncodedString());
		}
	}
}

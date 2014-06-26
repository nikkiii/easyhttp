package org.nikkii.http.multipart;

import org.nikkii.http.HttpRequest;
import org.nikkii.http.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Random;

/**
 * An HttpRequest for multipart/form-data requests
 *
 * @author Nikki
 */
public class HttpMultipartPostRequest extends HttpRequest {
	/**
	 * Standard CLRF line ending
	 */
	private static final char[] CLRF = new char[] { '\r', '\n' };

	/**
	 * Double CLRF line ending
	 */
	private static final char[] DOUBLE_CLRF = new char[] { '\r', '\n', '\r', '\n' };

	/**
	 * Boundary start token
	 */
	private static final String BOUNDARY_START = "---------------------------HttpAPIFormBoundary";

	/**
	 * Construct a new Multipart POST request
	 * @param url The URL string
	 */
	public HttpMultipartPostRequest(String url) {
		super(url);
	}

	@Override
	public void execute() throws IOException {
		String boundary = BOUNDARY_START + new Random().nextLong();

		// Open the connection and set the correct header
		openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

		boundary = "--" + boundary;

		OutputStream os = connection.getOutputStream();

		try (Writer writer = new OutputStreamWriter(os)) {
			for (Map.Entry<String, Object> entry : parameters.entrySet()) {
				// Write the start of our request
				writer.write(boundary);
				writer.write(CLRF);
				writer.write("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"");

				Object object = entry.getValue();

				// Check if it's a file
				if (object instanceof MultipartFile) {
					MultipartFile file = (MultipartFile) object;

					writer.write("; filename=\"" + file.getName() + "\"");
					writer.write(CLRF);

					// Get the mime type
					String type = URLConnection.guessContentTypeFromName(file.getName());
					if (type == null) {
						type = "application/octet-stream";
					}

					// Write the mime type
					writer.write("Content-Type: ");
					writer.write(type);
					writer.write(DOUBLE_CLRF);

					writer.flush();

					try (InputStream input = file.getInputStream()) {
						byte[] buffer = new byte[1024];
						while (true) {
							int read = input.read(buffer, 0, buffer.length);
							if (read == -1) {
								break;
							}
							os.write(buffer, 0, read);
						}
						os.flush();
					}
				} else {
					// Write a newline before the content
					writer.write(DOUBLE_CLRF);
					// Write the data
					writer.write(entry.getValue().toString());
				}

				writer.write(CLRF);
			}

			// Set the final boundary
			boundary = boundary + "--";
			// Write a boundary to let the server know the previous content area is finished
			writer.write(boundary);
			// Write a final newline
			writer.write(CLRF);
		}
	}
}

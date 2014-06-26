package org.nikkii.http;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.nikkii.http.data.RequestData;
import org.nikkii.http.multipart.HttpMultipartPostRequest;
import org.nikkii.http.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * JUnit Testing for the API
 * Note: The URLs used here are for testing only, please don't abuse them!
 *
 * @author Nikki
 */
public class HttpTest {

	@Test
	public void testGetRequest() throws IOException {
		try (HttpRequest request = new HttpGetRequest("http://google.com/")) {
			assertEquals(200, request.getResponseCode());
		}
	}

	@Test
	public void testRequestData() throws IOException {
		String agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36";
		try (HttpRequest request = new HttpGetRequest("http://nikkii.us/httpapi/useragent.php")) {
			request.setUserAgent(agent);

			assertEquals(agent, request.getResponseBody().trim());
		}
	}

	@Test
	public void testPostRequest() throws IOException {
		try (HttpRequest request = new HttpPostRequest("http://nikkii.us/httpapi/post.php")) {
			RequestData data = new RequestData();
			data.put("hello", "world");
			request.setParameters(data);

			assertEquals(200, request.getResponseCode());

			assertEquals(data.toURLEncodedString(), request.getResponseBody().trim());
		}
	}

	@Test
	public void testMultipartPostRequest() throws IOException {
		try (HttpRequest request = new HttpMultipartPostRequest("http://nikkii.us/httpapi/multipartpost.php")) {
			request.addParameter("file", new MultipartFile("test.txt", new ByteArrayInputStream(new String("hello").getBytes())));

			assertEquals("hello", request.getResponseBody().trim());
		}
	}
}

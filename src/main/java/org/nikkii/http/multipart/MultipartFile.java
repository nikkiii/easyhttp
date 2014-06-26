package org.nikkii.http.multipart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A Multipart Form File
 *
 * @author Nikki
 */
public class MultipartFile {

	/**
	 * The file name
	 */
	private String name;

	/**
	 * The input stream to pull data from
	 */
	private InputStream inputStream;

	/**
	 * Construct a new file object from an existing File instance
	 * @param file The file to use
	 * @throws IOException If an error occurred opening the file for read
	 */
	public MultipartFile(File file) throws IOException {
		this(file.getName(), new FileInputStream(file));
	}

	/**
	 * Construct a new file object from a name and InputStream
	 * Note: The InputStream will be automatically closed after the upload is done.
	 * @param name The file name
	 * @param inputStream The InputStream containing the file data
	 */
	public MultipartFile(String name, InputStream inputStream) {
		this.name = name;
		this.inputStream = inputStream;
	}

	/**
	 * Get the file name
	 * @return The file's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the data InputStream
	 * @return The InputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
}

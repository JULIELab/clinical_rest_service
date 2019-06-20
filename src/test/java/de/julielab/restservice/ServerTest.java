package de.julielab.restservice;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import de.julielab.restservice.annotation.Entity;
import spark.Spark;

public class ServerTest {
	public static final String TEST_MODELS = "src/test/resources/models";
	private static final String TEST_INPUT = "src/test/resources/test.txt";
	private static final int TEST_PORT = 4567;
	private static final String BASE_URL = "http://localhost:" + TEST_PORT;
	private static final String ENTITY_URL = BASE_URL + "/entities";
	static HttpClient httpClient = new HttpClient();

	@AfterClass
	public static void afterClass() throws Exception {
		Spark.stop();
		httpClient.stop();
	}

	@BeforeClass
	public static void beforeClass() throws Exception {
		Server.startServer(TEST_PORT, TEST_MODELS);
		httpClient.start();
	}

	@Test
	public void testAvailableEncodings() throws Exception {
		//plattform dependent, thus only checking if API works and not all contents
		final ContentResponse response = httpClient
				.GET(BASE_URL + "/supported-encodings");
		assertTrue(response.getContentAsString().contains("UTF-8"));
	}

	@Test
	public void testBadEncodingResponse() throws Exception {
		final Request request = httpClient.POST(ENTITY_URL);
		request.file(Paths.get(TEST_INPUT));
		request.header(Main.HEADER_CHAR_SET, "käsekuchen");
		final ContentResponse response = request.send();
		assertEquals(406, response.getStatus());
		assertEquals("Not Acceptable", response.getReason());
	}

	@Test
	public void testGoodEncodingResponse() throws Exception {
		final Request request = httpClient.POST(ENTITY_URL);
		request.file(Paths.get(TEST_INPUT));
		request.header(Main.HEADER_CHAR_SET, "UTF-8");
		ContentResponse response = request.send();

		request.header(Main.HEADER_CHAR_SET, "windows-1251");
		response = request.send();
		assertNotEquals(406, response.getStatus());
	}

	@Test
	public void testWholeServer() throws Exception {
		final Request request = httpClient.POST(ENTITY_URL);
		request.file(Paths.get(TEST_INPUT));
		final ContentResponse response = request.send();
		final String responseString = response.getContentAsString();
		final Entity[] expectedEntities = new Entity[] {
				new Entity("gene-protein", 0, 3, "IL2"),
				new Entity("gene-protein", 31, 34, "IL2") };
		final Entity[] actualEntities = new Gson().fromJson(responseString,
				Entity[].class);
		assertArrayEquals(expectedEntities, actualEntities);
	}
}

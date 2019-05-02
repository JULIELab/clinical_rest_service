import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import annotation.Entity;
import spark.Spark;


public class MainTest {
	private static final String TEST_INPUT = "src/test/resources/test.json";
	private static final String BASE_URL = "http://localhost:4567";
	private static final String ENTITY_URL = BASE_URL+"/entities";
	static HttpClient httpClient = new HttpClient();

	@BeforeClass
	public static void beforeClass() throws Exception {
		new Main().routing();
		httpClient.start();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		Spark.stop();
		httpClient.stop();
	}

	@Test
	public void test() throws Exception {
		Request request = httpClient.POST(ENTITY_URL);
		request.file(Paths.get(TEST_INPUT));
		ContentResponse response = request.send();
		String responseString = response.getContentAsString();
		Entity[] expectedEntities = new Entity[] {
				new Entity("Hase", 0, 666, "fooo") };
		Entity[] actualEntities = new Gson().fromJson(responseString,
				Entity[].class);
		assertArrayEquals(expectedEntities, actualEntities);
	}

	@Test
	public void testBadEncodingResponse() throws Exception {
		Request request = httpClient.POST(ENTITY_URL);
		request.file(Paths.get(TEST_INPUT));
		request.header(Main.HEADER_CHAR_SET, "käsekuchen");
		ContentResponse response = request.send();
		assertEquals(406, response.getStatus());
		assertEquals("Not Acceptable", response.getReason());
	}

	@Test
	public void testGoodEncodingResponse() throws Exception {
		Request request = httpClient.POST(ENTITY_URL);
		request.file(Paths.get(TEST_INPUT));
		request.header(Main.HEADER_CHAR_SET, "UTF-8");
		ContentResponse response = request.send();
		assertEquals(200, response.getStatus());
		
		request.header(Main.HEADER_CHAR_SET, "windows-1251");
		response = request.send();
		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void testAvailableEncodings() throws Exception{
		//plattform dependent, thus only checking if API works and not all contents
		ContentResponse response = httpClient.GET(BASE_URL+"/supported-encodings");
		assertTrue(response.getContentAsString().contains("UTF-8"));
	}
}

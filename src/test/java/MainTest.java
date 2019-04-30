import static org.junit.Assert.assertArrayEquals;

import java.nio.file.Paths;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import spark.Spark;

public class MainTest {
	static HttpClient httpClient = new HttpClient();

	@BeforeClass
	public static void beforeClass() throws Exception {
		Main.main(null);
		httpClient.start();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		Spark.stop();
		httpClient.stop();
	}

	@Test
	public void test() throws Exception {
		Request request = httpClient.POST("http://localhost:4567/entities");
		request.file(Paths.get("src/test/resources/test.json"));
		ContentResponse response = request.send();
		String responseString = response.getContentAsString();
		Entity[] expectedEntities = new Entity[] {
				new Entity("Hase", 0, 666, "fooo") };
		Entity[] actualEntities = new Gson().fromJson(responseString,
				Entity[].class);
		assertArrayEquals(expectedEntities, actualEntities);
	}
}

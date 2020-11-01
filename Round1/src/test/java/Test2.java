import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Test2 extends Utils {

//	WebDriver driver;

	String URL = "https://192.168.104.19:7443/avayascb-widget-dataprocessor/api/avayascb/link-reference-widgets";

	@Test(enabled = false)
	public void t1() {

		RestAssured.baseURI = "https://192.168.104.19:7443";
		RestAssured.useRelaxedHTTPSValidation();
		Response res = given().auth().preemptive().basic("dialler_manager_hk", "Avaya123!")
				.header("Content-Type", "application/json").header("tenantId", "HKTS")
				.body("{\"linkDisplayName\":\"kpmg12\",\"linkPurpose\":\"testing\",\"linkUrl\":\"www.kpmg.com\",\"linkDescription\":\"kpmg Link\",\"isLinkACtivated\":true,\"linkCreatedBy\":1,\"linkWithAttachment\":false,\"tenantId\":\"HKTS\"}")
				.log().all().
				// body(postdata).
				when().post("/avayascb-widget-dataprocessor/api/link-reference-widgets").then().assertThat()
				.statusCode(201).extract().response();
	}

	@Test(enabled = false)
	public void getRequest() {
		RestAssured.baseURI = "https://192.168.104.19:7443";
		RestAssured.useRelaxedHTTPSValidation();
		Response res = given().auth().preemptive().basic("dialler_manager_hk", "Avaya123!").
				header("tenantId", "HKTS").header("agentId", "34009").when()
				.get("/avayascb-widget-dataprocessor/api/avayascb/link-reference-widgets").then().assertThat()
				.statusCode(200).extract().response();

		System.out.println(res.asString());

	}

	@Test(enabled = false)
	public void webtable() {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size=1400,800");
		options.addArguments("test-type");
		options.addArguments("ignore-certificate-errors");
		options.setAcceptInsecureCerts(true);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("https://192.168.104.19:7443/avayascb-widget-dataprocessor/#");
		driver.findElement(By.id("login")).click();
		driver.findElement(By.id("username")).sendKeys("dialler_manager_hk");
		driver.findElement(By.id("password")).sendKeys("Avaya123!");
		driver.findElement(By.xpath("//button[text()='Sign in']")).click();
		driver.findElement(By.xpath("//*[@class='system-list']//following::span[text()='Access Rights Groups']"))
				.click();
		Utils.view("agent 1019");

	}

	@Test
	public void getRequestclient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		// Basic Auth
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("dialler_manager_hk", "Avaya123!"));

		// Accept SSL certificate
		TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();

		BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(
				socketFactoryRegistry);
//		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
//				.setConnectionManager(connectionManager).build();

//		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(URL); // http get request

//		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
//				.setConnectionManager(connectionManager).build();
		CloseableHttpClient httpClient1 = HttpClientBuilder.create().setSSLSocketFactory(sslsf)
				.setDefaultCredentialsProvider(provider).setConnectionManager(connectionManager).build();

		// Headers
		httpget.addHeader("tenantId", "HKTS");
		httpget.addHeader("agentId", "34009");
		try {
			CloseableHttpResponse closebaleHttpResponse = httpClient1.execute(httpget);
			System.out.println(closebaleHttpResponse.getStatusLine());
			System.out.println(closebaleHttpResponse.getEntity().toString());
			String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
			System.out.println(responseString);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

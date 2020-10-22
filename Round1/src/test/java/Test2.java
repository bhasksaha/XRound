import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Test2 {
	
	@Test
	public void t1() {
		
		RestAssured.baseURI = "https://192.168.104.19:7443";
		RestAssured.useRelaxedHTTPSValidation();
		Response res = 
				given().
				auth(). preemptive().basic("dialler_manager_hk", "Avaya123!").
				header("Content-Type", "application/json").header("tenantId", "HKTS").
				body("{\"linkDisplayName\":\"kpmg12\",\"linkPurpose\":\"testing\",\"linkUrl\":\"www.kpmg.com\",\"linkDescription\":\"kpmg Link\",\"isLinkACtivated\":true,\"linkCreatedBy\":1,\"linkWithAttachment\":false,\"tenantId\":\"HKTS\"}").log().all().
				//body(postdata).
				when().
				post("/avayascb-widget-dataprocessor/api/link-reference-widgets").
				then().assertThat().statusCode(201).
				extract()
				.response();
	}
	
	@Test
	public void getRequest() {
		RestAssured.baseURI = "https://192.168.104.19:7443";
		RestAssured.useRelaxedHTTPSValidation();
		Response res = given().header("tenantId", "HKTS").header("agentId", "34009").when()
				.get("/avayascb-widget-dataprocessor/api/avayascb/link-reference-widgets").then().assertThat().statusCode(200).extract()
				.response();


		System.out.println(res.asString());

	}

}

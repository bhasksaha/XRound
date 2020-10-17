import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import Request.POJO.request;
import Response.POJO.response;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matcher.*;
import static io.restassured.RestAssured.*;

public class Test1 {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://reqres.in/";

		request re = new request();
		re.setName("morpheus");
		re.setJob("leader");

//		response res=new response();

		response res = given().header("content-type", "application/json").body(re).when().post("api/users").then().log()
				.all().extract().as(Response.POJO.response.class);

		System.out.println(res.getId());
		System.out.println(res.getJob());
		System.out.println(res.getName());
		System.out.println(res.getCreatedAt());

	}
	
	
//	@Test(invocationCount=2)
//	public void testngtest() {
//		
//		RestAssured.baseURI = "https://reqres.in/";
//
//		request re = new request();
//		re.setName("morpheus");
//		re.setJob("leader");
//
////		response res=new response();
//
//		response res = given().header("content-type", "application/json").body(re).when().post("api/users").then().log()
//				.all().extract().as(Response.POJO.response.class);
//
//		System.out.println(res.getId());
//		System.out.println(res.getJob());
//		System.out.println(res.getName());
//		System.out.println(res.getCreatedAt());
//	}

}

package restAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class testCasesForAPItesting {
	@Test
	public void getUser() {
		// https://reqres.in/api/users/2
		RestAssured.baseURI = "https://reqres.in/api";
		Response response = given().when().get("users/2");
		// first assertion
		response.then().statusCode(200);

		// second assertion
		response.then().assertThat().body("data.id", equalTo(2))
		.body("data.email", equalTo("janet.weaver@reqres.in"))
		.body("data.first_name", equalTo("Janet")).body("data.last_name", equalTo("Weaver"))
		.body("data.avatar", equalTo("https://reqres.in/img/faces/2-image.jpg"));
		response.then().log().all();
	}

	@Test
	public void createUser() {
		RestAssured.baseURI = "https://reqres.in/api";
		// userdata
		String requestBody = "{\"name\": \"John Doe\", \"job\": \"Software Engineer\"}";
		Response response = given().contentType(ContentType.JSON)
				.body(requestBody).when().post("/users");

		// validation of status code
		response.then().statusCode(201);

		// validate the response
		response.then().assertThat().body("name", equalTo("John Doe")).body("job", equalTo("Software Engineer"));

	}

	@Test
	public void updateUser() {
		RestAssured.baseURI = "https://reqres.in/api";
		// userdata
		String requestBody = "{\"name\": \"Sonika Subedi\", \"job\": \"Software Engineer\"}";
		Response response = given().contentType(ContentType.JSON)
				.body(requestBody).when().put("users/2");

		// validation of status code
		response.then().statusCode(200);

		// validate the response
		response.then().assertThat().body("name", equalTo("Sonika Subedi")).body("job", equalTo("Software Engineer"));

	}

	@Test
	public void deleteUser() {
		RestAssured.baseURI = "https://reqres.in/api";
		Response response = given().when().delete("/users/2");
		response.then().statusCode(204);
	}

}

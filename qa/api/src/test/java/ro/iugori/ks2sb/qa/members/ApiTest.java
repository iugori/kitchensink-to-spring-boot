package ro.iugori.ks2sb.qa.members;

import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import ro.iugori.ks2sb.dto.Member;
import ro.iugori.ks2sb.qa.Setup;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    public static final String RESOURCE_URL = Setup.REST_URL + "/members";

    @Test
    void getAllMembers() {
        var rr = given().baseUri(Setup.BASE_URL)
                .when().get(RESOURCE_URL)
                .then().statusCode(200).
                body("", hasSize(greaterThan(0)));

        // this construct is redundant in this test
        // it is only placed as a reference
        var bodyStr = rr.extract().response().asString();
        var bodyJson = new JSONArray(bodyStr);
        assertThat(bodyJson.length()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void getJohn() {
        given().baseUri(Setup.BASE_URL)
                .when().get(RESOURCE_URL + "/0")
                .then().statusCode(200).
                body("name", equalTo("John Smith")).
                body("email", equalTo("john.smith@mailinator.com")).
                body("phoneNumber", equalTo("2125551212"));
    }

    @Test
    void getNobody() {
        given().baseUri(Setup.BASE_URL)
                .when().get(RESOURCE_URL + "/" + Long.MAX_VALUE)
                .then().statusCode(404);
    }

    @Test
    void registerNullMember() {
        var member = new Member();

        var validValues = List.of("must not be empty", "must not be null");

        given().baseUri(Setup.BASE_URL)
                .when().contentType("application/json").body(member).post(RESOURCE_URL)
                .then().statusCode(400).
                body("name", in(validValues)).
                body("email", in(validValues)).
                body("phoneNumber", in(validValues));
    }

    @Test
    void registerEmptyMember() {
        var member = Member.builder().name("").email("").phoneNumber("").build();

        given().baseUri(Setup.BASE_URL)
                .when().contentType("application/json").body(member).post(RESOURCE_URL)
                .then().statusCode(400).
                body("name", equalTo("size must be between 1 and 25")).
                body("email", equalTo("must not be empty")).
                body("phoneNumber", equalTo("numeric value out of bounds (<12 digits>.<0 digits> expected)"));
    }

    @Test
    void registerInvalidMember() {
        var member = Member.builder().name("1").email("1").phoneNumber("1").build();

        given().baseUri(Setup.BASE_URL)
                .when().contentType("application/json").body(member).post(RESOURCE_URL)
                .then().statusCode(400).
                body("name", equalTo("Must not contain numbers")).
                body("email", equalTo("must be a well-formed email address")).
                body("phoneNumber", equalTo("size must be between 10 and 12"));
    }

    @Test
    void registerDuplicateMember() {
        var member = Member.builder()
                .name("Jane Doe")
                .email("john.smith@mailinator.com")
                .phoneNumber("12345678910").build();

        given().baseUri(Setup.BASE_URL)
                .when().contentType("application/json").body(member).post(RESOURCE_URL)
                .then().statusCode(409).
                body("name", nullValue()).
                body("email", equalTo("Email taken")).
                body("phoneNumber", nullValue());
    }

    @Test
    void registerMember() {
        var faker = new Faker();

        var member = Member.builder()
                .name("Jane Doe")
                .email(faker.internet().emailAddress())
                .phoneNumber("12345678910").build();

        given().baseUri(Setup.BASE_URL)
                .when().contentType("application/json").body(member).post(RESOURCE_URL)
                .then().statusCode(200).
                body(blankString());

        given().baseUri(Setup.BASE_URL)
                .when().get(RESOURCE_URL)
                .then().statusCode(200).
                body("", hasItem(hasEntry("email", member.getEmail())));
    }

}

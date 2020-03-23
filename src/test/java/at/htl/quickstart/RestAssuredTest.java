package at.htl.quickstart;

import at.htl.quickstart.model.Movie;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class RestAssuredTest {

    private static RequestSpecification spec;

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:8080/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void test01_get() {

        Movie movie =
                when().get("/api/movie/find/1")
                        .then().statusCode(200)
                        .extract().as(Movie.class);

        List movieList =
                when().get("/api/movie/findAll")
                        .then().statusCode(200)
                        .extract().as(List.class);

        assertThat(movie.getTitle()).isEqualTo("Die Verurteilten");
        assertThat(movie.getGenre()).isEqualTo("Drama");
        assertThat(movieList.size()).isEqualTo(3);
    }
}

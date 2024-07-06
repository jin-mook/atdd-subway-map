package subway.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import subway.line.LineRequest;

public class LineAssuredTemplate {

    public static Response createLine(LineRequest lineRequest) {

        return RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(lineRequest)
                .when()
                .post("/lines");
    }

    public static Response updateLine() {

        return null;
    }
}

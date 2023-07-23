package subway.step;

import io.restassured.RestAssured;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class LineSectionStep {

  public static Map 지하철_구간_생성(Long id, Map<String, Object> 신규_구간_정보) {
    return RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam("lineId", id)
        .body(신규_구간_정보)
        .post("/lines/{lineId}/sections")
        .then().log().all()
        .assertThat()
        .statusCode(HttpStatus.CREATED.value())
        .extract()
        .jsonPath()
        .get("$");
  }

  public static Map 지하철_구간_생성에_실패한다(Long id, Map<String, Object> 신규_구간_정보) {
    return RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam("lineId", id)
        .body(신규_구간_정보)
        .post("/lines/{lineId}/sections")
        .then().log().all()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .extract()
        .jsonPath()
        .get("$");
  }

  public static List<Map> 지하철_구간_목록_조회(Long id) {
    return RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam("lineId", id)
        .get("/lines/{lineId}/sections")
        .then().log().all()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .jsonPath()
        .getList("$");
  }

  public static void 지하철_구간_제거(Long 노선_ID, Long 역_ID) {
    RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam("lineId", 노선_ID)
        .param("stationId", 역_ID)
        .delete("/lines/{lineId}/sections")
        .then().log().all()
        .assertThat()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }

  public static void 지하철_구간_제거_실패(Long 노선_ID, Long 역_ID) {
    RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam("lineId", 노선_ID)
        .param("stationId", 역_ID)
        .delete("/lines/{lineId}/sections")
        .then().log().all()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }
}

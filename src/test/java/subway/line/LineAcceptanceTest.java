package subway.line;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import subway.util.StationAssuredTemplate;

@DisplayName("지하철 노선 관리 기능")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LineAcceptanceTest {

    /**
     * Given 노선에 연결할 상행종점역, 하행종점역을 먼저 생성한다.
     * When 지하철 노선을 등록합니다. 이때 상행, 하행 종점역과 같이 등록한다.
     * Then 지하철 노선 목록을 요청할 때 생성한 노선 정보를 확인할 수 있습니다.
     */
    @DisplayName("지하철 노선을 등록하고 목록 조회를 하면 등록한 노선을 볼 수 있습니다.")
    @Test
    void createLine() {
        // given
        String upStation = "상행종점역";
        String downStation = "하행종점역";

        long upStationId = StationAssuredTemplate.createStation(upStation)
                .then()
                .extract().jsonPath().getLong("id");

        long downStationId = StationAssuredTemplate.createStation(downStation)
                .then()
                .extract().jsonPath().getLong("id");

        // when
        String lineName = "신분당선";
        String color = "bg-red-600";
        long distance = 10;

        LineRequest lineRequest = new LineRequest(lineName, color, upStationId, downStationId, distance);

        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(lineRequest)
                .when()
                .post("/lines")
                .then()
                .extract();

        // then
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(result.body().jsonPath().getString("name")).isEqualTo(lineName);
        Assertions.assertThat(result.body().jsonPath().getString("color")).isEqualTo(color);
        Assertions.assertThat(result.body().jsonPath().getList("stations")).hasSize(2)
                .extracting("name")
                .containsExactly(upStation, downStation);
    }
}

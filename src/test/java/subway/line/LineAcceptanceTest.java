package subway.line;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import subway.util.LineAssuredTemplate;
import subway.util.StationAssuredTemplate;

import java.util.List;

@Sql(scripts = {"/delete-data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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

        ExtractableResponse<Response> result = LineAssuredTemplate.createLine(lineRequest)
                .then()
                .extract();

        // then
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(result.body().jsonPath().getString("name")).isEqualTo(lineName);
        Assertions.assertThat(result.body().jsonPath().getString("color")).isEqualTo(color);
        Assertions.assertThat(result.body().jsonPath().getList("stations")).hasSize(2)
                .extracting("name")
                .contains(upStation, downStation);
    }

    /**
     * Given 노선에 2개의 지하철을 등록한다.
     * When 관리자가 노선 목록을 조회한다.
     * Then 모든 지하철 노선 목록이 반환된다.
     */
    @Test
    @DisplayName("지하철 노선 목록을 조회하면 모든 지하철 노선 목록이 반환된다.")
    void showAllLines() {
        // given
        String upStation = "상행종점역";
        String downStation = "하행종점역";
        String newStation = "새로운지하쳘역";

        long upStationId = StationAssuredTemplate.createStation(upStation)
                .then()
                .extract().jsonPath().getLong("id");

        long downStationId = StationAssuredTemplate.createStation(downStation)
                .then()
                .extract().jsonPath().getLong("id");

        long newStationId = StationAssuredTemplate.createStation(newStation)
                .then()
                .extract().jsonPath().getLong("id");

        LineAssuredTemplate.createLine(new LineRequest("신분당선", "bg-red-600", upStationId, downStationId, 10));
        LineAssuredTemplate.createLine(new LineRequest("2호선", "bg-green-600", upStationId, newStationId, 20));

        // when
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/lines")
                .then().log().all()
                .extract();

        // then
        List<LineResponse> responseData = result.jsonPath().getList(".", LineResponse.class);
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(responseData).hasSize(2);
        Assertions.assertThat(responseData).extracting("name")
                .contains("신분당선", "2호선");

        Assertions.assertThat(responseData.get(0).getStations()).hasSize(2)
                .extracting("id", "name")
                .contains(
                        Tuple.tuple(upStationId, upStation),
                        Tuple.tuple(downStationId, downStation)
                );

        Assertions.assertThat(responseData.get(1).getStations()).hasSize(2)
                .extracting("id", "name")
                .contains(
                        Tuple.tuple(upStationId, upStation),
                        Tuple.tuple(newStationId, newStation)
                );
    }

    /**
     * Given 지하철역과 지하철 노선을 등록합니다.
     * When 등록한 지하철 노선을 조회합니다.
     * Then 등록한 지하철 노선 정보를 응답받습니다.
     */
    @Test
    @DisplayName("특정 지하철 노선을 조회합니다.")
    void findLine() {
        // given
        String upStation = "상행종점역";
        String downStation = "하행종점역";

        long upStationId = StationAssuredTemplate.createStation(upStation)
                .then()
                .extract().jsonPath().getLong("id");

        long downStationId = StationAssuredTemplate.createStation(downStation)
                .then()
                .extract().jsonPath().getLong("id");

        String lineName = "신분당선";
        String color = "bg-red-600";
        long distance = 10;

        LineRequest lineRequest = new LineRequest(lineName, color, upStationId, downStationId, distance);
        long lineId = LineAssuredTemplate.createLine(lineRequest)
                .then().extract().jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("lineId", lineId)
                .when()
                .get("/lines/{lineId}")
                .then().log().all()
                .extract();

        // then
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(result.jsonPath().getString("name")).isEqualTo(lineName);
        Assertions.assertThat(result.jsonPath().getString("color")).isEqualTo(color);
        Assertions.assertThat(result.jsonPath().getList("stations")).hasSize(2)
                .extracting("name")
                .contains(upStation, downStation);
    }

    /**
     * Given 지하철 역과 지하철 노선을 생성합니다.
     * When 지하철 노선의 이름과 색 수정을 요청합니다.
     * Then 정상 처리 요청을 응답받습니다.
     */
    @Test
    @DisplayName("지하철 노선의 이름과 색 수정 요청을 하면 정상 응답을 받습니다.")
    void updateLine() {
        // given
        String upStation = "상행종점역";
        String downStation = "하행종점역";

        long upStationId = StationAssuredTemplate.createStation(upStation)
                .then()
                .extract().jsonPath().getLong("id");

        long downStationId = StationAssuredTemplate.createStation(downStation)
                .then()
                .extract().jsonPath().getLong("id");

        String lineName = "신분당선";
        String color = "bg-red-600";
        long distance = 10;

        LineRequest lineRequest = new LineRequest(lineName, color, upStationId, downStationId, distance);
        long lineId = LineAssuredTemplate.createLine(lineRequest)
                .then().extract().jsonPath().getLong("id");

        // when
        String updateLineName = "신분분당선";
        String updateColor = "bg-red-60000";

        UpdateLineRequest updateLineRequest = new UpdateLineRequest(updateLineName, updateColor);
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateLineRequest)
                .pathParam("lineId", lineId)
                .when()
                .put("/lines/{lineId}")
                .then().log().all()
                .extract();

        // then
        result.body().equals(null);
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}

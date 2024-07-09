package subway.section;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import subway.line.LineRequest;
import subway.line.LineStationsResponse;
import subway.station.StationFixtures;
import subway.util.LineAssuredTemplate;
import subway.util.StationAssuredTemplate;

@Sql(scripts = {"/delete-data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DisplayName("지하철 노선 구간 관리 기능")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SectionAcceptanceTest {

    /**
     * Given 노선에 구간이 하나 등록되어 있습니다.
     * When 해당 노선에 신규 구간을 추가합니다.
     * Then 신규 구간의 상행역이 기존 구간의 하행역과 같지 않아 에러 응답을 보냅니다.
     */
    @DisplayName("새로운 구간 등록 시 새 구간의 상행역이 기존 구간의 하행역과 같지 않다면 에러 응답을 보냅니다.")
    @Test
    void invalidSection() {
        // given
        long upStationId = StationAssuredTemplate.createStation(StationFixtures.UP_STATION.getName())
                .then().extract().jsonPath().getLong("id");
        long downStationId = StationAssuredTemplate.createStation(StationFixtures.DOWN_STATION.getName())
                .then().extract().jsonPath().getLong("id");
        long newUpStationId = StationAssuredTemplate.createStation(StationFixtures.NEW_UP_STATION.getName())
                .then().extract().jsonPath().getLong("id");
        long newDownStationId = StationAssuredTemplate.createStation(StationFixtures.NEW_DOWN_STATION.getName())
                .then().extract().jsonPath().getLong("id");

        long lineId = LineAssuredTemplate.createLine(new LineRequest("신분당선", "red", upStationId, downStationId, 10L))
                .then().extract().jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SectionRequest(newUpStationId, newDownStationId, 10L))
                .pathParam("lineId", lineId)
                .when()
                .post("/lines/{lineId}/sections")
                .then().log().all()
                .extract();

        // then
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(result.jsonPath().getString("message")).isEqualTo("구간 정보가 올바르지 않습니다.");
    }

    /**
     * Given 노선에 구간이 하나 등록되어 있습니다.
     * When 해당 노선에 신규 구간을 추가합니다.
     * Then 신규 구간의 하행역이 기존에 구역에 존재하기 때문에 에러 응답을 보냅니다.
     */
    @DisplayName("새로운 구간 등록 시 새 구간의 하행역이 기존 구간에 존재하는 역이라면 에러 응답을 보냅니다.")
    @Test
    void existDownStation() {
        // given
        long upStationId = StationAssuredTemplate.createStation(StationFixtures.UP_STATION.getName())
                .then().extract().jsonPath().getLong("id");
        long downStationId = StationAssuredTemplate.createStation(StationFixtures.DOWN_STATION.getName())
                .then().extract().jsonPath().getLong("id");

        long lineId = LineAssuredTemplate.createLine(new LineRequest("신분당선", "red", upStationId, downStationId, 10L))
                .then().extract().jsonPath().getLong("id");
        // when
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SectionRequest(downStationId, upStationId, 10L))
                .pathParam("lineId", lineId)
                .when()
                .post("/lines/{lineId}/sections")
                .then().log().all()
                .extract();
        // then
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(result.jsonPath().getString("message")).isEqualTo("구간 정보가 올바르지 않습니다.");
    }

    /**
     * Given 노선에 구간이 하나 등록되어 있습니다.
     * When 해당 노선에 신규 구간을 추가합니다.
     * Then 추가된 신규 구간이 정상적으로 응답으로 보입니다.
     */
    @DisplayName("새로운 구간을 등록합니다.")
    @Test
    void addSection() {
        // given
        long upStationId = StationAssuredTemplate.createStation(StationFixtures.UP_STATION.getName())
                .then().extract().jsonPath().getLong("id");
        long downStationId = StationAssuredTemplate.createStation(StationFixtures.DOWN_STATION.getName())
                .then().extract().jsonPath().getLong("id");
        long newDownStationId = StationAssuredTemplate.createStation(StationFixtures.NEW_DOWN_STATION.getName())
                .then().extract().jsonPath().getLong("id");

        long lineId = LineAssuredTemplate.createLine(new LineRequest("신분당선", "red", upStationId, downStationId, 10L))
                .then().extract().jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new SectionRequest(downStationId, newDownStationId, 10L))
                .pathParam("lineId", lineId)
                .when()
                .post("/lines/{lineId}/sections")
                .then().log().all()
                .extract();

        // then
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(result.jsonPath().getList("stations", LineStationsResponse.class)).hasSize(4)
                .extracting("name")
                .contains(
                        StationFixtures.UP_STATION.getName(),
                        StationFixtures.DOWN_STATION.getName(),
                        StationFixtures.DOWN_STATION.getName(),
                        StationFixtures.NEW_DOWN_STATION.getName()
                );
    }
}

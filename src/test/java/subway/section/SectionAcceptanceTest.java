package subway.section;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import subway.line.LineRequest;
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
//    @DisplayName("새로운 구간 등록 시 새 구간의 상행역이 기존 구간의 하행역과 같지 않다면 에러 응답을 보냅니다.")
//    @Test
//    void invalidSection() {
//        // given
//        long upStationId = StationAssuredTemplate.createStation(StationFixtures.UP_STATION.getName())
//                .then().extract().jsonPath().getLong("id");
//        long downStationId = StationAssuredTemplate.createStation(StationFixtures.DOWN_STATION.getName())
//                .then().extract().jsonPath().getLong("id");
//
//        LineAssuredTemplate.createLine(new LineRequest("신분당선", "red", upStationId, downStationId, 10L))
//        // when
//
//        // then
//
//    }
}

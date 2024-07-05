package subway.line;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.Station;

import java.util.List;

class LineTest {

    @DisplayName("지하철 노선에 역을 추가하면 역 entity 에도 지하철 노선이 정상적으로 추가되어야 합니다.")
    @Test
    void addStation() {
        // given
        Station upStation = new Station("강남역");
        Station downStation = new Station("양재역");

        Line line = Line.createLine("신분당선", "red");

        // when
        line.addStation(List.of(upStation, downStation));

        // then
        Assertions.assertThat(line.getStations()).extracting("name")
                .containsExactly("강남역", "양재역");
        Assertions.assertThat(upStation.getLine().getName()).isEqualTo("신분당선");
        Assertions.assertThat(downStation.getLine().getName()).isEqualTo("신분당선");
    }

}
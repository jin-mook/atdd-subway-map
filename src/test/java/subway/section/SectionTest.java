package subway.section;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.exception.NotSameUpAndDownStationException;
import subway.station.StationFixtures;

class SectionTest {

    @Test
    @DisplayName("구간의 길이는 0이하 값이 올 수 없습니다.")
    void zeroDistance() {
        // given
        // when
        // then
        Assertions.assertThatThrownBy(() -> new Section(StationFixtures.UP_STATION, StationFixtures.DOWN_STATION, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("distance 값이 올바르지 않습니다.");
    }

    @DisplayName("상행역과 하행역은 같으면 안됩니다.")
    @Test
    void upDownDiff() {
        // given
        // when
        // then
        Assertions.assertThatThrownBy(() -> new Section(StationFixtures.UP_STATION, StationFixtures.UP_STATION, 10L))
                .isInstanceOf(NotSameUpAndDownStationException.class);
    }
}
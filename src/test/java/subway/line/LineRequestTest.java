package subway.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LineRequestTest {

    private String lineName;
    private String lineColor;

    @BeforeEach
    void setUp() {
        this.lineName = "test";
        this.lineColor = "red";
    }

    @DisplayName("upStationId 값이 0보다 작으면 안됩니다.")
    @Test
    void upStationId() {
        // given
        long upStationId = 0;
        long downStationId = 1;
        long distance = 10;

        // when
        // then
        assertThatThrownBy(() -> new LineRequest(lineName, lineColor, upStationId, downStationId, distance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("stationId 값이 올바르지 않습니다.");
    }

    @DisplayName("downStationId 값이 0보다 작으면 안됩니다.")
    @Test
    void downStationId() {
        // given
        long upStationId = 1;
        long downStationId = 0;
        long distance = 10;

        // when
        // then
        assertThatThrownBy(() -> new LineRequest(lineName, lineColor, upStationId, downStationId, distance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("stationId 값이 올바르지 않습니다.");
    }

    @DisplayName("distance 값이 0보다 작으면 안됩니다.")
    @Test
    void distance() {
        // given
        long upStationId = 1;
        long downStationId = 2;
        long distance = 0;

        // when
        // then
        assertThatThrownBy(() -> new LineRequest(lineName, lineColor, upStationId, downStationId, distance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("distance 값이 올바르지 않습니다.");
    }
}
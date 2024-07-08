package subway.line;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LineTest {

    @Test
    @DisplayName("노선의 이름을 수정합니다.")
    void updateLineName() {
        // given
        Line line = Line.createLine("기존 이름", "기존 색");
        String newName = "새로운 이름";
        // when
        line.updateName(newName);
        // then
        Assertions.assertThat(line.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("노선의 색을 수정합니다.")
    void updateLineColor() {
        // given
        Line line = Line.createLine("기존 이름", "기존 색");
        String newColor = "새로운 색";
        // when
        line.updateColor(newColor);
        // then
        Assertions.assertThat(line.getColor()).isEqualTo(newColor);
    }
}
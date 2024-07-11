package subway.line;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.section.SectionFixtures;

class LineTest {

    private LineInfoDto lineInfoDto;

    @BeforeEach
    void setUp() {
        lineInfoDto = new LineInfoDto("기존 이름", "기존 색", 10L);
    }

    @Test
    @DisplayName("노선의 이름을 수정합니다.")
    void updateLineName() {
        // given
        Line line = new Line("기존 이름", "기존 색", SectionFixtures.FIRST_SECTION);
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
        Line line = new Line("기존 이름", "기존 색", SectionFixtures.FIRST_SECTION);
        String newColor = "새로운 색";
        // when
        line.updateColor(newColor);
        // then
        Assertions.assertThat(line.getColor()).isEqualTo(newColor);
    }
}
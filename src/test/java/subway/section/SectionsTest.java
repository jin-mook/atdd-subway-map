package subway.section;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.station.StationFixtures;

class SectionsTest {

    @DisplayName("새로운 구역을 추가할 때 새로운 구역의 상행역과 기존 마지막 구역의 하행역이 다르면 에러가 발생합니다.")
    @Test
    void noSameStation() {
        // given
        Sections sections = new Sections();
        Section section = new Section(StationFixtures.UP_STATION, StationFixtures.DOWN_STATION, 10L);
        sections.addSection(section);

        Section newSection = new Section(StationFixtures.NEW_UP_STATION, StationFixtures.NEW_DOWN_STATION, 10L);
        // when
        // then
        Assertions.assertThatThrownBy(() -> sections.addSection(newSection))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("새로운 구역의 하행역이 기존 구역에 존재한다면 에러가 발생합니다.")
    @Test
    void hasDownStation() {
        // given
        Sections sections = new Sections();
        Section firstSection = new Section(StationFixtures.UP_STATION, StationFixtures.DOWN_STATION, 10L);

        sections.addSection(firstSection);

        Section secondSection = new Section(StationFixtures.DOWN_STATION, StationFixtures.NEW_DOWN_STATION, 10L);

        sections.addSection(secondSection);

        // when
        Section thirdSection = new Section(StationFixtures.NEW_DOWN_STATION, StationFixtures.UP_STATION, 10L);

        // then
        Assertions.assertThatThrownBy(() -> sections.addSection(thirdSection))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("기존 구역이 존재하지 않으면 신규 구역을 추가합니다.")
    @Test
    void sectionEmpty() {
        // given
        Sections sections = new Sections();
        Section section = new Section(StationFixtures.UP_STATION, StationFixtures.DOWN_STATION, 10L);
        // when
        sections.addSection(section);

        // then
        Assertions.assertThat(sections.getSections()).hasSize(1);
    }

    @DisplayName("기존 구역의 하행역과 신규 구역의 상행역이 동일하고 신규 구역의 하행역이 기존 구역에 존재하지 않으면 신규 구역을 추가합니다.")
    @Test
    void sameUpDownStation() {
        // given
        Sections sections = new Sections();
        Section section = new Section(StationFixtures.UP_STATION, StationFixtures.DOWN_STATION, 10L);
        sections.addSection(section);

        Section newSection = new Section(StationFixtures.DOWN_STATION, StationFixtures.NEW_DOWN_STATION, 10L);
        // when
        sections.addSection(newSection);
        // then
        Assertions.assertThat(sections.getSections()).hasSize(2);
    }
}
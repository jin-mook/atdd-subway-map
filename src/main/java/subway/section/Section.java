package subway.section;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import subway.exception.NotSameUpAndDownStationException;
import subway.line.Line;
import subway.station.Station;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Section {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    private Long distance;

    public Section(Station upStation, Station downStation, Long distance) {
        if (upStation.equals(downStation)) {
            throw new NotSameUpAndDownStationException();
        }
        if (distance <= 0) {
            throw new IllegalArgumentException("distance 값이 올바르지 않습니다.");
        }

        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public boolean isDownStationSameWithNewUpStation(Section section) {
        return this.downStation.equals(section.getUpStation());
    }

    public boolean containStation(Station station) {
        return upStation.equals(station) || downStation.equals(station);
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public void addLine(Line line) {
        this.line = line;
    }
}

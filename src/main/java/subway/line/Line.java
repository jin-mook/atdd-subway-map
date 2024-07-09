package subway.line;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import subway.exception.SameUpAndDownStationException;
import subway.station.Station;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;
    private Long distance;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    public Line(LineInfoDto lineInfoDto, Station upStation, Station downStation) {
        if (lineInfoDto.getDistance() <= 0) {
            throw new IllegalArgumentException("distance 값이 올바르지 않습니다.");
        }
        this.name = lineInfoDto.getName();
        this.color = lineInfoDto.getColor();
        this.distance = lineInfoDto.getDistance();
        addUpAndDownStation(upStation, downStation);
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public void updateColor(String newColor) {
        this.color = newColor;
    }

    private void addUpAndDownStation(Station upStation, Station downStation) {
        if (upStation.equals(downStation)) {
            throw new SameUpAndDownStationException();
        }
        this.upStation = upStation;
        this.downStation = downStation;
    }
}

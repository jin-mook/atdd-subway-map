package subway.line;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import subway.station.Station;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "line", fetch = FetchType.LAZY)
    private Set<LineStation> lineStations = new HashSet<>();

    public Line(String name, String color, Long distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("distance 값이 올바르지 않습니다.");
        }
        this.name = name;
        this.color = color;
        this.distance = distance;
    }

    public void addStation(LineStation lineStation) {
        lineStations.add(lineStation);
    }

    public Set<Station> getStations() {
        return lineStations.stream()
                .map(LineStation::getStation)
                .collect(Collectors.toUnmodifiableSet());
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public void updateColor(String newColor) {
        this.color = newColor;
    }
}

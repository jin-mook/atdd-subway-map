package subway.line;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import subway.Station;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;

    @OneToMany(mappedBy = "line")
    private Set<Station> stations = new HashSet<>();

    public static Line createLine(String name, String color) {
        return new Line(name, color);
    }

    private Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void addStation(List<Station> upStation) {
        this.stations.addAll(upStation);
        upStation.forEach(station -> station.addLine(this));
    }

    public Set<Station> getStations() {
        return Collections.unmodifiableSet(this.stations);
    }
}

package subway.line;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import subway.Station;

import javax.persistence.*;
import java.util.*;
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

    @OneToMany(mappedBy = "line", fetch = FetchType.LAZY)
    private Set<LineStation> lineStations = new HashSet<>();

    public static Line createLine(String name, String color) {
        return new Line(name, color);
    }

    private Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void addStation(LineStation lineStation) {
        lineStations.add(lineStation);
    }



    public Set<Station> getStations() {
        return lineStations.stream().map(LineStation::getStation).collect(Collectors.toUnmodifiableSet());
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public void updateColor(String newColor) {
        this.color = newColor;
    }
}

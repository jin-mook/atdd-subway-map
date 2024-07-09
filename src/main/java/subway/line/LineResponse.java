package subway.line;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LineResponse {

    private Long id;
    private String name;
    private String color;
    private List<LineStationsResponse> stations;

    public LineResponse(Line line, List<LineStationsResponse> stations) {
        this.id = line.getId();
        this.name = line.getName();
        this.color = line.getColor();
        this.stations = stations;
    }

    public static LineResponse from(Line line) {
        List<LineStationsResponse> stationList = Stream.of(line.getUpStation(), line.getDownStation())
                .map(station -> new LineStationsResponse(station.getId(), station.getName()))
                .collect(Collectors.toList());

        return new LineResponse(line, stationList);
    }
}

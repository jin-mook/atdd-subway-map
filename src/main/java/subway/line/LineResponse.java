package subway.line;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LineResponse {

    private Long id;
    private String name;
    private String color;
    private List<LineStationsResponse> stations;

    private LineResponse(Long id, String name, String color, List<LineStationsResponse> stations) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.stations = stations;
    }

    public static LineResponse from(Line line) {
        List<LineStationsResponse> stations = line.getStations().stream()
                .map(station -> new LineStationsResponse(station.getId(), station.getName()))
                .collect(Collectors.toList());

        return new LineResponse(
                line.getId(),
                line.getName(),
                line.getColor(),
                stations
        );
    }
}

package subway.line;

import java.util.List;
import java.util.stream.Collectors;

public class LineStationMapper {

    private LineStationMapper() {
    }

    public static LineResponse makeOneLineResponse(List<LineStation> lineStations) {
        if (lineStations.size() < 1) {
            throw new IllegalArgumentException("LineStation 값은 필수 입니다.");
        }

        Line targetLine = lineStations.get(0).getLine();

        List<LineStationsResponse> stations = lineStations.stream().collect(Collectors.groupingBy(
                        LineStation::getLine,
                        Collectors.mapping(LineStation::getStation, Collectors.toList())
                ))
                .get(targetLine)
                .stream().map(station -> new LineStationsResponse(station.getId(), station.getName()))
                .collect(Collectors.toList());

        return new LineResponse(targetLine, stations);
    }

    public static LineResponse from(Line line) {
        List<LineStationsResponse> stations = line.getLineStations()
                .stream()
                .map(LineStation::getStation)
                .map(station -> new LineStationsResponse(station.getId(), station.getName()))
                .collect(Collectors.toList());

        return new LineResponse(
                line,
                stations
        );
    }
}

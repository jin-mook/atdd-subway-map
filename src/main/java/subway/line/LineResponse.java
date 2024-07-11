package subway.line;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import subway.section.Section;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
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
        List<LineStationsResponse> stationList = line.mapSectionStations(LineStationsResponse::new);

        return new LineResponse(line, stationList);
    }
}

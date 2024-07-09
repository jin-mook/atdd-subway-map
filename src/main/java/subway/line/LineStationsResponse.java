package subway.line;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import subway.station.Station;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LineStationsResponse {

    private Long id;
    private String name;

    public LineStationsResponse(Station station) {
        this.id = station.getId();
        this.name = station.getName();
    }
}

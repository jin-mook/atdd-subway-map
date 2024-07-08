package subway.line;

import lombok.Getter;

@Getter
public class LineStationsResponse {

    private Long id;
    private String name;

    public LineStationsResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

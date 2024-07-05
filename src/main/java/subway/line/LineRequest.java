package subway.line;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class LineRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String color;

    @NotNull
    private long upStationId;

    @NotNull
    private long downStationId;

    @NotNull
    private long distance;

    public LineRequest(String name, String color, long upStationId, long downStationId, long distance) {
        if (upStationId < 1 || downStationId < 1) {
            throw new IllegalArgumentException("stationId 값이 올바르지 않습니다.");
        }

        if (distance <= 0) {
            throw new IllegalArgumentException("distance 값이 올바르지 않습니다.");
        }

        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    private LineRequest() {
    }
}

package subway.exception;

public class SameUpAndDownStationException extends RuntimeException {

    private static final String MESSAGE = "해당하는 지하철 역 정보가 없습니다.";

    public SameUpAndDownStationException() {
        super();
    }
}

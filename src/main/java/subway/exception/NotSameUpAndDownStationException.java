package subway.exception;

public class NotSameUpAndDownStationException extends RuntimeException {

    private static final String MESSAGE = "구간 정보가 올바르지 않습니다.";

    public NotSameUpAndDownStationException() {
        super(MESSAGE);
    }
}

package subway.exception;

public class NoLineExistException extends RuntimeException {

    private static final String MESSAGE = "해당하는 지하철 노선 정보가 없습니다.";

    public NoLineExistException() {
        super(MESSAGE);
    }
}

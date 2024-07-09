package subway.exception;

public class CannotDeleteSectionException extends RuntimeException {

    private static final String MESSAGE = "구간을 삭제할 수 없습니다.";

    public CannotDeleteSectionException() {
        super(MESSAGE);
    }
}

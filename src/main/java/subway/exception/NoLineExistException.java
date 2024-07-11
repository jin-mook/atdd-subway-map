package subway.exception;

import subway.common.ErrorMessage;

public class NoLineExistException extends RuntimeException {

    public NoLineExistException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}

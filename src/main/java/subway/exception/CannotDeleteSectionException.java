package subway.exception;

import subway.common.ErrorMessage;

public class CannotDeleteSectionException extends RuntimeException {

    public CannotDeleteSectionException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}

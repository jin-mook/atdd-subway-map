package subway.exception;

import subway.common.ErrorMessage;

public class IllegalDistanceValueException extends RuntimeException {

    public IllegalDistanceValueException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}

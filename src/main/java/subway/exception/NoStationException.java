package subway.exception;

import subway.common.ErrorMessage;

public class NoStationException extends RuntimeException {

    public NoStationException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}

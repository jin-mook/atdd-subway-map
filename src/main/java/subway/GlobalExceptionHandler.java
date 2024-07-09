package subway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.common.ErrorMessage;
import subway.common.ErrorResponse;
import subway.exception.NoLineException;
import subway.exception.NoStationException;
import subway.exception.NotSameUpAndDownStationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoLineException.class, NoStationException.class, NotSameUpAndDownStationException.class})
    public ResponseEntity<ErrorMessage> noElementException(Exception exception) {
        return ErrorResponse.badRequest(exception.getMessage());
    }
}

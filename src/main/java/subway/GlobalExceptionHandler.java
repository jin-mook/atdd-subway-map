package subway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.common.ErrorResponse;
import subway.exception.CannotDeleteSectionException;
import subway.exception.NoLineExistException;
import subway.exception.NoStationException;
import subway.exception.NotSameUpAndDownStationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoLineExistException.class)
    public ResponseEntity<String> lineException(Exception exception) {
        return ErrorResponse.badRequest(exception.getMessage());
    }

    @ExceptionHandler({NoStationException.class, NotSameUpAndDownStationException.class})
    public ResponseEntity<String> stationException(Exception exception) {
        return ErrorResponse.badRequest(exception.getMessage());
    }

    @ExceptionHandler(CannotDeleteSectionException.class)
    public ResponseEntity<String> sectionException(Exception exception) {
        return ErrorResponse.badRequest(exception.getMessage());
    }
}

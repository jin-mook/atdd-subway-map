package subway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import subway.exception.NoLineException;
import subway.exception.NoStationException;
import subway.exception.SameUpAndDownStationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoLineException.class, NoStationException.class, SameUpAndDownStationException.class})
    public ResponseEntity<Void> noElementException() {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}

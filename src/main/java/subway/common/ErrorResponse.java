package subway.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {

    public static ResponseEntity<ErrorMessage> badRequest(String message) {
        return new ResponseEntity<>(new ErrorMessage(message), HttpStatus.BAD_REQUEST);
    }
}

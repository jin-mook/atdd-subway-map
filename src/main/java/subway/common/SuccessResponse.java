package subway.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class SuccessResponse {

    public static <T> ResponseEntity<T> created(T data, HttpHeaders httpHeaders) {
        httpHeaders.addAll(HttpHeaders.VARY, List.of("Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        return new ResponseEntity<>(data, httpHeaders, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> ok(T data) {
        HttpHeaders httpHeaders = makeDefaultOptionHeaders();
        return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);
    }

    public static ResponseEntity<Void> ok() {
        return ok(null);
    }

    public static ResponseEntity<Void> noContent() {
        HttpHeaders httpHeaders = makeDefaultOptionHeaders();
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.NO_CONTENT);
    }

    private static HttpHeaders makeDefaultOptionHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(HttpHeaders.VARY, List.of("Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        return headers;
    }
}

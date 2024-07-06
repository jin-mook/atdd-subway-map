package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LineController {

    private final LineService lineService;

    @PostMapping("/lines")
    public ResponseEntity<LineResponse> createLine(@Validated @RequestBody LineRequest lineRequest) {
        LineResponse data = lineService.saveLine(lineRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(HttpHeaders.VARY, List.of("Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        headers.add(HttpHeaders.LOCATION, "/lines/" + data.getId());
        return new ResponseEntity<>(data, headers, HttpStatus.CREATED);
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineResponse>> showLines() {
        List<LineResponse> data = lineService.showLines();
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(HttpHeaders.VARY, List.of("Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @GetMapping("/lines/{lineId}")
    public ResponseEntity<LineResponse> showLine(@PathVariable Long lineId) {
        System.out.println("lineId = " + lineId);
        LineResponse data = lineService.showLine(lineId);
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(HttpHeaders.VARY, List.of("Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}

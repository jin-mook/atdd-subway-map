package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import subway.common.SuccessResponse;

import java.util.List;

@RequestMapping("/lines")
@RestController
@RequiredArgsConstructor
public class LineController {

    private final LineService lineService;

    @PostMapping
    public ResponseEntity<LineResponse> createLine(@Validated @RequestBody LineRequest lineRequest) {
        LineResponse data = lineService.saveLine(lineRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, "/lines/" + data.getId());
        return SuccessResponse.created(data, httpHeaders);
    }

    @GetMapping
    public ResponseEntity<List<LineResponse>> showLines() {
        List<LineResponse> data = lineService.showLines();
        return SuccessResponse.ok(data);
    }

    @GetMapping("/{lineId}")
    public ResponseEntity<LineResponse> showLine(@PathVariable Long lineId) {
        LineResponse data = lineService.showLine(lineId);
        return SuccessResponse.ok(data);
    }

    @PutMapping("/{lineId}")
    public ResponseEntity<Void> updateLine(@PathVariable Long lineId,
                                           @Validated @RequestBody UpdateLineRequest updateLineRequest) {
        lineService.updateLine(lineId, updateLineRequest);
        return SuccessResponse.ok();
    }

    @DeleteMapping("/{lineId}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long lineId) {
        lineService.deleteLine(lineId);
        return SuccessResponse.noContent();
    }
}

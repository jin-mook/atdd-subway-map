package subway.station;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.common.SuccessResponse;

import java.net.URI;
import java.util.List;

@RequestMapping("/stations")
@RestController
public class StationController {
    private StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<StationResponse> createStation(@RequestBody StationRequest stationRequest) {
        StationResponse station = stationService.saveStation(stationRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, "/lines/" + station.getId());
        return SuccessResponse.created(station, httpHeaders);
    }

    @GetMapping
    public ResponseEntity<List<StationResponse>> showStations() {
        return SuccessResponse.ok(stationService.findAllStations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        stationService.deleteStationById(id);
        return SuccessResponse.noContent();
    }
}

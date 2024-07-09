package subway.section;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.exception.NoLineException;
import subway.line.Line;
import subway.line.LineRepository;
import subway.line.LineResponse;
import subway.station.Station;
import subway.station.StationService;

@Service
@Transactional
@RequiredArgsConstructor
public class SectionService {

    private final LineRepository lineRepository;

    private final StationService stationService;

    public LineResponse addSection(Long lineId, SectionRequest sectionRequest) {

        Line line = lineRepository.findByIdWithSectionsAndStations(lineId)
                .orElseThrow(NoLineException::new);

        Station upStation = stationService.findById(sectionRequest.getUpStationId());
        Station downStation = stationService.findById(sectionRequest.getDownStationId());

        Section section = new Section(upStation, downStation, sectionRequest.getDistance());

        line.addSection(section);
        return LineResponse.from(line);
    }
}

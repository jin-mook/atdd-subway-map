package subway.section;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.line.Line;
import subway.line.LineResponse;
import subway.line.LineService;
import subway.station.Station;
import subway.station.StationService;

@Service
@Transactional
@RequiredArgsConstructor
public class SectionService {

    private final StationService stationService;
    private final LineService lineService;

    public LineResponse addSection(Long lineId, SectionRequest sectionRequest) {
        Line line = lineService.findLine(lineId);

        Station upStation = stationService.findById(sectionRequest.getUpStationId());
        Station downStation = stationService.findById(sectionRequest.getDownStationId());

        Section section = new Section(upStation, downStation, sectionRequest.getDistance());
        line.addSection(section);

        return LineResponse.from(line);
    }

    public void deleteSection(Long lineId, Long stationId) {
        Line line = lineService.findLine(lineId);

        Section targetSection = line.findDeleteTargetSection(stationId);
        line.deleteSection(targetSection);
    }
}

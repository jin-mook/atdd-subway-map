package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.exception.NoLineException;
import subway.exception.NoStationException;
import subway.station.Station;
import subway.station.StationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public LineResponse saveLine(LineRequest lineRequest) {

        Station upStation = stationRepository.findById(lineRequest.getUpStationId())
                .orElseThrow(NoStationException::new);
        Station downStation = stationRepository.findById(lineRequest.getDownStationId())
                .orElseThrow(NoStationException::new);

        Line line = new Line(lineRequest.toLineInfoDto(), upStation, downStation);
        Line savedLine = lineRepository.save(line);

        return LineResponse.from(savedLine);
    }

    @Transactional(readOnly = true)
    public List<LineResponse> showLines() {
        List<Line> lines = lineRepository.findAll();

        return lines.stream()
                .map(LineResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LineResponse showLine(Long lineId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(NoLineException::new);

        return LineResponse.from(line);
    }

    public void updateLine(Long lineId, UpdateLineRequest updateLineRequest) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(NoLineException::new);
        line.updateName(updateLineRequest.getName());
        line.updateColor(updateLineRequest.getColor());
    }

    public void deleteLine(Long lineId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(NoLineException::new);
        lineRepository.delete(line);
    }
}

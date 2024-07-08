package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.exception.NoLineException;
import subway.exception.NoStationException;
import subway.station.Station;
import subway.station.StationRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final LineStationRepository lineStationRepository;

    public LineResponse saveLine(LineRequest lineRequest) {

        Station upStation = stationRepository.findById(lineRequest.getUpStationId())
                .orElseThrow(NoStationException::new);
        Station downStation = stationRepository.findById(lineRequest.getDownStationId())
                .orElseThrow(NoStationException::new);

        Line line = new Line(lineRequest.getName(), lineRequest.getColor(), lineRequest.getDistance());
        Line savedLine = lineRepository.save(line);

        LineStation upLineStation = new LineStation(savedLine, upStation);
        LineStation downLineStation = new LineStation(savedLine, downStation);

        List<LineStation> lineStations = lineStationRepository.saveAll(List.of(upLineStation, downLineStation));

        return LineStationMapper.makeOneLineResponse(lineStations);
    }

    @Transactional(readOnly = true)
    public List<LineResponse> showLines() {
        List<Line> lines = lineRepository.findAllWithDistinct();
        return lines.stream().map(LineStationMapper::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LineResponse showLine(Long lineId) {
        Line line = lineRepository.findDistinctById(lineId)
                .orElseThrow(NoLineException::new);
        return LineStationMapper.from(line);
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
        Set<LineStation> lineStations = line.getLineStations();

        lineStationRepository.deleteAll(lineStations);
        lineRepository.delete(line);
    }
}

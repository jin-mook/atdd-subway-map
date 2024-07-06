package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.Station;
import subway.StationRepository;

import java.util.List;
import java.util.NoSuchElementException;
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
                .orElseThrow(() -> new NoSuchElementException("해당하는 역 정보가 없습니다."));
        Station downStation = stationRepository.findById(lineRequest.getDownStationId())
                .orElseThrow(() -> new NoSuchElementException("해당하는 역 정보가 없습니다."));

        Line line = Line.createLine(lineRequest.getName(), lineRequest.getColor());
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
                .orElseThrow(() -> new NoSuchElementException("해당하는 노선 정보가 없습니다."));
        return LineStationMapper.from(line);
    }

    public void updateLine(Long lineId, UpdateLineRequest updateLineRequest) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 노선 정보가 없습니다."));
        line.updateName(updateLineRequest.getName());
        line.updateColor(updateLineRequest.getColor());
    }

    public void deleteLine(Long lineId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 노선 정보가 없습니다."));
        Set<LineStation> lineStations = line.getLineStations();

        lineStationRepository.deleteAll(lineStations);
        lineRepository.delete(line);
    }
}

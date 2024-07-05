package subway.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.Station;
import subway.StationRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public LineResponse saveLine(LineRequest lineRequest) {

        Station upStation = stationRepository.findById(lineRequest.getUpStationId())
                .orElseThrow(() -> new NoSuchElementException("해당하는 역 정보가 없습니다."));
        Station downStation = stationRepository.findById(lineRequest.getDownStationId())
                .orElseThrow(() -> new NoSuchElementException("해당하는 역 정보가 없습니다."));

        Line line = Line.createLine(lineRequest.getName(), lineRequest.getColor());
        line.addStation(List.of(upStation, downStation));

        Line savedLine = lineRepository.save(line);

        return LineResponse.from(savedLine);
    }
}

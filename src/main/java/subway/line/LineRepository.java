package subway.line;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LineRepository extends JpaRepository<Line, Long> {

    @Query("select distinct l from Line l join fetch l.lineStations")
    List<Line> findAllWithDistinct();
}

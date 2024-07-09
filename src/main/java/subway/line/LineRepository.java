package subway.line;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LineRepository extends JpaRepository<Line, Long> {

    @Query("select l from Line l join fetch l.sections.sections s join fetch s.upStation join fetch s.downStation")
    List<Line> findAllWithSectionsAndStations();
}

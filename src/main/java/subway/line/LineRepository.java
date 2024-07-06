package subway.line;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LineRepository extends JpaRepository<Line, Long> {

    @Query("select distinct l from Line l join fetch l.lineStations ls join fetch ls.station")
    List<Line> findAllWithDistinct();

    @Query("select distinct l from Line l join fetch l.lineStations ls join fetch ls.station where l.id = :id")
    Optional<Line> findDistinctById(@Param("id") Long id);
}

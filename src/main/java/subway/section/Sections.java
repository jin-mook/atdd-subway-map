package subway.section;

import subway.exception.CannotDeleteSectionException;
import subway.exception.NotSameUpAndDownStationException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    public void addSection(Section newSection) {
        if (sections.isEmpty() || canConnectedWithNewSection(newSection)) {
            sections.add(newSection);
            return;
        }

        throw new NotSameUpAndDownStationException();
    }

    private boolean canConnectedWithNewSection(Section newSection) {
        Section lastSection = getLastSection();

        return lastSection.isDownStationSameWithNewUpStation(newSection)
                && !alreadyHasNewDownStation(newSection);
    }

    private boolean alreadyHasNewDownStation(Section newSection) {
        return sections.stream()
                .anyMatch(section -> section.containStation(newSection.getDownStation()));
    }

    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    public Section getDeleteTargetSection(Long stationId) {
        if (sections.size() <= 1) {
            throw new CannotDeleteSectionException();
        }

        if (!isLastStation(stationId)) {
            throw new CannotDeleteSectionException();
        }

        return getLastSection();
    }

    public void deleteSection(Section section) {
        sections.remove(section);
    }

    private boolean isLastStation(Long stationId) {
        return getLastSection().getDownStation().getId().equals(stationId);
    }

    private Section getLastSection() {
        return sections.get(sections.size() - 1);
    }
}

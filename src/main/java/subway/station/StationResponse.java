package subway.station;

public class StationResponse {

  private Long id;
  private String name;

  public StationResponse() {
  }

  public StationResponse(Station station) {
    this.id = station.getStationId();
    this.name = station.getName();
  }

  public StationResponse(Long stationId, String name) {
    this.id = stationId;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}

package subway.common;

import lombok.Getter;

@Getter
public class ErrorMessage {

    private final String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}

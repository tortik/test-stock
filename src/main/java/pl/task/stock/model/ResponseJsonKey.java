package pl.task.stock.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  ResponseJsonKey {
    OPEN("1. open"),
    CLOSE("4. close");

    @NonNull
    private String value;

    @Override
    public String toString() {
        return value;
    }

}

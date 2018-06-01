package pl.task.stock.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeSeries {

    DAILY("TIME_SERIES_DAILY", "Daily"),
    INTRADAY("TIME_SERIES_INTRADAY", "60min");

    @NonNull
    private String value;
    @NonNull
    private String suffix;

    @Override
    public String toString() {
        return value;
    }
}

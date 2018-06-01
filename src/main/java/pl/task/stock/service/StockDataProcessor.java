package pl.task.stock.service;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import pl.task.stock.model.ResponseJsonKey;
import pl.task.stock.model.TimeSeries;
import pl.task.stock.util.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.task.stock.model.ResponseJsonKey.CLOSE;
import static pl.task.stock.model.ResponseJsonKey.OPEN;
import static pl.task.stock.model.TimeSeries.INTRADAY;
import static pl.task.stock.util.DateUtils.DATE_FORMAT;
import static pl.task.stock.util.DateUtils.transform;

@Singleton
public class StockDataProcessor {

    public static final int DIVIDE_SCALE = 4;
    private StockService stockService;

    @Inject
    public StockDataProcessor(StockService stockService) {
        this.stockService = stockService;
    }

    public void printAverageOpenWithSigmaClosed(String fromDate, String toDate, String apiKey, String equityName) {
        if (!DateUtils.isValidDate(fromDate) && !DateUtils.isValidDate(toDate)) {
            System.out.format("Date format is incorrect either for startDate or endDate. Please use pattern %s", DATE_FORMAT);
            return;
        }
        LocalDate startDay = transform(fromDate);
        LocalDate endDay = transform(toDate);
        TimeSeries series = startDay.isEqual(endDay) ? INTRADAY : TimeSeries.DAILY;
        JsonObject response = stockService.getStockRates(series, Optional.of(apiKey), equityName);
        Set<String> datesToFilter = getJsonKeysToProcess(startDay, endDay, response);

        BigDecimal openAverage = calculateAverageRate(response, datesToFilter, OPEN);
        BigDecimal closeAverage = calculateAverageRate(response, datesToFilter, CLOSE);
        double closedSigma = calculateSigma(response, datesToFilter, CLOSE, closeAverage);
        printResult(openAverage.doubleValue());
        printResult(closedSigma);

    }

    private BigDecimal calculateAverageRate(JsonObject data, Set<String> daysToProcess, ResponseJsonKey key) {
        BigDecimal sum = daysToProcess.stream().
                map(data::getAsJsonObject).
                map(it -> it.getAsJsonPrimitive(key.getValue())).
                map(JsonElement::getAsString).
                map(BigDecimal::new).
                reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(daysToProcess.size()), DIVIDE_SCALE, RoundingMode.HALF_DOWN);

    }

    private double calculateSigma(JsonObject data, Set<String> daysToProcess, ResponseJsonKey key, BigDecimal average) {
        BigDecimal diffBetweenAverageInPow = daysToProcess.stream().
                map(data::getAsJsonObject).
                map(it -> it.getAsJsonPrimitive(key.getValue())).
                map(JsonElement::getAsString).
                map(BigDecimal::new).
                map(average::subtract).
                map(it -> it.pow(2)).
                reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal dispersion = diffBetweenAverageInPow.divide(BigDecimal.valueOf(daysToProcess.size()), DIVIDE_SCALE, RoundingMode.HALF_DOWN);
        return Math.sqrt(dispersion.doubleValue());
    }

    private Set<String> getJsonKeysToProcess(LocalDate startDay, LocalDate endDay, JsonObject response) {
        Set<String> datesToFilter = DateUtils.getDatesBetween(startDay, endDay);
        return response.keySet().stream().filter(datesToFilter::contains).collect(Collectors.toSet());

    }

    private void printResult(double res) {
        System.out.format("%.4f ", res);
    }
}
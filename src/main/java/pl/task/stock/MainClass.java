package pl.task.stock;


import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import pl.task.stock.service.StockDataProcessor;

public class MainClass {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Please provide all data in following order 'stockName startDate endDate apiKey'");
        }
        Injector injector = Guice.createInjector(new AbstractModule(){});

        StockDataProcessor app = injector.getInstance(StockDataProcessor.class);
        app.printAverageOpenWithSigmaClosed(args[1], args[2], args[3], args[0]);

    }
}

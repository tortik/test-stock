# Test Stock

Test application for calculating average open position on specified date range
It wasn't covered with unit test, due to lack of requirements in tech spec.

## Getting Started

Before run main class, make sure that you import dependencies
```
mvn clean package
```

### Frameworks

* Guice - for DI
* Retrofit - for HTTP calls
* Commons-configuration2 - for reading properties

## It calculates
* Average - sum of stock values divided on count
* Sigma - sqrt(((average - stock value first)2 + ...+(average - stock value last)2) / amount)
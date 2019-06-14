package com.app.service;

import com.app.converters.CarsJsonConverter;
import com.app.exception.MyException;
import com.app.model.Car;
import com.app.model.enums.Color;
import com.app.model.enums.SortType;
import com.app.validator.CarValidator;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarsService {
    private final Set<Car> cars;

    public CarsService(String jsonFilename) {
        cars = getFromJsonFile(jsonFilename);
    }

    private Set<Car> getFromJsonFile(String jsonFile) {

        CarValidator carValidator = new CarValidator();
        AtomicInteger atomicInteger = new AtomicInteger(1);

        return new CarsJsonConverter(jsonFile).fromJson()
                .orElseThrow(() -> new MyException("CAR SERVICE - FROM JSON CONVERSION EXCEPTION"))
                .stream()
                .filter(car -> {
                    Map<String, String> errors = carValidator.validate(car);


                    if (carValidator.hasErrors()) {
                        System.out.println("\n\n----------------------------------------------");
                        System.out.println("EXCEPTION IN CAR NO. " + atomicInteger.get());
                        errors.forEach((k, v) -> System.out.println(k + " " + v));
                        System.out.println("----------------------------------------------\n\n");
                    }
                    atomicInteger.incrementAndGet();
                    return !carValidator.hasErrors();
                })
                .collect(Collectors.toSet());
    }

    public List<Car> sort(SortType sortType, boolean descending) {
        if (sortType == null) {
            throw new MyException("Sort type object is null");
        }

        Stream<Car> carStream = null;

        switch (sortType) {
            case COLOR:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getColor));
                break;
            case MODEL:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getModel));
                break;
            case PRICE:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getPrice));
                break;
            case MILEAGE:
                carStream = cars.stream().sorted(Comparator.comparing(Car::getMileage));
                break;
        }

        List<Car> sortedCars = carStream.collect(Collectors.toList());

        if (descending) {
            Collections.reverse(sortedCars);
        }
        return sortedCars;
    }

    public List<Car> checkMileage(int mileage) {
        if (mileage < 0) {
            throw new MyException("mileage cannot be negative");
        }
        return cars.stream().filter(c -> c.getMileage() > mileage).collect(Collectors.toList());
    }

    public Map<Color, Long> carsMatchingColor() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(car -> car.getColor(), Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue(),
                        (v1, v2) -> v1,
                        () -> new LinkedHashMap<>()
                ));
    }

    public Map<String, Car> theMostExpensiveModelOfCar() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(
                        car -> car.getModel(),
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(car -> car.getPrice())), carOp -> carOp.orElseThrow(() -> new MyException("CAR NOT FOUND")))
                        )
                );
    }

    public void stats() {
        IntSummaryStatistics iss = cars.stream().collect(Collectors.summarizingInt(car -> car.getMileage()));
        System.out.println("MAX MILEAGE = " + iss.getMax());
        System.out.println("MIN MILEAGE = " + iss.getMin());
        System.out.println("AVG MILEAGE = " + iss.getAverage());

        System.out.println("AVG PRICE: " + cars.stream().collect(Collectors2.summarizingBigDecimal(Car::getPrice)).getAverage());
        System.out.println("MIN PRICE: " + cars.stream().collect(Collectors2.summarizingBigDecimal(Car::getPrice)).getMin());
        System.out.println("MIN PRICE: " + cars.stream().collect(Collectors2.summarizingBigDecimal(Car::getPrice)).getMax());
    }

    public List<Car> maxPriceCars() {

        return cars
                .stream()
                .map(car -> car.getPrice())
                .max(Comparator.naturalOrder())
                .flatMap(maxPrice -> Optional.of(cars
                        .stream()
                        .filter(car -> car.getPrice().equals(maxPrice))
                        .collect(Collectors.toList())
                ))
                .orElseThrow(() -> new MyException("NO CARS WITH MAX PRICE"));

    }

    Set<Car> carsWithSortedComponents() {

        return cars
                .stream()
                .peek(car -> car.setComponents(car.getComponents().stream().sorted().collect(Collectors.toCollection(() -> new LinkedHashSet<>()))))
                .collect(Collectors.toSet());


    }

    Map<String, List<Car>> carsGroupedByComponents() {

        return cars
                .stream()
                .flatMap(c -> c.getComponents().stream())
                .distinct()
                .collect(Collectors
                        .toMap(Function.identity(), c -> cars.stream()
                                .filter(car -> car.getComponents().contains(c))
                                .collect(Collectors.toList())));
    }

    Set<Car> carsInPriceRange(BigDecimal x, BigDecimal y) {

        if (x.compareTo(y) >= 0) {
            throw new MyException("PRICE RANGE IS NOT CORRECT");
        }

        return cars
                .stream()
                .filter(car -> car.getPrice().compareTo(x) > 0 && car.getPrice().compareTo(y) < 0)
                .collect(Collectors.toSet());
    }


    @Override
    public String toString() {
        return cars.stream().map(car -> car.toString()).collect(Collectors.joining("\n"));
    }
}

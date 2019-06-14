package com.app.service;


import com.app.exception.MyException;
import com.app.model.Car;
import com.app.model.enums.SortType;

import java.math.BigDecimal;
import java.util.*;

public class MenuService {

    private final CarsService carsService;
    private final UserDataService userDataService;

    public MenuService(String jsonFilename) {
        this.carsService = new CarsService(jsonFilename);
        this.userDataService = new UserDataService();
    }

    public void mainMenu() {
        do {

            try {

                printMenu();
                int option = userDataService.getInt("Choose option");

                switch (option) {
                    case 1:
                        option1();
                        break;
                    case 2:
                        option2();
                        break;
                    case 3:
                        option3();
                        break;
                    case 4:
                        option4();
                        break;
                    case 5:
                        option5();
                        break;
                    case 6:
                        option6();
                        break;
                    case 7:
                        option7();
                        break;
                    case 8:
                        option8();
                        break;
                    case 9:
                        option9();
                        break;

                }

            } catch (MyException e) {
                System.out.println("\n\n------------------- EXCEPTION ---------------");
                System.out.println(e.getMessage());
                System.out.println("---------------------------------------------\n\n");
            }

        } while (true);
    }

    private void printMenu() {
        System.out.println("\n\n-----------------------------------------");
        System.out.println("1 - show cars");
        System.out.println("2 - sort cars");
        System.out.println("3 - compare mileages ");
        System.out.println("4 - the most expensive model");
        System.out.println("5 - stats");
        System.out.println("6 - max price cars");
        System.out.println("7 - sorted components");
        System.out.println("8 - grouped components");
        System.out.println("9 - cars in price range");
    }

    private void option1() {
        System.out.println(carsService);
    }

    private void option2() {
        SortType sortType = userDataService.getSortType();
        boolean descending = userDataService.getBoolean("Descending?");
        carsService
                .sort(sortType, descending)
                .forEach(car -> System.out.println(car));
    }

    private void option3() {
        List<Car> l1 = new ArrayList<Car>(carsService.checkMileage(30000));
        System.out.println(l1);
    }

    private void option4() {
        Map<String, Car> m1 = new HashMap<>(carsService.theMostExpensiveModelOfCar());
        System.out.println(m1);
    }

    private void option5() {
        carsService.stats();
    }
    private void option6() {
        List<Car> l1 = new ArrayList<Car>(carsService.maxPriceCars());
        System.out.println(l1);
    }
    private void option7() {
        Set<Car> s1 = new LinkedHashSet<>(carsService.carsWithSortedComponents());
        System.out.println(s1);
    }
    private void option8() {
        Map<String, List<Car>> m1 = new HashMap<>(carsService.carsGroupedByComponents());
        System.out.println(m1);
    }
    private void option9 () {
        Set<Car> s1 = new HashSet<>(carsService.carsInPriceRange(new BigDecimal(100),new BigDecimal(140)));
        System.out.println(s1);
    }

}

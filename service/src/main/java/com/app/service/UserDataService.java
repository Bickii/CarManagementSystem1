package com.app.service;


import com.app.exception.MyException;
import com.app.model.enums.SortType;

import java.util.Scanner;

public class UserDataService {

    private Scanner scanner = new Scanner(System.in);

    public int getInt(String message) {
        System.out.println(message);
        String text = scanner.nextLine();

        if (!text.matches("\\d+")) {
            throw new MyException("Int value is not correct");
        }

        return Integer.parseInt(text);
    }

    public SortType getSortType() {
        System.out.println("Choose sort type:");
        System.out.println("1 - price");
        System.out.println("2 - model");
        System.out.println("3 - mileage");
        System.out.println("4 - color");
        String text = scanner.nextLine();

        if (!text.matches("[1-4]")) {
            throw new MyException("Sort type value is not correct");
        }

        return SortType.values()[Integer.parseInt(text) - 1];
    }

    public boolean getBoolean(String message) {
        System.out.println(message + "[y/n]");
        char decision = scanner.nextLine().charAt(0);
        return decision == 'y';
    }
}

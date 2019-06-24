package menu;

import com.app.service.MenuService;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        System.out.println("Insert the path of the cars.json file please");
        Scanner sc = new Scanner(System.in);
        String m = sc.nextLine();
        final String JSON_FILE_FOLDER = m;
        final String jsonFilename = JSON_FILE_FOLDER + "cars.json";
        MenuService menuService = new MenuService(jsonFilename);
        menuService.mainMenu();
    }
}

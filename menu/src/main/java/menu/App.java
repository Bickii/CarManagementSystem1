package menu;


import com.app.service.MenuService;

public class App
{
    public static void main( String[] args )
    {
        final String jsonFilename = "cars.json";
        MenuService menuService = new MenuService(jsonFilename);
        menuService.mainMenu();
    }
}

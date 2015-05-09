package engine;

import gui.AdminGUI;
import javafx.stage.Stage;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class AdminController {
    public AdminController(Stage stage) {
        stage.close();
        System.out.println("Admin app started");
        try {
            new AdminGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

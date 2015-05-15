package engine;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class AdminController {
    public AdminController(Stage stage) {
        stage.close();
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root, stage2.getWidth(), stage2.getHeight()));
        stage2.show();
    }

}

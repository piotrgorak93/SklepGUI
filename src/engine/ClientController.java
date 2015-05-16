package engine;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Piotr G�rak, Maciej Knicha� dnia 2015-05-09.
 */
public class ClientController {
    public ClientController(Stage stage) {
        stage.close();
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client.fxml"));
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

package engine;

import gui.ClientGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Piotr G�rak, Maciej Knicha� dnia 2015-05-09.
 */
public class ClientController {

    public ClientController(Stage stage) {
        stage.close();

        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("client.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage2 = new Stage();
        stage2.setTitle("System sklepowy");
        stage2.setScene(new Scene(root, 450, 450));
        stage2.show();
    }
}

package engine;

import gui.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
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
        Image image = new Image(getClass().getResourceAsStream("1.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        stage2.setScene(new Scene(root, stage2.getWidth(), stage2.getHeight()));

        stage2.show();
    }
}

package gui;

import engine.AdminController;
import engine.ClientController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Piotr Górak, Maciej Knicha³ dnia 2015-05-09.
 */
public class Login {
    ArrayList<User> listaUzytkownikow = new ArrayList<>();
    String checkUser;
    String checkPw;

    public Login(Stage primaryStage) {
        listaUzytkownikow.add(new User("admin", "admin"));
        listaUzytkownikow.add(new User("user", "user"));
        primaryStage.setTitle("Witaj w sklepie");

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 50, 50, 50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20, 20, 20, 30));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Username");
        final TextField txtUserName = new TextField();
        Label lblPassword = new Label("Password");
        final PasswordField pf = new PasswordField();
        Button btnLogin = new Button("Login");
        final Label lblMessage = new Label();

        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(btnLogin, 2, 1);
        gridPane.add(lblMessage, 1, 2);


        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);

        //DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Adding text and DropShadow effect to it
        Text text = new Text("Logowanie do sklepu");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        text.setId("text");


        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);

        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp);
        String css = this.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        //primaryStage.setResizable(false);
        primaryStage.show();

        //Action for btnLogin
        btnLogin.setOnAction(event -> {
            checkUser = txtUserName.getText();
            checkPw = pf.getText();
            if (czyJestUzytkownik(checkUser, checkPw)) {
                lblMessage.setText("Congratulations!");
                lblMessage.setTextFill(Color.GREEN);
                if (checkUser.equals("admin")) {
                    new AdminController(primaryStage);
                } else
                    new ClientController(primaryStage);
            } else {
                lblMessage.setText("Bledne dane logowania");
                lblMessage.setTextFill(Color.RED);
            }
            txtUserName.setText("");
            pf.setText("");
        });


    }

    private User findUserByName(String user) {
        for (User user1 : listaUzytkownikow) {
            if (user1.nazwa.equals(user))
                return user1;
        }
        return null;
    }

    private boolean czyJestUzytkownik(String user, String password) {
        for (User user1 : listaUzytkownikow) {
            if (user1.nazwa.equals(user) && user1.haslo.equals(password)) {
                return true;
            }
        }
        return false;
    }
}

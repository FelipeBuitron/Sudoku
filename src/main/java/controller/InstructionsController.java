package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.fxml.FXML;

import java.io.IOException;

public class InstructionsController {

    @FXML
    private Label titulo;

    @FXML
    private Label subtitulo;

    @FXML
    private Label texto;

    @FXML
    private Button botonVolver;


    public void initialize(){

        Font gilroy = Font.loadFont(
                getClass().getResourceAsStream("/fonts/gilroy.TTF"),
                20
        );

        Font gilroyBold = Font.loadFont(
                getClass().getResourceAsStream("/fonts/gilroyblod.TTF"),
                20
        );

        Font rog = Font.loadFont(
                getClass().getResourceAsStream("/fonts/rog.otf"),
                60
        );
        Font gilroyBoton = Font.loadFont(
                getClass().getResourceAsStream("/fonts/rog.otf"),
                20
        );

        titulo.setFont(rog);
        subtitulo.setFont(gilroy);
        texto.setFont(gilroy);
        botonVolver.setFont(gilroyBoton);

    }
    public void goBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/StartView.fxml")
        );
        Parent root = loader.load();

        Scene scene =new Scene(root);

        Stage stage=(Stage)((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(scene);
        stage.show();

    }
}


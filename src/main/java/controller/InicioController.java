package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;

import java.io.IOException;

public class InicioController {

    public void BotonIniciarJuego(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/JuegoView.fxml")
        );
        Parent root = loader.load();

        Scene scene =new Scene(root);

        Stage stage=(Stage)((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(scene);
        stage.show();
    }
    public void BotonInstrucciones(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/instrucciones.fxml")
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


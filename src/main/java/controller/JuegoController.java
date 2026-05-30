package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;



import java.util.ArrayList;

public class JuegoController {

    @FXML private GridPane tableroSudoku;

    private ArrayList<ArrayList<TextField>> tablero;

    public void initialize() {

        // TABLERO

        tablero = new ArrayList<>();

        for (int fila = 0; fila < 6; fila++) {

            ArrayList<TextField> filaActual = new ArrayList<>();

            for (int columna = 0; columna < 6; columna++) {

                TextField celda = new TextField();

                celda.setPrefSize(60, 60);
                celda.setMaxSize(60, 60);

                celda.setStyle(
                        "-fx-alignment: center;" +
                                "-fx-font-size:50px;" +
                                "-fx-border-color: black;" +
                                "-fx-border-width: 1;"
                );

                int bloqueFila = fila / 2;
                int bloqueColumna = columna / 3;
                String baseStyle =
                        "-fx-font-size: 18px;" +
                                "-fx-alignment: center;" +
                                "-fx-border-color: black;" +
                                "-fx-border-width: 1;";

                if ((bloqueFila + bloqueColumna) % 2 == 0) {
                    celda.setStyle(baseStyle + "-fx-background-color: #E0E0E0;");

                } else {
                    celda.setStyle(baseStyle + "-fx-background-color: white;");
                }

                tableroSudoku.add(celda, columna, fila);
                filaActual.add(celda);
            }

            tablero.add(filaActual);
        }
    }
}
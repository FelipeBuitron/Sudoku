package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.SudokuModel; // Importamos el modelo que acabamos de crear

public class JuegoController {

    @FXML private GridPane tableroSudoku;

    // Cambiamos ArrayList por una matriz primitiva estándar
    private TextField[][] celdas;
    private SudokuModel modelo;

    public void initialize() {

        // Inicializamos la matriz visual de 6x6 y creamos el modelo
        celdas = new TextField[6][6];
        modelo = new SudokuModel();

        // Le pedimos al modelo el tablero con los números revelados
        int[][] tableroInicial = modelo.getTableroInicial();

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {

                TextField celda = new TextField();

                celda.setPrefSize(60, 60);
                celda.setMaxSize(60, 60);

                int bloqueFila = fila / 2;
                int bloqueColumna = columna / 3;

                String baseStyle =
                        "-fx-font-size: 18px;" +
                                "-fx-alignment: center;" +
                                "-fx-border-color: black;" +
                                "-fx-border-width: 1;";

                // Coloreamos los bloques
                if ((bloqueFila + bloqueColumna) % 2 == 0) {
                    celda.setStyle(baseStyle + "-fx-background-color: #E0E0E0;");
                } else {
                    celda.setStyle(baseStyle + "-fx-background-color: white;");
                }

                // NUEVO: Verificamos si esta celda debe mostrar un número inicial
                if (tableroInicial[fila][columna] != 0) {
                    celda.setText(String.valueOf(tableroInicial[fila][columna]));
                    celda.setEditable(false); // Bloqueamos la celda para que no la modifiquen
                    celda.setStyle(celda.getStyle() + "-fx-font-weight: bold;"); // Lo ponemos en negrita
                }

                tableroSudoku.add(celda, columna, fila);
                celdas[fila][columna] = celda; // Guardamos el TextField en nuestra matriz
            }
        }
    }
}
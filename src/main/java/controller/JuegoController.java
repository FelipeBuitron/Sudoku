package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.SudokuModel;

public class JuegoController {

    @FXML private GridPane tableroSudoku;

    private TextField[][] celdas;
    private SudokuModel modelo;

    public void initialize() {
        celdas = new TextField[6][6];
        modelo = new SudokuModel();

        int[][] tableroInicial = modelo.getTableroInicial();

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {

                TextField celda = new TextField();
                celda.setPrefSize(60, 60);
                celda.setMaxSize(60, 60);

                // Establecemos los colores base (gris y blanco) por bloques
                establecerEstiloBase(celda, fila, columna);

                // Si la celda tiene un número inicial fijo
                if (tableroInicial[fila][columna] != 0) {
                    celda.setText(String.valueOf(tableroInicial[fila][columna]));
                    celda.setEditable(false); // No se puede modificar
                    celda.setStyle(celda.getStyle() + "-fx-font-weight: bold;"); // Texto en negrita
                } else {
                    // SI LA CELDA ES EDITABLE, creamos las variables constantes para el Listener
                    final int f = fila;
                    final int c = columna;

                    // HU-4: Validación en tiempo real al cambiar el texto
                    celda.textProperty().addListener((observable, oldValue, newValue) -> {

                        // 1. Si el usuario borra el número (celda vacía), quitamos el borde rojo
                        if (newValue.isEmpty()) {
                            establecerEstiloBase(celda, f, c);
                            return;
                        }

                        // 2. HU-3: Restricción estricta de entrada (Solo permite números del 1 al 6)
                        if (!newValue.matches("[1-6]")) {
                            celda.setText(oldValue); // Revierte el cambio si escribe letras, 0, 7, etc.
                            return;
                        }

                        int numIngresado = Integer.parseInt(newValue);

                        // 3. HU-4: Comprobación con el modelo y feedback visual (Borde Rojo)
                        if (modelo.verificarNumero(f, c, numIngresado)) {
                            establecerEstiloBase(celda, f, c); // Corrección o acierto: vuelve al estilo normal
                        } else {
                            ponerBordeRojo(celda, f, c); // Error: se resalta en rojo
                        }
                    });
                }

                tableroSudoku.add(celda, columna, fila);
                celdas[fila][columna] = celda;
            }
        }
    }

    // Método auxiliar para el estilo normal (Bordes negros de 1px)
    private void establecerEstiloBase(TextField celda, int fila, int columna) {
        int bloqueFila = fila / 2;
        int bloqueColumna = columna / 3;
        String baseStyle = "-fx-font-size: 18px; -fx-alignment: center; -fx-border-width: 1; -fx-border-color: black;";

        if ((bloqueFila + bloqueColumna) % 2 == 0) {
            celda.setStyle(baseStyle + "-fx-background-color: #E0E0E0;");
        } else {
            celda.setStyle(baseStyle + "-fx-background-color: white;");
        }
    }

    // Método auxiliar para el estilo de error (Bordes rojos de 2px)
    private void ponerBordeRojo(TextField celda, int fila, int columna) {
        int bloqueFila = fila / 2;
        int bloqueColumna = columna / 3;
        String estiloError = "-fx-font-size: 18px; -fx-alignment: center; -fx-border-width: 2; -fx-border-color: red;";

        if ((bloqueFila + bloqueColumna) % 2 == 0) {
            celda.setStyle(estiloError + "-fx-background-color: #E0E0E0;");
        } else {
            celda.setStyle(estiloError + "-fx-background-color: white;");
        }
    }
}
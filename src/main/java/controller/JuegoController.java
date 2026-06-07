package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SudokuModel;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Random;

public class JuegoController {

    @FXML private GridPane tableroSudoku;
    @FXML private Label lblAyudas;

    private TextField[][] celdas;
    private SudokuModel modelo;
    private TextField celdaSeleccionada;
    private int ayudasRestantes = 5;

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

                    // Guarda la celda actualmente seleccionada
                    celda.focusedProperty().addListener((obs, oldVal, newVal) -> {
                        if (newVal) {
                            celdaSeleccionada = celda;
                        }
                    });

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
        lblAyudas.setText("" + ayudasRestantes);
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
    @FXML
    private void borrarCelda() {
        if (celdaSeleccionada != null) {
            celdaSeleccionada.clear();
        }
    }
    @FXML
    private void nuevoJuego() {

        modelo = new SudokuModel();

        int[][] tableroInicial = modelo.getTableroInicial();

        ayudasRestantes = 5;
        lblAyudas.setText("" + ayudasRestantes);

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {

                establecerEstiloBase(
                        celdas[fila][columna],
                        fila,
                        columna);

                if (tableroInicial[fila][columna] != 0) {

                    celdas[fila][columna].setText(
                            String.valueOf(tableroInicial[fila][columna])
                    );

                    celdas[fila][columna].setEditable(false);

                    celdas[fila][columna].setStyle(
                            celdas[fila][columna].getStyle() +
                                    "-fx-font-weight: bold;"
                    );

                } else {

                    celdas[fila][columna].setText("");

                    celdas[fila][columna].setEditable(true);
                }

            }
        }

        celdaSeleccionada = null;
    }
    @FXML
    private void usarAyuda() {


        if (ayudasRestantes <= 0) {
            return;
        }

        Random random = new Random();

        int fila;
        int columna;

        do {
            fila = random.nextInt(6);
            columna = random.nextInt(6);

        } while (!celdas[fila][columna].getText().isEmpty()
            || !celdas[fila][columna].getPromptText().isEmpty());

        int sugerencia =
                modelo.getTableroResuelto()[fila][columna];

        celdas[fila][columna].setPromptText(
                String.valueOf(sugerencia)
        );
        ayudasRestantes--;

        lblAyudas.setText(
                "" + ayudasRestantes
        );
    }
    public void BotonRegresar(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/InicioView.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        double width = stage.getWidth();
        double height = stage.getHeight();

        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setWidth(width);
        stage.setHeight(height);

        stage.show();
    }
}
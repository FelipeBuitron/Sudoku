package model;

import java.util.Random;

public class SudokuModel {

    // 1. Aquí guardamos la matriz con la solución exacta de tu imagen (el 6 amarillo incluido)
    private final int[][] tableroResuelto = {
            {1, 6, 4, 5, 3, 2},
            {3, 2, 5, 4, 1, 6},
            {6, 4, 3, 1, 2, 5},
            {5, 1, 2, 6, 4, 3},
            {2, 5, 1, 3, 6, 4},
            {4, 3, 6, 2, 5, 1}
    };

    // 2. Esta matriz guardará los números que el jugador verá al iniciar (los demás serán 0)
    private int[][] tableroInicial;

    public SudokuModel() {
        tableroInicial = new int[6][6]; // Se crea lleno de ceros por defecto
        generarTableroInicial();
    }

    // 3. Método para revelar 2 números aleatorios por cada bloque de 2x3
    private void generarTableroInicial() {
        Random random = new Random();

        // El tablero 6x6 tiene 3 bloques a lo alto y 2 a lo ancho
        for (int bloqueFila = 0; bloqueFila < 3; bloqueFila++) {
            for (int bloqueCol = 0; bloqueCol < 2; bloqueCol++) {

                int colocados = 0;

                // Mientras no hayamos colocado 2 números en este bloque específico...
                while (colocados < 2) {
                    // Escogemos una coordenada al azar dentro del bloque (2 filas, 3 columnas)
                    int filaAleatoria = random.nextInt(2);
                    int colAleatoria = random.nextInt(3);

                    // Calculamos la posición real en la matriz grande de 6x6
                    int filaReal = (bloqueFila * 2) + filaAleatoria;
                    int colReal = (bloqueCol * 3) + colAleatoria;

                    // Si la celda está vacía (tiene un 0), copiamos el número de la solución
                    if (tableroInicial[filaReal][colReal] == 0) {
                        tableroInicial[filaReal][colReal] = tableroResuelto[filaReal][colReal];
                        colocados++;
                    }
                }
            }
        }
    }

    // Métodos para que el controlador pueda pedirle estos tableros al modelo
    public int[][] getTableroInicial() {
        return tableroInicial;
    }

    public int[][] getTableroResuelto() {
        return tableroResuelto;
    }
}
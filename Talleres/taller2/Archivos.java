package aed;

import java.util.Scanner;
import java.io.PrintStream;

class Archivos {
    float[] leerVector(Scanner entrada, int largo) {
        float[] res = new float[largo];
        for (int i = 0; i < largo; i++){
            res[i] = entrada.nextFloat();
        }
        return res;
    }

    float[][] leerMatriz(Scanner entrada, int filas, int columnas) {  // filas; columnas son el tamaño que tienen.
        float[][] res = new float[filas][columnas];
        for (int i = 0; i < filas; i++){
            res[i] = leerVector(entrada, columnas);
        }
        return res;
    }

    void imprimirPiramide(PrintStream salida, int alto) {
        for (int i = 1; i <= alto; i++){
            if (i != 1) salida.print("\n");
            salida.print(concatenarNVecesSubstring(" ", alto - i));
            salida.print(concatenarNVecesSubstring("*", 2*i - 1));
            salida.print(concatenarNVecesSubstring(" ", alto - i));
        }

        // i desde 1 incluido hasta alto incluido
        // empieza con n-1 espacios, * n - 1 espacios
        // luego con n-2 espacios, *** n - 2 espacios
        // luego con n-i espacios, (i-1)+1+(i-1) = 2i - 1 asteriscos; n-i asteriscos 
        // hasta i = n y (n-1)*2 + 1 asteriscos

        salida.close();
        
    }

    String concatenarNVecesSubstring(String str, int n) {
        String res = "";
        for (int i = 0; i < n; i++){
            res += str;
        }
        return res;
    }
}

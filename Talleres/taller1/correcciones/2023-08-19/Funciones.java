package aed;

class Funciones {
    int cuadrado(int x) {
        return x * x;
    }

    double distancia(double x, double y) {
        return Math.sqrt(x*x+y*y);
    }

    boolean esPar(int n) {
        return (n % 2) == (0);
    }

    boolean esBisiesto(int n) {
        return (((n % 4 == 0) && !(n % 100 == 0)) || ((n % 400) == 0));
    }

    int factorialIterativo(int n) {
        int res = 1;
        for(int i = 1; i <= n; i++){
            res *= i;
        }
        return res;
    }

    int factorialRecursivo(int n) {
        if (n == 0){
            return 1;
        } else {
        return n * factorialIterativo(n - 1);
        }
    }

    boolean esPrimo(int n) {
        boolean res = true;
        int i = 2;

        if (n < 2){
            res = false;
        }

        while (i <= Math.sqrt(n) && res){
            if(n % i == 0) {
                res = false;
            }
            i++;
        }
        return res;
    }

    int sumatoria(int[] numeros) {
        int res = 0;
        for(int elem : numeros){
            res += elem;
        }
        return res;
    }

    int busqueda(int[] numeros, int buscado) {
        for (int i = 0; i < numeros.length; i++){
            if(numeros[i] == buscado){
                return i;
            }
        }
        return -1; // En caso de que no este en la array (el requiere me asegura que siempre buscado pertenece a numeros)
    }

    boolean tienePrimo(int[] numeros) {
        for (int elem : numeros){
            if (esPrimo(elem)){
                return true;
            }
        }
        return false;
    }

    boolean todosPares(int[] numeros) {
        for (int elem : numeros){
            if (elem % 2 != 0){  // si hay algún elemento que no sea par, devuelve falso
                return false;
            }
        }
        return true;  // en caso que ninguno dio falso, devuelve true
    }

    boolean esPrefijo(String s1, String s2) {

        if (s1.length() > s2.length()){
            return false;
        }

        for (int i = 0; i < s1.length(); i++){
            if (s1.charAt(i) != s2.charAt(i)){
                return false;
            }
        }
        return true;
    }

    boolean esSufijo(String s1, String s2) {
        return esPrefijo(darVuelta(s1), darVuelta(s2));
    }

    String darVuelta(String str) {
        String res = "";
        for (int i = 0; i < str.length(); i++){
            res += str.charAt(str.length() - 1 - i);
        }
        return res;
    }
}

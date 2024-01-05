package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo raiz;
    private int cardinal;

    private class Nodo {
        private Nodo padre;
        private Nodo der;
        private Nodo izq;
        private T valor;

        // Crear Constructor del nodo
        Nodo(T v) {
            this.padre = null;
            this.der = null;
            this.izq = null;
            this.valor = v;
        }
    }

    public ABB() {
        this.raiz = null;
        this.cardinal = 0;
    }

    public int cardinal() {
        return this.cardinal;
    }

    public T minimo(){
        Nodo actual = this.raiz;
        while (actual.izq != null){
            actual = actual.izq;
        }
        return actual.valor;
    }

    public T maximo(){
        Nodo actual = this.raiz;
        while (actual.der != null){
            actual = actual.der;
        }
        return actual.valor;
    }

    public void insertar(T elem){
        if (!pertenece(elem)) {

            Nodo nuevo = new Nodo(elem);
            
            Nodo actual = this.raiz;
            Nodo anterior = null;
            while (actual != null){
                anterior = actual;
                if (actual.valor.compareTo(elem) > 0) {  // actual.valor > elem
                    actual = actual.izq;
                } else {
                    actual = actual.der;
                }
            }
            nuevo.padre = anterior;
            if (anterior == null){
                this.raiz = nuevo;
            } else if (anterior.valor.compareTo(nuevo.valor) > 0) {  // anterior.valor > elem)
                anterior.izq = nuevo;
            } else {
                anterior.der = nuevo;
            }
            cardinal += 1;
        }
    }

    public boolean pertenece(T elem){
        Nodo nodoFinal = busqueda_recursiva(this.raiz, elem);
        if (nodoFinal == null) return false;
        return true;
    }

    public Nodo busqueda_recursiva(Nodo actual, T elem){
        if (actual == null || elem.equals(actual.valor)) return actual;
        if (actual.valor.compareTo(elem) > 0) {  // actual.valor > elem
            return busqueda_recursiva(actual.izq, elem);
        } else {
            return busqueda_recursiva(actual.der, elem);
        }

    }



    
    public void eliminar(T elem){
        if (pertenece(elem)){  // Caso esta el elemento que queremos eliminar.
            
            // Busqueda del nodo a borrar y su anterior:
            Nodo actual = this.raiz;
            Nodo anterior = null;

            while(!actual.valor.equals(elem)){
                anterior = actual;
                if (actual.valor.compareTo(elem) > 0){  // actual.valor > elem
                    actual = actual.izq;
                } else {
                    actual = actual.der;
                }
            }

            // Actual es a borrar y anterior es su padre.

            if (actual.izq == null && actual.der == null){  // Caso no tiene descendencia, es una hoja:

                if(actual.equals(this.raiz)){  // CASO RAIZ
                    this.raiz = null;
                    // RESTO DE CASOS
                } else if (anterior.izq == actual){
                    anterior.izq = null;
                } else {  // anterior.der == actual
                    anterior.der = null;
                }
                this.cardinal -= 1;

            } else if (actual.izq == null && actual.der != null) {  // Caso tiene solo un hijo (derecho):

                if (actual.equals(this.raiz)){  // CASO RAIZ
                    this.raiz = actual.der;
                } else {
                // RESTO DE CASOS
                    if (anterior.izq == actual){
                        anterior.izq = actual.der;
                    } else {  // anterior.der == actual
                        anterior.der = actual.der;
                    }
                }
                this.cardinal -= 1;

            } else if (actual.izq != null && actual.der == null) {  // Caso tiene solo un hijo (izquierdo):

                if (actual.equals(this.raiz)){  // CASO RAIZ
                    this.raiz = actual.izq;
                } else {
                // RESTO DE CASOS
                    if (anterior.izq == actual){
                        anterior.izq = actual.izq;
                    } else {  // anterior.der == actual
                        anterior.der = actual.izq;
                    }
                }
                this.cardinal -= 1;

            } else {  // Caso tiene dos hijos.
                // Busco el sucesor inmediato de actual
                Nodo candidatoSucesor = actual.der;  // Como actual tiene dos hijos, en particular tiene hijo derecho.
                                                     // Es distinto de null entonces candidatoSucesor y por lo tanto candidatoSucesor.izq no se indefine.
                while (candidatoSucesor.izq != null){
                    candidatoSucesor = candidatoSucesor.izq;
                }

                T valorCopiado = candidatoSucesor.valor;
                eliminar(valorCopiado);
                actual.valor = valorCopiado;
            }
        }
    }




    public String toString(){
            String res;
            Iterador<T> iterador = iterador();

            res = "{";
            while(iterador.haySiguiente()){
                if (res != "{"){
                    res += ",";
                }
                res += (iterador.siguiente().toString());
            }
            res += "}";
            return res;
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;
        private int contador = 0;

        public boolean haySiguiente() {            
            return contador < cardinal;
        }
    
        public T siguiente() {
            if (_actual == null){  // Inicializar
                Nodo mirando = raiz;
                while (mirando.izq != null){
                    mirando = mirando.izq;
                }
                _actual = mirando;
            } else {
                _actual = sucesor(_actual);
            }
            contador += 1;
            return _actual.valor;
        }

        public Nodo sucesor(Nodo x){
            if (x.der != null){
                // Minimo del subarbol derecho.
                Nodo mirando = x.der;
                while (mirando.izq != null){
                    mirando = mirando.izq;
                }
                return mirando;
            }
            Nodo y = x.padre;
            while (y != null && x.equals(y.der)){
                x = y;
                y = y.padre;
            }
            return y;
        }
    }


    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}

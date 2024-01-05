package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    private class Nodo {
        T valor;
        Nodo siguiente;
        Nodo anterior;
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        longitud = 0;
    }

    public int longitud() {
        return longitud;
    }

    public void agregarAdelante(T elem) {  // Meto en el primer lugar
        Nodo nuevo = new Nodo();
        if (primero != null){
        nuevo.valor = elem;
        nuevo.siguiente = primero;
        nuevo.anterior = null;
        primero.anterior = nuevo;
        primero = nuevo;
        } else {  // Quiero que ultimo y primero apunten al mismo Nodo, este caso no hay nada y quiero agregar un Nodo.
            nuevo.valor = elem;
            nuevo.siguiente = null;
            nuevo.anterior = null;
            primero = nuevo;
            ultimo = nuevo;
        }
        longitud++;
    }

    public void agregarAtras(T elem) {  // Meto en el ultimo lugar
        Nodo nuevo = new Nodo();
        
        if (primero != null){
        nuevo.valor = elem;
        nuevo.siguiente = null;
        nuevo.anterior = ultimo;
        ultimo.siguiente = nuevo;
        ultimo = nuevo;
        } else {  // Quiero que ultimo y primero apunten al mismo Nodo, este caso no hay nada y quiero agregar un Nodo.
            nuevo.valor = elem;
            nuevo.siguiente = null;
            nuevo.anterior = null;
            primero = nuevo;
            ultimo = nuevo;
        }
        longitud++;
    }




    public T obtener(int i) {
        Nodo actual = primero;
        for (int contador = 0; contador < i; contador++){
            actual = actual.siguiente;
        }
        return actual.valor;
    }

    public void eliminar(int i) {
        if (longitud() == 1){
            primero = null;
            ultimo = null;

        } else if (i == 0){
            primero.siguiente.anterior = null;
            primero = primero.siguiente;

        } else if (i == longitud() - 1) {
            ultimo.anterior.siguiente = null;
            ultimo = ultimo.anterior;

        } else {
        
        Nodo actual = primero;
        for (int contador = 0; contador < i; contador++){
            actual = actual.siguiente;
        }
        // actual ahora es el nodo a eliminar
        actual.anterior.siguiente = actual.siguiente;
        actual.siguiente.anterior = actual.anterior;
        }
        longitud -= 1;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primero;
        for(int i=0; i < indice; i++){
            actual = actual.siguiente;
        }
        actual.valor = elem;
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> nuevo = new ListaEnlazada<T>();
        Nodo actual = primero;
        for (int contador = 0; contador < longitud(); contador++){
            nuevo.agregarAtras(actual.valor);
            actual = actual.siguiente;
        }
        return nuevo;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        ListaEnlazada<T> antiguo = lista.copiar();
        primero = antiguo.primero;
        ultimo = antiguo.ultimo;
        longitud = antiguo.longitud;
    }
    
    @Override
    public String toString() {
        String res = "[";
        for (int contador = 0; contador < longitud(); contador++){
            res = res.concat(obtener(contador).toString());
            if (contador != longitud() - 1){
                res = res.concat(", ");
            }
        }
        res = res.concat("]");
        return res;
    }

    private class ListaIterador implements Iterador<T> {
    	int dedito = 0;

        public boolean haySiguiente() {
	        return dedito < longitud;
        }
        
        public boolean hayAnterior() {
	        return dedito > 0;
        }

        public T siguiente() {
            T res = obtener(dedito);
            dedito++;
            return res;
        }
        

        public T anterior() {  // Por alguna razón siguiente y anterior funcionan distinto en el orden de operaciones.
            dedito--;
            T res = obtener(dedito);
            return res;
        }
    }

    public Iterador<T> iterador() {
	    return new ListaIterador();
    }

}

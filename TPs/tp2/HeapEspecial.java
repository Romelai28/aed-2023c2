package aed;

public class HeapEspecial {
    public Nodo[] arr;

    // OBS: |arr| es equivalente a la cantidad de elementos en nuestro heapEspecial. Podríamos llamarlo n.

    public HeapEspecial(Nodo[] arr){  // O(|arr|)
        this.arr = arr;  // O(1) pasa la referencia
        array2Heap(this.arr);  // O(|arr|), que en la practica por aca solo pasan arr de magnitud P entonces seria O(P)
    }

    private int hijo_izq(int i) {return 2*i+1;}  // O(1)

    private int hijo_der(int i) {return 2*i+2;}  // O(1)
    
    private void bajar(int i){  // La complejidad en el peor caso es O(Altura del heap), como el heap esta perfectamente balanceado salvo el último nivel, su altura es del orden log(|arr|)
        int masGrande = i;  // O(1)
        int hijoIzq = hijo_izq(i);  // O(1)
        int hijoDer = hijo_der(i);  // O(1)

        if (hijoIzq < this.arr.length && this.arr[hijoIzq].votosCocientizados > this.arr[masGrande].votosCocientizados){    // Evaluar guarda: O(1). La rama más compleja es O(1)
            masGrande = hijoIzq;  // O(1)
        }
        if (hijoDer < this.arr.length && this.arr[hijoDer].votosCocientizados > this.arr[masGrande].votosCocientizados){  // Evaluar guarda: O(1). La rama más compleja es O(1)
            masGrande = hijoDer;  // O(1)
        }
        if (masGrande != i){  // Evaluar guarda: O(1).
            swap(i, masGrande);  // O(1)
            bajar(masGrande);  // Notar que se va a llamar la función bajar a lo sumo una cantidad de veces del orden de la altura del heap.
        }
    }

    public void array2Heap(Nodo[] arreglo){  // La complejidad en el peor caso es O(|arr|) dado a que:
        // Es una implementación del algoritmo de Floyd para construir un heap a partir de un array de elementos.
        for(int i=(this.arr.length/2)-1; i>=0; i--){
            bajar(i);
        }
    }

    public int procesarUnaBancaDiputado(){  // La complejidad en el peor caso es O(log(|arr|)).
        // Desencola, modifica al cociente y votosCocientizado y luego lo vuelve a encolar. Devuelve su id_partido.
        int partido=this.arr[0].partido;  // O(1)
        Nodo nuevo=new Nodo(partido,this.arr[0].votosTotales/(this.arr[0].cociente+1),this.arr[0].votosTotales,this.arr[0].cociente+1);  // O(1)
        this.arr[0]=nuevo;  // O(1)
        bajar(0);  // O(log(|arr|))
        return partido;  // O(1)
    }

    private void swap(int i, int j){  // La complejidad en el peor caso es O(1).
        Nodo temporal = this.arr[j];  // O(1)
        this.arr[j] = this.arr[i];  // O(1)
        this.arr[i] = temporal;  // O(1)
    }
}

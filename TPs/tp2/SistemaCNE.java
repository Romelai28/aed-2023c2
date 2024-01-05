package aed;

public class SistemaCNE {
    String[] partidos;  // Nombres partidos
    String[] distritos;  // Nombre distritos
    int[] votosPresidenciales;
    int[][] votosDiputados;
    int[] ultimaMesaPorDistrito;
    int primero; // id del primero (presidencial)
    int segundo; // id del segundo (presidencial)
    int votosTotalesPresidenciales;
    int[] votosTotalesDiputadosPorDistrito;
    HeapEspecial[] arrDeHeapDiputados;  // Va a ser una array de D elementos que cada uno de ellos son referencias de tipo heapEspecial's.
    // Cada elemento de tipo heapEspecial tendrá información para cualcular las bancas que se le asignaran a cada diputados por distritos. 
    int[] diputadosPorDistrito;  // Me dice la cantidad de diputados en disputa en cada distrito
    int[][] memoBancasPorDistrito;  // Tiene (tal vez) los resultados correctos de las asignaciones de bancas a cada partido de diputados en cada distrito.
    boolean[] memoFlag;  // Me dice si en el distrito que estoy considerando el resultado en memoBancasPorDistrito es válido.

    public class VotosPartido{
        private int presidente;
        private int diputados;

        VotosPartido(int presidente, int diputados){  // O(1)
            this.presidente = presidente;  // O(1)
            this.diputados = diputados;  // O(1)
        }
        public int votosPresidente(){return presidente;}  // O(1)
        public int votosDiputados(){return diputados;}  // O(1)
    } 

    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) {
        // La complejidad en el peor caso es O(D*P) por ser sumas de O(1), O(P), O(D), O(D*P).
        this.partidos=nombresPartidos;  // O(1)
        this.distritos=nombresDistritos;  // O(1)
        this.votosPresidenciales= new int[this.partidos.length]; // O(P)
        this.votosDiputados=new int[this.distritos.length][this.partidos.length];  // O(D*P)
        this.ultimaMesaPorDistrito=ultimasMesasDistritos;  // O(1)
        this.primero=0;  // O(1)
        this.segundo=1;  // O(1)
        this.votosTotalesPresidenciales= 0;  // O(1)
        this.votosTotalesDiputadosPorDistrito= new int[this.distritos.length];  // O(D)
        this.memoBancasPorDistrito = new int[distritos.length][partidos.length-1];  // O(D*P)
        this.memoFlag = new boolean[distritos.length];  // O(D)
        this.arrDeHeapDiputados = new HeapEspecial[this.distritos.length];  // O(D)
        this.diputadosPorDistrito=diputadosPorDistrito;  // O(1)
    }

    public String nombrePartido(int idPartido) {  // La complejidad en el peor caso O(1)
        return partidos[idPartido];  // O(1) porque devuelve una referencia a una array.
    }

    public String nombreDistrito(int idDistrito) {  // La complejidad en el peor caso O(1)
        return distritos[idDistrito];  // O(1) porque devuelve una referencia a una array.
    }

    public int diputadosEnDisputa(int idDistrito) {  // La complejidad en el peor caso O(1)
        return diputadosPorDistrito[idDistrito];  // O(1) porque devuelve una referencia a una array.
    }
    
    public String distritoDeMesa(int idMesa) {  // La complejidad en el peor caso O(1)
        return this.distritos[indiceDistritoMesa(idMesa)];  // O(1) porque devuelve una referencia a una array.
    }

    private int indiceDistritoMesa(int idMesa) {  // La complejidad en el peor caso O(log(D)).
        // Binary Search adaptada para este problema por su particularidad de los indices que devuelve.
        // arr ordenada crecientemente.
        // Por el requiere sabemos que elem pertenece entre 0 incluido y arr[arr.length - 1].
        int izq = -1;  // O(1)
        // Todos los elementos a la izquierda de izq incluido son < al elemento
        int der = this.ultimaMesaPorDistrito.length;  // O(1)
        // Todos los elementos a la derecha de der incluido son >= al elemento
        while (der-izq > 1) {  // la diferencia entre der-izq se va reduciendo por cada iteración del ciclo por la mitad. Por lo tanto,
            // el ciclo se va a ejecutar a lo sumo una cantidad del orden logaritmico de veces (log_2(D)). El cuerpo solamente contiene operaciones O(1)
            // Por lo tanto, su suma también es O(1). La complejidad del ciclo en el peor caso es O(log(D)).
            // El rango activo es [izq+1 hasta der-1]
            int medio = (izq + der) / 2;  // O(1)  -cuerpo del bucle-
            if (this.ultimaMesaPorDistrito[medio] < idMesa){  // Evaluar guarda: O(1). La rama más compleja es O(1)
                izq = medio;  // O(1)
            } else {
                der = medio;  // O(1)
            }
        }
        if (this.ultimaMesaPorDistrito[der] == idMesa){  // Evaluar guarda: O(1). La rama más compleja es O(1) // Notar que sin el requiere se rompe aca!
            return der + 1;  // O(1)
        } else {
            return der;  // O(1)
        }
    }

    private void actualizarPrimeroSegundo(){  // La complejidad en el peor caso O(P).
        // Sumando las complejidades tenemos suma de O(1) + O(P) = O(P).
        this.primero = 0;  // O(1)
        this.segundo = 1;  // O(1)
        for(int i=0; i < this.partidos.length - 1; i++){  // El cuepo del for ejecuta del orden de P veces. Su cuerpo es O(1) por lo tanto la complejidad del ciclo for es O(P)
            if (votosPresidenciales[i] > votosPresidenciales[primero]){  // Evaluar guarda: O(1). La rama más compleja es O(1)
                this.segundo = this.primero;  // O(1)
                this.primero = i;  // O(1)
            } else if (votosPresidenciales[i] > votosPresidenciales[segundo] && i != primero){  // O(1). La rama más compleja es O(1)
                this.segundo = i;  // O(1)
            }
        }
    }

    private float porcentajeVotosPresidenciales(int idPartido){  // La complejidad en el peor caso O(1).
        return (this.votosPresidenciales(idPartido) * 100) / this.votosTotalesPresidenciales;  // O(1)
    }
    
    private float porcentajeVotosDiputados(int idDistrito, int idPartido){  // La complejidad en el peor caso O(1).
        return (this.votosDiputados(idPartido, idDistrito) * 100) / this.votosTotalesDiputadosPorDistrito[idDistrito];  // O(1)
    }

    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {  // La complejidad en el peor caso O(log(D) + P) dado que:
        // Es suma de algunos O(P), O(log(D)) y O(1).
        int indiceDistritoMesa = indiceDistritoMesa(idMesa); // O(log(D)) 
        for(int i=0; i < this.partidos.length; i++){  // El for se ejecuta P veces. Tiene solamente operaciones O(1) por lo tanto su complejidad es O(P)
            this.votosPresidenciales[i] += actaMesa[i].votosPresidente();  // O(1)
            this.votosTotalesPresidenciales += actaMesa[i].votosPresidente();  // O(1)
            this.votosDiputados[indiceDistritoMesa][i] += actaMesa[i].votosDiputados();  // O(1)
            this.votosTotalesDiputadosPorDistrito[indiceDistritoMesa] += actaMesa[i].votosDiputados();  // O(1)
            if (i!=this.partidos.length-1) {this.memoBancasPorDistrito[indiceDistritoMesa][i] = 0;}  // O(1)
        }
        actualizarPrimeroSegundo();  // O(P)
        this.memoFlag[indiceDistritoMesa] = false;  // O(1)
        Nodo[] arrDeNodos = new Nodo[this.partidos.length - 1];  // O(P)
        for (int i=0; i < this.partidos.length - 1; i++){  // El cuepo del for ejecuta del orden de P veces. Su cuerpo es O(1) por lo tanto la complejidad del ciclo for es O(P)
            if(porcentajeVotosDiputados(indiceDistritoMesa, i) >= 3){ // Evaluar guarda: O(1). La rama más compleja es O(1)
                arrDeNodos[i] = new Nodo(i, votosDiputados(i, indiceDistritoMesa), votosDiputados(i, indiceDistritoMesa), 1);  // O(1)
            } else {
                arrDeNodos[i] = new Nodo(i,-1,-1, 1);  // O(1) // No se les pueden asignar bancas porque no superan el 3%.
            }
        }
        this.arrDeHeapDiputados[indiceDistritoMesa]= new HeapEspecial(arrDeNodos); // O(P)
    }

    public int votosPresidenciales(int idPartido){  // La complejidad en el peor caso O(1).
        return votosPresidenciales[idPartido];  // O(1)
    }

    public int votosDiputados(int idPartido, int idDistrito){  // La complejidad en el peor caso O(1).
        return votosDiputados[idDistrito][idPartido];  // O(1)
    }

    public int[] resultadosDiputados(int idDistrito){  // La complejidad en el peor caso O(D_d*log(P)).
        if (!this.memoFlag[idDistrito]){ // Evaluar guarda: O(1). La rama más compleja es O(D_d*log(P))
            for(int i=0; i < this.diputadosPorDistrito[idDistrito]; i++){  // El for se ejecuta D_d veces. Su cuerpo es de complejidad O(log(P)) por lo tanto el ciclo tiene complejidad O(D_d*log(P))
                int partidoSuma=this.arrDeHeapDiputados[idDistrito].procesarUnaBancaDiputado();  // O(log(P)) -cuerpo-
                this.memoBancasPorDistrito[idDistrito][partidoSuma] +=1;  // O(1) -cuerpo-
            }
            this.memoFlag[idDistrito] = true;  // O(1)
        }
        return this.memoBancasPorDistrito[idDistrito];  // O(1) porque devuelve la referencia.
    }

    public boolean hayBallotage(){  // La complejidad en el peor caso O(1).
        if (this.partidos.length < 3) {return false;}  // O(1)
        return !(porcentajeVotosPresidenciales(this.primero) >= 45 || (porcentajeVotosPresidenciales(this.primero) >= 40 && porcentajeVotosPresidenciales(this.primero) - porcentajeVotosPresidenciales(this.segundo) > 10));
        // Como porcentajeVotosPresidenciales habiamos visto que era O(1), la linea del return hace solamente operaciones O(1).
    }
}


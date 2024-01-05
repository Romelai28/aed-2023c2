package aed;

public class Nodo{
    int partido;  // id_partido.
    int votosTotales;
    int votosCocientizados;
    int cociente;
        
public Nodo(int partido, int votoCocientizado, int votosTotales, int cociente){  // La complejidad en el peor caso es O(1)
    this.partido=partido;  // O(1)
    this.votosCocientizados=votoCocientizado;  // O(1)
    this.votosTotales=votosTotales;  // O(1)
    this.cociente=cociente;  // O(1)
    }
        
public int votosTotales(){  // La complejidad en el peor caso es O(1)
    return this.votosTotales;  // O(1)
    }
}
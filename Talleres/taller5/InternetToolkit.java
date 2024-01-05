package aed;

import java.util.LinkedList;
import java.util.List;

public class InternetToolkit {
    public InternetToolkit() {
    }
    public Fragment[] tcpReorder(Fragment[] fragments) {
        for (int i=1; i<fragments.length; i++){
            // Desde 0 hasta i sin incluir esta ordenada.
            Fragment valor = fragments[i];
            int j = i-1;
            while (j>=0 && fragments[j].compareTo(valor) > 0){
                fragments[j+1] = fragments[j];
                j--;
            }
            fragments[j+1] = valor;
        }
        return fragments;
    }

    public Router[] kTopRouters(Router[] routers, int k, int umbral) {
        
        int cantSuperaElUmbral = 0;
        for (int i=0; i<routers.length; i++){
            if (routers[i].getTrafico() >= umbral) {cantSuperaElUmbral += 1;}
        }

        Router[] arrSuperaElUmbral = new Router[cantSuperaElUmbral];
        int j = 0;
        for (int i=0; i<routers.length; i++){
            if (routers[i].getTrafico() >= umbral){
                arrSuperaElUmbral[j] = routers[i];
                j++;
            }
        }

        PriorityQueueAcotada<Router> routersPrioridad = new PriorityQueueAcotada<Router>(routers.length);  // MINheap
        routersPrioridad.array2Heap(arrSuperaElUmbral);

        Router[] res = new Router[k];

        int i = 0;
        while (i<k && !routersPrioridad.estaVacio()){
            res[i] = routersPrioridad.desencolar();
            i++;
        }

        return res;
    }

    public IPv4Address[] sortIPv4(String[] ipv4) {
        
        IPv4Address[] arr_original = new IPv4Address[ipv4.length];
        
        for (int i=0; i<arr_original.length; i++){
            arr_original[i] = new IPv4Address(ipv4[i]);
        }

        for (int num_octeto = 3; num_octeto>=0; num_octeto--){
            ListaEnlazada<IPv4Address>[] bucket = new ListaEnlazada[256];
            for (int l=0; l<bucket.length; l++){
                bucket[l] = new ListaEnlazada<IPv4Address>();
            }

            for (int i=0; i<arr_original.length; i++){
                IPv4Address direccion = arr_original[i];
                bucket[direccion.getOctet(num_octeto)].agregarAtras(direccion);
            }

            int k = 0;
            for(int i=0; i<bucket.length; i++){
                Iterador<IPv4Address> iterador = bucket[i].iterador();
                while(iterador.haySiguiente()){
                    arr_original[k] = iterador.siguiente();
                    k++;
                }
            }
        }

        return arr_original;
    }

}

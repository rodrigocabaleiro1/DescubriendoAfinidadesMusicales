package pruebas;

import java.util.List;

import negocio.*;

public class PruebaGrafo {
    public static void main(String[] args) {
        Usuario[] usuarios = {
            new Usuario("Ana", 5, 2, 3, 1),
            new Usuario("Luis", 3, 2, 4, 5),
            new Usuario("Maria", 4, 1, 2, 3),
            new Usuario("Pedro", 2, 5, 1, 4)
        };

        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        indice.MostrarSimilaridades();

        Grafo grafo = new Grafo(indice.getMatriz());
        List<Arista> mst = grafo.obtenerArbolGeneradorMinimo();
        System.out.println("\nÁrbol Generador Mínimo:");
        for (Arista a : mst) {
            System.out.println(a.toString());
        }

        System.out.println("\nGrupos luego de eliminar la arista más pesada:");
//        List<List<Usuario>> grupos = grafo.dividirEnGrupos(mst);
//        for (int i = 0; i < grupos.size(); i++) {
//            System.out.println("Grupo " + (i+1) + ":");
//            for (Usuario u : grupos.get(i)) {
//                System.out.println("  " + u.nombre());
//            }
//        }
    }
}

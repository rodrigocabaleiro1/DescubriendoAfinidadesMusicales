package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grafo {
    private Usuario[] usuarios;
    private List<Arista> aristas;

    public Grafo(Usuario[] usuarios, int[][] matrizSimilaridad) {
        this.usuarios = usuarios;
        this.aristas = construirAristas(matrizSimilaridad);
    }

    // Generar lista de aristas a partir de la matriz de similaridad
    private List<Arista> construirAristas(int[][] matriz) {
        List<Arista> lista = new ArrayList<>();
        for (int i = 0; i < usuarios.length; i++) {
            for (int j = i + 1; j < usuarios.length; j++) {
                lista.add(new Arista(usuarios[i], usuarios[j], matriz[i][j]));
            }
        }
        return lista;
    }

    // Algoritmo de Kruskal para encontrar el Árbol Generador Mínimo
    public List<Arista> arbolGeneradorMinimo() {
        List<Arista> resultado = new ArrayList<>();
        Collections.sort(aristas); // ordenar por peso
        UnionFind uf = new UnionFind(usuarios.length);

        for (Arista arista : aristas) {
            int i = indexOf(arista.getU1());
            int j = indexOf(arista.getU2());

            if (uf.union(i, j)) { // si une dos componentes diferentes
                resultado.add(arista);
            }
        }
        return resultado;
    }

    // Eliminar la arista de mayor peso del MST
    public List<List<Usuario>> dividirEnGrupos(List<Arista> mst) {
        if (mst.isEmpty()) return null;

        // Encontrar la arista de mayor peso
        Arista max = Collections.max(mst);
        mst.remove(max);

        // Reconstruir componentes a partir del MST sin esa arista
        UnionFind uf = new UnionFind(usuarios.length);
        for (Arista a : mst) {
            int i = indexOf(a.getU1());
            int j = indexOf(a.getU2());
            uf.union(i, j);
        }

        // Agrupar usuarios por componente
        List<List<Usuario>> grupos = new ArrayList<>();
        for (int i = 0; i < usuarios.length; i++) {
            int root = uf.find(i);
            boolean agregado = false;
            for (List<Usuario> g : grupos) {
                if (uf.find(indexOf(g.get(0))) == root) {
                    g.add(usuarios[i]);
                    agregado = true;
                    break;
                }
            }
            if (!agregado) {
                List<Usuario> nuevo = new ArrayList<>();
                nuevo.add(usuarios[i]);
                grupos.add(nuevo);
            }
        }

        return grupos;
    }

    // Buscar índice de un usuario en el array
    private int indexOf(Usuario u) {
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i].equals(u)) return i;
        }
        return -1;
    }
}

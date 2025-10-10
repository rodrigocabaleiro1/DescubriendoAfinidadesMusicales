package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Grafo {
    private List<Arista> aristas;
	private int cantidadNodos;
    private List<Arista> arbolGeneradorMinimo;

    public Grafo(int[][] pesoAristas) {
    	this.cantidadNodos = pesoAristas.length;
    	this.aristas = new ArrayList<>();
    	this.arbolGeneradorMinimo = new ArrayList<>();
        construirAristas(pesoAristas);
        generarArbolGeneradorMinimo();
    }
  
    private void construirAristas(int[][] matriz) {
        for (int i = 0; i < cantidadNodos; i++) {
            for (int j = i; j < cantidadNodos; j++) {
            	if(nodosDistintos(i,j)) {
            		Arista actual = new Arista(i, j, matriz[i][j]);
            		this.aristas.add(actual);
            	}
            }
        }
    }

    private boolean nodosDistintos(int i, int j) {
		return i != j;
	}

	// Algoritmo de Kruskal para encontrar el Árbol Generador Mínimo
    public void generarArbolGeneradorMinimo() {
    	HashSet<Integer> nodosAgregados = new HashSet<Integer>();
        Collections.sort(aristas); // ordenar por peso
        for (Arista aristaActual : aristas) {
        	if(nodosNoAgregados(aristaActual, nodosAgregados)) {
        		arbolGeneradorMinimo.add(aristaActual);
        		nodosAgregados.add((Integer) aristaActual.getUsuario1());
        		nodosAgregados.add((Integer) aristaActual.getUsuario2());
        	}
        }
    }

    private boolean nodosNoAgregados(Arista aristaActual, HashSet<Integer> nodosAgregados) {
		return !(nodosAgregados.contains(aristaActual.getUsuario1()) 
				&& nodosAgregados.contains(aristaActual.getUsuario2()));
	}

    
    public List<Arista> eliminarAristaDeMayorPeso(List<Arista> aristas){
    	ordenarAristasDeMenorAMayor(aristas);
    	eliminarUltimoElemento(aristas);
    	return aristas;
    }

	private void eliminarUltimoElemento(List<Arista> aristas) {
		aristas.remove(aristas.size()-1);
	}

	private void ordenarAristasDeMenorAMayor(List<Arista> aristas) {
		Collections.sort(aristas);
	}
	
	// Eliminar la arista de mayor peso del MST
    public List<List<Usuario>> dividirEnGrupos(List<Arista> arbolGeneradorMinimo) {
        if (arbolGeneradorMinimo.isEmpty()) return null;

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
    
    public List<Arista> obtenerArbolGeneradorMinimo(){
    	return new ArrayList<Arista>(this.arbolGeneradorMinimo);
    }
    
    
}

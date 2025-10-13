package negocio;

import java.util.*;

public class Grafo<T> {
    private Map<Integer, T> vertices;
    private Map<Integer, Map<Integer, Integer>> aristas;

    public Grafo() {
        vertices = new HashMap<>();
        aristas = new HashMap<>();
    }
    public void agregarVertice(int id, T dato) {
        if (vertices.containsKey(id))
            throw new IllegalArgumentException("El vértice " + id + " ya existe.");
        vertices.put(id, dato);
        aristas.put(id, new HashMap<>());
    }

    public void eliminarVertice(int id) {
        if (!vertices.containsKey(id))
            throw new IllegalArgumentException("El vértice no existe.");
        vertices.remove(id);
        aristas.remove(id);
        
        for (Map<Integer, Integer> conexiones : aristas.values()) {
            conexiones.remove(id);
        }
    }

    public void modificarVertice(int id, T nuevoDato) {
        if (!vertices.containsKey(id))
            throw new IllegalArgumentException("El vértice no existe.");
        vertices.put(id, nuevoDato);
    }

    public boolean existeVertice(int id) {
        return vertices.containsKey(id);
    }

    public T obtenerDatoVertice(int id) {
        return vertices.get(id);
    }

    public Collection<Integer> obtenerVertices() {
        return vertices.keySet();
    }

    public void agregarArista(int origen, int destino, int peso) {
        if (!vertices.containsKey(origen) || !vertices.containsKey(destino))
            throw new IllegalArgumentException("Alguno de los vértices no existe.");
        if (origen == destino)
            return;
        aristas.get(origen).put(destino, peso);
        aristas.get(destino).put(origen, peso);
    }

    public void eliminarArista(int origen, int destino) {
        if (aristas.containsKey(origen)) aristas.get(origen).remove(destino);
        if (aristas.containsKey(destino)) aristas.get(destino).remove(origen);
    }

    public boolean existeArista(int origen, int destino) {
        return aristas.containsKey(origen) && aristas.get(origen).containsKey(destino);
    }

    public Integer obtenerPeso(int origen, int destino) {
        if (!existeArista(origen, destino)) return null;
        return aristas.get(origen).get(destino);
    }

    public List<Integer> obtenerVecinos(int id) {
        if (!vertices.containsKey(id)) return new ArrayList<>();
        return new ArrayList<>(aristas.get(id).keySet());
    }

    public List<Arista> arbolExpansionMinimo() {
        List<Arista> todas = new ArrayList<>();

        for (var entry : aristas.entrySet()) {
            int origen = entry.getKey();
            for (var destinoPeso : entry.getValue().entrySet()) {
                int destino = destinoPeso.getKey();
                int peso = destinoPeso.getValue();

                if (origen < destino) {
                    todas.add(new Arista(origen, destino, peso));
                }
            }
        }

        todas.sort(Comparator.comparingInt(Arista::obtenerPeso));

        Kruskal kruskal = new Kruskal(vertices.size());
        List<Arista> mst = new ArrayList<>();

        for (Arista arista : todas) {
            int origen = arista.obtenerOrigen();
            int destino = arista.obtenerDestino();

            if (kruskal.union(origen, destino)) {
                mst.add(arista);
            }
        }

        return mst;
    }


    public List<Set<Integer>> particionarEnKComponentes(int k) {
        if (k < 1) throw new IllegalArgumentException("k debe ser mayor o igual a 1");

        List<Arista> mst = arbolExpansionMinimo();

        mst.sort((a, b) -> Integer.compare(b.obtenerPeso(), a.obtenerPeso()));

        Kruskal conjuntos = new Kruskal(vertices.size());

        for (int i = k - 1; i < mst.size(); i++) {
            Arista arista = mst.get(i);
            conjuntos.union(arista.obtenerOrigen(), arista.obtenerDestino());
        }

        Map<Integer, Set<Integer>> componentes = new HashMap<>();
        for (Integer vertice : vertices.keySet()) {
            int raiz = conjuntos.find(vertice);
            componentes.computeIfAbsent(raiz, x -> new HashSet<>()).add(vertice);
        }

        return new ArrayList<>(componentes.values());
    }
    
    public void generarAPartirDeMatrizDeSimilaridad(int[][] matriz, Map<Integer, T> verticesConDatos) {
        this.vertices.clear();
        this.aristas.clear();

        for (Map.Entry<Integer, T> entry : verticesConDatos.entrySet()) {
            agregarVertice(entry.getKey(), entry.getValue());
        }

        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int peso = matriz[i][j];
                agregarArista(i, j, peso);
            }
        }
    }

    public void mostrarAristas() {
        for (var entry : aristas.entrySet()) {
            int origen = entry.getKey();
            for (var destinoPeso : entry.getValue().entrySet()) {
                System.out.println(origen + " - " + destinoPeso.getKey() +
                        " (" + destinoPeso.getValue() + ")");
            }
        }
    }
}

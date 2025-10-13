package negocio;

public class Arista implements Comparable<Arista> {
    private int origen;
    private int destino;
    private int peso;

    public Arista(int origen, int destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    // Getters
    public int obtenerOrigen() {
        return origen;
    }

    public int obtenerDestino() {
        return destino;
    }

    public int obtenerPeso() {
        return peso;
    }

    // Setters
    public void establecerPeso(int nuevoPeso) {
        this.peso = nuevoPeso;
    }

    // Para poder ordenar las aristas por peso (necesario para Kruskal)
    @Override
    public int compareTo(Arista otra) {
        return Integer.compare(this.peso, otra.peso);
    }

    // Para mostrar por consola de forma clara
    @Override
    public String toString() {
        return "Arista [origen=" + origen + ", destino=" + destino + ", peso=" + peso + "]";
    }

    // Para comparar aristas por contenido
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Arista)) return false;
        Arista otra = (Arista) obj;
        return this.origen == otra.origen && this.destino == otra.destino && this.peso == otra.peso;
    }

    @Override
    public int hashCode() {
        return origen * 31 + destino * 17 + peso;
    }
}

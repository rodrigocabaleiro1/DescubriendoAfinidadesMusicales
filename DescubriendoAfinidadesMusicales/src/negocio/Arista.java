package negocio;

public class Arista implements Comparable<Arista> {
    private Usuario u1;
    private Usuario u2;
    private int peso;

    public Arista(Usuario u1, Usuario u2, int peso) {
        this.u1 = u1;
        this.u2 = u2;
        this.peso = peso;
    }

    public Usuario getU1() { return u1; }
    public Usuario getU2() { return u2; }
    public int getPeso() { return peso; }

    @Override
    public int compareTo(Arista otra) {
        return Integer.compare(this.peso, otra.peso);
    }

    @Override
    public String toString() {
        return u1.nombre() + " - " + u2.nombre() + " (" + peso + ")";
    }
}

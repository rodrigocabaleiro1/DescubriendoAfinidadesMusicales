package negocio;

public class Arista <T> implements Comparable<Arista>, Cloneable {
    private T idUsuario1;
    private T idUsuario2;
    private int peso;

    public Arista(T usuario1, T usuario2, int peso) {
    	validarArista(usuario1,usuario2);
        this.idUsuario1 = usuario1;
        this.idUsuario2 = usuario2;
        this.peso = peso;
    }

    private void validarArista(T usuario1, T usuario2) {
    	if(datoNulo(usuario1) || datoNulo(usuario1)) {
    		throw new IllegalArgumentException("¡ERROR! No es posible crear una arista si hay usuarios con valor nulo");
    	}
		if(soloHayUnUsuario(usuario1, usuario2)) {
			throw new IllegalArgumentException("¡ERROR! No es posible crear una arista para un unico usuario");
		}
	}
    
    private boolean datoNulo(T dato) {
		return dato == null;
	}

	private boolean soloHayUnUsuario(T usuario1, T usuario2) {
		
    	return usuario1.equals(usuario2);
    }

	public T getUsuario1() { return this.idUsuario1; }
    public T getUsuario2() { return this.idUsuario2; }
    public int getPeso() { return peso; }

    @Override
    public int compareTo(Arista otra) {
        return Integer.compare(this.peso, otra.peso);
    }

    @Override
    public String toString() {
        return idUsuario1 + " - " + idUsuario2 + " (" + peso + ")";
    }
    
    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}

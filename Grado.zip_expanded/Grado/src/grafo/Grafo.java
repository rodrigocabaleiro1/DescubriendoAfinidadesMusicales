package grafo;

import java.util.HashSet;
import java.util.Set;

public class Grafo {
	private boolean [][] A;
	
	public Grafo (int vertices) {

		A = new boolean [vertices] [vertices];
	}
	public void agregarArista (int i, int j) {
		verticeMuyChico(i);
		verticeMuyChico(j);
		verticeMuyGrande(i);
		verticeMuyGrande(j);
		iIgualJ(i,j);
		A [i][j] = true;
		A [j][i] = true;
	}
	
	public void eliminarArista (int i, int j) {
		verticeMuyChico(i);
		verticeMuyChico(j);
		verticeMuyGrande(i);
		verticeMuyGrande(j);
		iIgualJ(i,j);
		
		A [i][j] = false;
		A [j][i] = false;
	}
	public Set<Integer> vecinos(int i){
	
		
		Set <Integer> ret = new HashSet<Integer>();

		if(A.length ==0){
			return ret;
		}
		verticeMuyChico(i);
		verticeMuyGrande(i);
		for (int j = 0 ; j< tamanio(); j++) {
			if (existeArista(i,j)) {
				ret.add(j);
			}
		}
		return ret;
	}
	
	public boolean existeArista (int i, int j){
		return A[i][j];
	}
	public int tamanio() {
		return A.length;
	}
	
	@Override
	public String toString() {
		return this.A.toString();
	}
	
	//COMPROBACIONES-----------------------
	
	private void iIgualJ(int i, int j) {
		if (i== j) {
			throw new IllegalArgumentException("No se puede agregar una arista entre dos vertices iguales");
		}
		
	}
	private void verticeMuyChico(int i) {
		if (i<0) {
			throw new IllegalArgumentException("los vertices deben ser mayores a 0. Dato ingresado: " + i);
		}
	}
	private void verticeMuyGrande(int i) {
		if (i>= A.length) {
			throw new IllegalArgumentException("los vertices deben ser menores a " + (A.length -1) +".Dato Ingresado: " + i);
		}		}
}

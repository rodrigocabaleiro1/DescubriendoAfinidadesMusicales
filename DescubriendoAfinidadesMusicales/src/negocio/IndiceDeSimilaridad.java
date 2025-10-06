package negocio;

import java.util.List;

public class IndiceDeSimilaridad {
	
	private int [][] similaridadUsuarios;
	
	private Usuario[] usuarios;
	private int cantidadUsuarios;
	
	public IndiceDeSimilaridad(Usuario[] usuarios) {
		this.usuarios = usuarios;
		cantidadUsuarios = usuarios.length;
		similaridadUsuarios = new int [cantidadUsuarios][cantidadUsuarios];
		calcularSimilaridadUsuarios();
	}

	private void calcularSimilaridadUsuarios() {
		int similaridadActual;
		int usuariosAnalizados = 0;
		for(Usuario actual: usuarios) {
			for (int i = 0 + usuariosAnalizados; i < cantidadUsuarios; i++) {
				similaridadActual = calcularSimilaridad(actual, usuarios[i]);
				establecerSimilaridad(similaridadActual, i, usuariosAnalizados);
			}
			usuariosAnalizados ++;
		}
	}

	private void establecerSimilaridad(int similaridadActual, int i, int j) {
		similaridadUsuarios[i][j] = similaridadActual;
		similaridadUsuarios[j][i] = similaridadActual;
	}

	private int calcularSimilaridad(Usuario primario, Usuario secundario) {
		int folclore, tango, rockNacional, urbano, similaridad;
		
		folclore = modulo(primario.interesFolclore() - secundario.interesFolclore());
		tango = modulo(primario.interesTango() - secundario.interesTango());
		rockNacional = modulo(primario.interesRockNacional() - secundario.interesRockNacional());
		urbano = modulo(primario.interesUrbano() - secundario.interesUrbano());
		
		similaridad = folclore + tango + rockNacional + urbano;
		return similaridad;
	}

	private int modulo(int numero) {
		return Math.abs(numero);
	}
	
	public void MostrarSimilaridades() {
		for(int i = 0 ; i< cantidadUsuarios; i++) {
			System.out.print(usuarios[i].nombre() + " | ");
			for (int j = 0; j < cantidadUsuarios; j++) {
				System.out.print(similaridadUsuarios[i][j] + "  ");
			}
			System.out.println("");
		}
		
	}
	public int[][] getMatriz() {
	    return similaridadUsuarios;
	}

}

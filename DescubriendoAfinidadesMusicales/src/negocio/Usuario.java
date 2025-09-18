package negocio;

public class Usuario {
	private int interesFolclore, interesTango, interesRockNacional, interesUrbano;
	private String nombre;
	
	public Usuario(String nombre) {
		this.nombre = nombre;
		interesFolclore = 1;
		interesTango = 1;
		interesRockNacional = 1;
		interesUrbano = 1;
	}
	
	public void incrementarInteresFolclore() {
		validarInteresAlto(interesFolclore);
		interesFolclore++;
	}
	
	public void incrementarInteresTango() {
		validarInteresAlto(interesTango);
		interesTango++;
	}
	
	public void incrementarInteresRockNacional() {
		validarInteresAlto(interesRockNacional);
		interesRockNacional++;
	}
	
	public void incrementarInteresUrbano() {
		validarInteresAlto(interesUrbano);
		interesUrbano++;
	}
	
	public void disminuirInteresFolclore() {
		validarInteresBajo(interesFolclore);
		interesFolclore--;
	}
	
	public void disminuirInteresTango() {
		validarInteresBajo(interesTango);
		interesTango--;
	}
	
	public void disminuirInteresRockNacional() {
		validarInteresBajo(interesRockNacional);
		interesRockNacional--;
	}
	
	public void disminuirInteresUrbano() {
		validarInteresBajo(interesUrbano);
		interesUrbano--;
	}
	
	//---------------------------------------------
	//	Validaciones
	//---------------------------------------------
	public void validarInteresBajo(int interesAnalizado) {
		if(interesAnalizado <= 1) {
			throw new RuntimeException("¡ERROR! El interes en el area musical seleccionada ya esta al MINIMO posible");
		}
	}
	public void validarInteresAlto(int interesAnalizado) {
		if(interesAnalizado >= 5) {
			throw new RuntimeException("¡ERROR! El interes en el area musical seleccionada ya esta al MAXIMO posible");
		}
	}
	
	@Override 
	public String toString() {
		StringBuilder texto = new StringBuilder();
		texto.append("Usuario: " + nombre + "\n");
		texto.append("--------------------------\n");
		texto.append("_Interes por el Tango: " + interesTango + "\n");
		texto.append("_Interes por el Folclore: " + interesFolclore + "\n");
		texto.append("_Interes por el Rock Nacional: " + interesRockNacional + "\n");
		texto.append("_Interes por el Genero Urbano: " + interesUrbano + "\n");
		
		return texto.toString();
	}
}


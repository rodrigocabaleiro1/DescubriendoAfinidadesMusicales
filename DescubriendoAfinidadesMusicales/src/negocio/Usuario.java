package negocio;

public class Usuario {
	private int interesFolclore, interesTango, interesRockNacional, interesUrbano;
	private String nombre;
	
	public Usuario(String nombre, int interesFolclore, int interesTango, 
			int interesRockNacional, int interesUrbano) {
		validarIntereses(interesFolclore, interesTango, interesRockNacional, interesUrbano);
		this.nombre = nombre;
		this.interesFolclore = interesFolclore;
		this.interesTango = interesTango;
		this.interesRockNacional = interesRockNacional;
		this.interesUrbano = interesUrbano;
	}
	
	public String nombre() {
		return nombre;
	}
	
	public int interesFolclore() {
		return interesFolclore;
	}
	
	public int interesTango() {
		return interesTango;
	}
	
	public int interesRockNacional() {
		return interesRockNacional;
	}
	
	public int interesUrbano() {
		return interesUrbano;
	}
	

	private void validarIntereses(int interesFolclore, int interesTango, 
			int interesRockNacional, int interesUrbano) {
		validarInteresBajo(interesFolclore, "Interes Folclore");
		validarInteresAlto(interesFolclore, "Interes Folclore");
		
		validarInteresBajo(interesTango, "Interes Tango");
		validarInteresAlto(interesTango, "Interes Tango");
		
		validarInteresBajo(interesRockNacional, "Interes Rock Nacional");
		validarInteresAlto(interesRockNacional, "Interes Rock Nacional");
		
		validarInteresBajo(interesUrbano, "Interes Urbano");
		validarInteresAlto(interesUrbano, "Interes Urbano");
	}
	
	
	private void validarInteresBajo(int interesAnalizado, String nombreInteres) {
		if(interesAnalizado < 1) {
			throw new IllegalArgumentException("¡ERROR! El dato '"+ nombreInteres + "' no puede ser menor a 1. "
					+ "DatoIngresado: " + interesAnalizado);
		}
	}
	private void validarInteresAlto(int interesAnalizado, String nombreInteres) {
		if(interesAnalizado > 5) {
			throw new IllegalArgumentException("¡ERROR! El dato '"+ nombreInteres + "' no puede ser mayor a 5. "
					+ "DatoIngresado: " + interesAnalizado);
		}
	}
	
	//---------------------------------------------
	// Override
	//---------------------------------------------
	@Override 
	public String toString() {
		StringBuilder texto = new StringBuilder();
		texto.append("--------------------------\n");
		texto.append("Usuario: " + nombre + "\n");
		texto.append("--------------------------\n");
		texto.append("_Interes por el Tango: " + interesTango + "\n");
		texto.append("_Interes por el Folclore: " + interesFolclore + "\n");
		texto.append("_Interes por el Rock Nacional: " + interesRockNacional + "\n");
		texto.append("_Interes por el Genero Urbano: " + interesUrbano + "\n");
		
		return texto.toString();
	}
}


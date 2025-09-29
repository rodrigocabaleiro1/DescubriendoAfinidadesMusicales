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
	
	public Usuario(String nombre, int interesFolclore, int interesTango, 
			int interesRockNacional, int interesUrbano) {
		//validarDatos(nombre, interesFolclore, interesTango, interesRockNacional, interesUrbano);
		this.nombre = nombre;
		this.interesFolclore = interesFolclore;
		this.interesTango = interesTango;
		this.interesRockNacional = interesRockNacional;
		this.interesUrbano = interesUrbano;
	}

	private int convertirNumero(String numero) {
		return Integer.parseInt(numero);
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
	
	//---------------------------------------------
	//	Validaciones
	//---------------------------------------------
	private void validarDatos(String nombre, String interesFolclore, String interesTango,
			String interesRockNacional, String interesUrbano) {
		validarDato(nombre, "nombre");
		validarInteres(interesFolclore, "interesFolclore");
		validarInteres(interesTango, "interesTango");
		validarInteres(interesUrbano, "interesUrbano");
		validarInteres(interesRockNacional, "interesRockNacional");
	}

	private void validarDato(String dato, String nombreDato) {
		if(datoVacio(dato)) {
			throw new IllegalArgumentException ("el dato '" + nombreDato + "' no puede estar vacio");
		}
		
	}

	private boolean datoVacio(String dato) {
		return dato.length() == 0 || dato == "" || dato == " " || dato == null;
	}

	private void validarInteres(String interes, String nombreInteres) {
		validarDato(interes, nombreInteres);
		validarValorInteres(interes, nombreInteres);
	}

	private void validarValorInteres(String interes, String nombreInteres) {
		esNumero(interes, nombreInteres);
		int numero = convertirNumero(interes);
		
	}

	private void esNumero(String interes, String nombreInteres) {
		if (noEsNumero(interes)) {
			throw new NumberFormatException ("el dato '" + nombreInteres + "' debe ser un Número");
		}
		
	}

	private boolean noEsNumero(String interes) {
		try {
	        int n = convertirNumero(interes);
	        return false;
	    } catch (NumberFormatException e) {
	        return true;
	    }
	}
	
	public void validarInteresBajo(int interesAnalizado, String nombreInteres) {
		if(interesAnalizado < 1) {
			throw new IllegalArgumentException("¡ERROR! El dato '"+ nombreInteres + "' no puede ser menor a 1. "
					+ "DatoIngresado: " + interesAnalizado);
		}
	}
	public void validarInteresAlto(int interesAnalizado, String nombreInteres) {
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


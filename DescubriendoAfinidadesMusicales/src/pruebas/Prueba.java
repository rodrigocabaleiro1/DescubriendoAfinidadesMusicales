package pruebas;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import negocio.Usuario;

public class Prueba {
	ArrayList <Usuario> usuarios = new ArrayList<Usuario>();
	public void cargarUsuarios(){
		List<String> usuariosCargados = cargarUsuarios("usuarios.txt");
		crearUsuarios(usuariosCargados);
		System.out.println(usuarios);
	}

	private void crearUsuarios(List<String> usuariosCargados) {
		HashMap <String,String> datosUsuario = new HashMap<String, String>();
		
		
		for(String linea : usuariosCargados) {
			if (!(linea.contains("-") || linea.contains("Usuarios"))) {

				analizarLinea(linea, datosUsuario);
				if(datosCompletosUsuario(datosUsuario)) {
					crearUsuario(datosUsuario);
				}
				
			}else {
				datosUsuario = new HashMap<String, String>();
			}
		}
		
	}

	

	private void crearUsuario(HashMap<String, String> datosUsuario) {
		String nombre, interesFolclore, interesTango, interesRockNacional, interesUrbano;
		nombre = datosUsuario.get("nombre");
		interesFolclore = datosUsuario.get("interesFolclore");
		interesTango = datosUsuario.get("interesTango");
		interesRockNacional = datosUsuario.get("interesRockNacional");
		interesUrbano = datosUsuario.get("interesUrbano");
		
		Usuario usuario = new Usuario(nombre, interesFolclore, interesTango, interesRockNacional, interesUrbano);
		usuarios.add(usuario);
		
	}

	private void analizarLinea(String linea, HashMap<String, String> datosUsuario) {
		String nombreDato = datoActual(linea);
		guardarDato(datosUsuario, nombreDato, extraerDato(linea));
	}
	
	private String datoActual(String linea) {
		if(linea.contains("nombre")) {
			return "nombre";
		}
		
		else if(linea.contains("interesFolclore")) {
			return "interesFolclore";
		}
		
		else if(linea.contains("interesTango")) {
			return "interesTango";
		}
		
		else if(linea.contains("interesRockNacional")) {
			return "interesRockNacional";
		}
		
		else if(linea.contains("interesUrbano")) {
			return "interesUrbano";
		}
		else {
			return "";
		}
	}

	private String extraerDato(String linea) {
		String dato;
		int igualPosicion = obtenerPosicionCaracter(linea, '=');
		dato = quitarEspacios(textoAPartirDePosicion(linea, igualPosicion + 1)); 
		return dato;
	}

	private String quitarEspacios(String texto) {
		return texto.trim(); //eliminar espacios de extremos
	}

	private String textoAPartirDePosicion(String texto, int posicion) {
		return texto.substring(posicion);
	}

	public List<String> cargarUsuarios(String archivo) {
		List<String> lineas;
		try {
            //FileWriter writer = new FileWriter("usuarios.txt");
            lineas = Files.readAllLines(Paths.get("usuarios.txt"));
            return lineas;

        } catch (IOException e) {
            System.out.println("Error ocurrido durante el manejo del archivo de texto.");
            e.printStackTrace();
        }
		lineas = new ArrayList<String>();
		return lineas;
		
	}
	
	private boolean datosCompletosUsuario(HashMap<String, String> datosUsuario) {
		boolean datosCompletos = true;
		datosCompletos &= existeDato(datosUsuario, "nombre");
		datosCompletos &= existeDato(datosUsuario, "interesFolclore");
		datosCompletos &= existeDato(datosUsuario, "interesTango");
		datosCompletos &= existeDato(datosUsuario, "interesRockNacional");
		datosCompletos &= existeDato(datosUsuario, "interesUrbano");
		return datosCompletos;
	}
	
	private boolean existeDato(HashMap<String, String> datosUsuario, String dato) {
		return datosUsuario.containsKey(dato);
	}

	private void guardarDato(HashMap<String, String> mapa, String nombreDato, String dato) {
		mapa.put(nombreDato, dato);
	}
	private int obtenerPosicionCaracter(String texto, char caracter) {
		return texto.indexOf(caracter);
	}
	
}

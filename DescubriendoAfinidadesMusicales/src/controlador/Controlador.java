package controlador;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import negocio.Usuario;
import pantalla.AltaUsuario;

public class Controlador {
	private AltaUsuario pantallaAltaUsuario;
	public void altaUsuario(String nombre, int interesFolclore, int interesTango, int interesRockNacional, int interesUrbano) throws Exception {
		Usuario usuario = new Usuario(nombre,interesFolclore,interesTango,interesRockNacional,interesUrbano);
		
		guardarUsuario(usuario);
	}

	private void guardarUsuario(Usuario usuario) throws IOException {
		Usuario[] usuarios = cargarUsuarios();
		Gson gson = new Gson();
		FileWriter writer = new FileWriter("usuarios.json");
		for(Usuario actual: usuarios) {
			gson.toJson(actual, writer);
		}
		gson.toJson(usuario, writer);
		writer.close();
	}
	
	public Usuario[] cargarUsuarios() {
		Gson archivoUsuarios = new Gson();
		try (FileReader reader = new FileReader("usuarios.json")) {
	        Usuario[] usuarios = archivoUsuarios.fromJson(reader, Usuario[].class);
	      return usuarios;  
	        
	    } catch (Exception e) {
	        System.out.println(e);
	    }
		return null;
	}
	
	public void mostrarPantallaAltaUsuario() {
		pantallaAltaUsuario = new AltaUsuario(this);
		pantallaAltaUsuario.setVisible(true);
	}
}

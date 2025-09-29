package pruebas;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import negocio.IndiceDeSimilaridad;
import negocio.Usuario;

public class Prueba {
	
	
	public Usuario[] cargarUsuarios() {
		Gson archivoUsuarios = new Gson();
		try (FileReader reader = new FileReader("usuarios.json")) {
	        Usuario[] usuarios = archivoUsuarios.fromJson(reader, Usuario[].class);

	        for (Usuario u : usuarios) {
	            //System.out.println(u.toString());
	        }
	      return usuarios;  
	        
	    } catch (Exception e) {
	        System.out.println(e);
	    }
		return null;
	}
}

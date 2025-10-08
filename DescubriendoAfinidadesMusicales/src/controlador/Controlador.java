package controlador;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import negocio.Usuario;
import pantalla.AltaUsuario;
import pantalla.MenuPrincipal;
import pantalla.VisualizarUsuarios;

public class Controlador {
    private MenuPrincipal menuPrincipal;
    private AltaUsuario pantallaAltaUsuario;
    private VisualizarUsuarios pantallaVisualizarUsuarios;
    private Map <String, Usuario> usuarios;
    private final String ruta = "usuarios.json";
    private final Gson generadorJson = new Gson(); 
    
    
    public Controlador() {
    	usuarios = new HashMap<String, Usuario>();
    	cargarUsuarios();
    }
    // ======== MÉTODOS DE INICIO ========
    public void iniciarAplicacion() {
        menuPrincipal = new MenuPrincipal(this);
        menuPrincipal.setVisible(true);
    }

    // ======== MENÚ PRINCIPAL ========
    public void mostrarPantallaAltaUsuario() {
        if (pantallaAltaUsuario == null) {
            pantallaAltaUsuario = new AltaUsuario(this);
        }
        pantallaAltaUsuario.setVisible(true);
    }

    public void mostrarPantallaVisualizarUsuarios() {
        if (pantallaVisualizarUsuarios == null) {
            pantallaVisualizarUsuarios = new VisualizarUsuarios(this);
        }
        pantallaVisualizarUsuarios.actualizarListaUsuarios(obtenerNombreUsuarios());
        pantallaVisualizarUsuarios.setVisible(true);
    }

    public void volverAlMenuPrincipal() {
        if (menuPrincipal == null) {
            menuPrincipal = new MenuPrincipal(this);
        }
        menuPrincipal.setVisible(true);
    }

    // ======== GESTIÓN DE USUARIOS ========
    public void altaUsuario(String nombre, int interesFolclore, int interesTango, int interesRockNacional, int interesUrbano) throws Exception {
        Usuario usuario = new Usuario(nombre, interesFolclore, interesTango, interesRockNacional, interesUrbano);
        guardarUsuario(usuario);
    }

    private void guardarUsuario(Usuario usuario) throws IOException {
        agregarUsuario(usuario);       
        almacenarUsuarios();
	}

	private void agregarUsuario(Usuario usuario) {
		this.usuarios.put(usuario.nombre(), usuario);
	}

	private void almacenarUsuarios() throws IOException {
		Collection<Usuario> coleccionUsuarios = this.usuarios.values(); 
		try (FileWriter fw = new FileWriter(ruta)) {
        	generadorJson.toJson(coleccionUsuarios, fw);
        	}
	}

    public void cargarUsuarios() {
		try (FileReader reader = new FileReader(ruta)) {
			Usuario[] arregloUsuarios;
			arregloUsuarios = generadorJson.fromJson(reader, Usuario[].class);
			cargarUsuarios(arregloUsuarios);
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}

    private void cargarUsuarios(Usuario[] arregloUsuarios) {
    	if(!arregloVacio(arregloUsuarios)) {
    		for(Usuario actual: arregloUsuarios) {
    			agregarUsuario(actual);
    		}
    	}
	}

	public void eliminarUsuario(String nombre) throws IOException {
    	validarEliminarUsuario(nombre);
        usuarios.remove(nombre);
        almacenarUsuarios();
    }
	
	public Set<String> obtenerNombreUsuarios() {
		return usuarios.keySet();
	}

	private void validarEliminarUsuario(String nombre) {
		if(!existeUsuario(nombre)) {
    		throw new RuntimeException("¡ERROR! No es posible eliminar el usuario: " + nombre + " ya que no existe.");
    	}
	}

	private boolean existeUsuario(String nombre) {
		return this.usuarios.containsKey(nombre);
	}
	
	private boolean arregloVacio(Usuario[] arreglo) {
		return arreglo == null || arreglo.length == 0;
	}

}

package controlador;

import java.awt.Point;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import negocio.Arista;
import negocio.Grafo;
import negocio.IndiceDeSimilaridad;
import negocio.Usuario;
import pantalla.AltaUsuario;
import pantalla.MenuPrincipal;
import pantalla.VisualizarUsuarios;
import pantalla.VistaGrafo;

public class Controlador {
	private static int usuariosCreados = 0;
    private MenuPrincipal menuPrincipal;
    private AltaUsuario pantallaAltaUsuario;
    private VisualizarUsuarios pantallaVisualizarUsuarios;
    private VistaGrafo pantallaGrafo;
    private Map <String, Usuario> usuarios;
    private final String ruta = "usuarios.json";
    private final Gson generadorJson = new Gson();
    private Grafo<String> grafo;
    
    
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
        pantallaVisualizarUsuarios.actualizarListaUsuarios(obtenerIdsUsuarios());
        pantallaVisualizarUsuarios.setVisible(true);
    }

    public void volverAlMenuPrincipal() {
        if (menuPrincipal == null) {
            menuPrincipal = new MenuPrincipal(this);
        }
        menuPrincipal.setVisible(true);
    }
    
    public void mostrarPantallaGrafo() {
    	if(grafo ==null) {
    		grafo = new Grafo<>();
    	}
    	if (pantallaGrafo == null) {
    		pantallaGrafo = new VistaGrafo(this);
    	}
    	pantallaGrafo.setVisible(true);
    	//probarGrafoUsuarios();
    }
    
    
 // =================== PRUEBA DE GRAFO ===================
    public void probarGrafoUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados para generar el grafo.");
            return;
        }
        Usuario[] arregloUsuarios = usuarios.values().toArray(new Usuario[0]);
        
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(arregloUsuarios);
        
        Map<Integer, Usuario> mapaIndices = new HashMap<>();
        for (int i = 0; i < arregloUsuarios.length; i++) {
            mapaIndices.put(i, arregloUsuarios[i]);
        }

        Grafo<Integer> grafo = new Grafo<>();
        for (int i = 0; i < arregloUsuarios.length; i++) {
            grafo.agregarVertice(i);
        }
        int[][] matriz = indice.obtenerIndice();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = i + 1; j < matriz[i].length; j++) {
                grafo.agregarArista(i, j, matriz[i][j]);
            }
        }

        System.out.println("Grafo generado a partir de la matriz de similaridad:");
        Set<String> aristasMostradas = new HashSet<>();
        for (int origenId : grafo.obtenerVertices()) {
            for (int destinoId : grafo.obtenerVecinos(origenId)) {
                String key = origenId < destinoId ? origenId + "-" + destinoId : destinoId + "-" + origenId;
                if (!aristasMostradas.contains(key)) {
                    Usuario origen = mapaIndices.get(origenId);
                    Usuario destino = mapaIndices.get(destinoId);
                    int peso = grafo.obtenerPeso(origenId, destinoId);
                    System.out.println(origen.nombre() + " → " + destino.nombre() + " | Peso: " + peso);
                    aristasMostradas.add(key);
                }
            }
        }
    }
    
    public List<Point> obtenerAristas(){
    	 Usuario[] usuarios = this.usuarios.values().toArray(new Usuario[0]);
         IndiceDeSimilaridad similaridades = new IndiceDeSimilaridad(usuarios);
         this.grafo.generarAPartirDeMatrizDeSimilaridad(similaridades.obtenerIndice(), similaridades.usuariosPorIndice());
         List<Arista> aristasAGM = grafo.arbolGeneradorMinimo();
         List<Point> aristas = new ArrayList<>();
         for(Arista actual: aristasAGM) {
        	 int origen = actual.obtenerOrigen();
        	 int destino = actual.obtenerDestino();
        	 aristas.add(new Point(origen, destino));
         }
         return aristas; 
    }
    
    // ======== GESTIÓN DE USUARIOS ========
    public void altaUsuario(String nombre, int interesFolclore, int interesTango, int interesRockNacional, int interesUrbano) throws Exception {
        Usuario usuario = new Usuario("" + usuariosCreados,nombre, interesFolclore, interesTango, interesRockNacional, interesUrbano);
        guardarUsuario(usuario);
    }

    private void guardarUsuario(Usuario usuario) throws IOException {
        agregarUsuario(usuario);       
        almacenarUsuarios();
	}

	private void agregarUsuario(Usuario usuario) {
		 ++usuariosCreados;
		this.usuarios.put(usuario.obtenerID(), usuario);
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
			usuariosCreados += arregloUsuarios.length;
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

	public void eliminarUsuario(String clave) throws IOException {
    	validarEliminarUsuario(clave);
        usuarios.remove(clave);
        almacenarUsuarios();
    }
	
	
	public Set<String> obtenerIdsUsuarios() {
		return this.usuarios.keySet();
	}

	public String nombreUsuario(String clave) {
		return this.usuarios.get(clave).nombre();
	}
	
	private void validarEliminarUsuario(String clave) {
		if(!existeUsuario(clave)) {
    		throw new RuntimeException("¡ERROR! No es posible eliminar el usuario: " + clave + " ya que no existe.");
    	}
	}

	private boolean existeUsuario(String clave) {
		return this.usuarios.containsKey(clave);
	}
	
	private boolean arregloVacio(Usuario[] arreglo) {
		return arreglo == null || arreglo.length == 0;
	}

}

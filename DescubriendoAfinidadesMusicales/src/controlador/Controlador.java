package controlador;

import java.awt.Point;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;

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

    public final Map<String, Usuario> usuarios;
    private final String ruta = "usuarios.json";
    private final Gson generadorJson = new Gson();
    private Grafo<String> grafo;

    public Controlador() {
        usuarios = new HashMap<>();
        cargarUsuarios();
    }

    // ======== INICIO DE APLICACIÓN ========
    public void iniciarAplicacion() {
        menuPrincipal = new MenuPrincipal(this);
        menuPrincipal.setVisible(true);
    }

    // ======== MENÚ PRINCIPAL ========
    public void mostrarPantallaAltaUsuario() {
        if (pantallaAltaUsuario == null)
            pantallaAltaUsuario = new AltaUsuario(this);
        pantallaAltaUsuario.setVisible(true);
    }

    public void mostrarPantallaVisualizarUsuarios() {
        if (pantallaVisualizarUsuarios == null)
            pantallaVisualizarUsuarios = new VisualizarUsuarios(this);

        pantallaVisualizarUsuarios.actualizarListaUsuarios(obtenerIdsUsuarios());
        pantallaVisualizarUsuarios.setVisible(true);
    }

    public void volverAlMenuPrincipal() {
        if (menuPrincipal == null)
            menuPrincipal = new MenuPrincipal(this);
        menuPrincipal.setVisible(true);
    }

    public void mostrarPantallaGrafo() {
        if (grafo == null)
            grafo = new Grafo<>();

        if (pantallaGrafo == null)
            pantallaGrafo = new VistaGrafo(this);

        pantallaGrafo.setVisible(true);
    }

    // ======== GESTIÓN DE USUARIOS ========
    public void altaUsuario(String nombre, int interesFolclore, int interesTango,
                            int interesRockNacional, int interesUrbano) throws Exception {
        if (nombre == null || nombre.length()==0)
            throw new IllegalArgumentException("El nombre del usuario no puede ser nulo o vacío.");

        Usuario usuario = new Usuario(
                String.valueOf(usuariosCreados),
                nombre,
                interesFolclore,
                interesTango,
                interesRockNacional,
                interesUrbano
        );
        guardarUsuario(usuario);
    }

    private void guardarUsuario(Usuario usuario) throws IOException {
        if (usuario == null) return;
        agregarUsuario(usuario);
        almacenarUsuarios();
    }

    private void agregarUsuario(Usuario usuario) {
        if (usuario == null) return;
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
            Usuario[] arregloUsuarios = generadorJson.fromJson(reader, Usuario[].class);
            if (!arregloVacio(arregloUsuarios))
                cargarUsuarios(arregloUsuarios);

            usuariosCreados += (arregloUsuarios != null ? arregloUsuarios.length : 0);
        } catch (Exception e) {
            System.out.println("No se pudieron cargar usuarios: " + e.getMessage());
        }
    }

    private void cargarUsuarios(Usuario[] arregloUsuarios) {
        if (arregloUsuarios == null) return;
        for (Usuario actual : arregloUsuarios) {
            if (actual != null && actual.obtenerID() != null)
                agregarUsuario(actual);
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

    public String obtenerInformacionUsuario(String id) {
        Usuario u = this.usuarios.get(id);
        return (u != null) ? u.toString() : "Usuario no encontrado";
    }

    public String nombreUsuario(String clave) {
        Usuario u = this.usuarios.get(clave);
        return (u != null) ? u.nombre() : "Desconocido";
    }

    private void validarEliminarUsuario(String clave) {
        if (!existeUsuario(clave))
            throw new RuntimeException("¡ERROR! No es posible eliminar el usuario: " + clave + " ya que no existe.");
    }

    private boolean existeUsuario(String clave) {
        return this.usuarios.containsKey(clave);
    }

    private boolean arregloVacio(Usuario[] arreglo) {
        return arreglo == null || arreglo.length == 0;
    }

    // ======== MÉTODOS PARA EL GRAFO ========
    public Map<String, Usuario> obtenerUsuarios() {
        return usuarios; // permite acceder a los usuarios desde VistaGrafo
    }

    public List<Point> obtenerAristas() {
        Usuario[] usuariosArray = this.usuarios.values().toArray(new Usuario[0]);
        if (usuariosArray.length == 0) return Collections.emptyList();

        IndiceDeSimilaridad similaridades = new IndiceDeSimilaridad(usuariosArray);
        this.grafo.generarAPartirDeMatrizDeSimilaridad(similaridades.obtenerIndice(), similaridades.usuariosPorIndice());

        List<Arista> aristasAGM = grafo.arbolGeneradorMinimo();
        List<Point> aristas = new ArrayList<>();

        for (Arista actual : aristasAGM) {
            aristas.add(new Point(actual.obtenerOrigen(), actual.obtenerDestino()));
        }
        return aristas;
    }

    public String dividirAGMPorMayorPeso(List<Arista> grupo1, List<Arista> grupo2) {
        grupo1.clear();
        grupo2.clear();

        List<Arista> agm = grafo.arbolGeneradorMinimo();
        if (agm.isEmpty()) return "El AGM está vacío.";

        Arista aristaMax = agm.get(0);
        for (Arista a : agm) if (a.obtenerPeso() > aristaMax.obtenerPeso()) aristaMax = a;
        agm.remove(aristaMax);

        Grafo<Integer> grafoTemp = new Grafo<>();
        for (Integer v : grafo.obtenerVertices()) grafoTemp.agregarVertice(v);
        for (Arista a : agm) grafoTemp.agregarArista(a.obtenerOrigen(), a.obtenerDestino(), a.obtenerPeso());

        Set<Integer> visitados = new HashSet<>();
        for (Integer vertice : grafoTemp.obtenerVertices()) {
            if (!visitados.contains(vertice)) {
                List<Integer> componente = new ArrayList<>();
                dfs(grafoTemp, vertice, visitados, componente);

                List<Arista> aristasComponente = new ArrayList<>();
                for (Arista a : agm) {
                    if (componente.contains(a.obtenerOrigen()) && componente.contains(a.obtenerDestino())) {
                        aristasComponente.add(a);
                    }
                }

                if (grupo1.isEmpty()) grupo1.addAll(aristasComponente);
                else grupo2.addAll(aristasComponente);
            }
        }

        // Construir String con IDs reales
        StringBuilder sb = new StringBuilder();
        sb.append("Arista eliminada del AGM (destacada en ROJO): ")
          .append(obtenerIdPorIndice(aristaMax.obtenerOrigen())).append(" - ")
          .append(obtenerIdPorIndice(aristaMax.obtenerDestino()))
          .append(" | Peso: ").append(aristaMax.obtenerPeso()).append("\n\n");

        sb.append("Grupo 1 (AZUL):\n");
        if (grupo1.isEmpty()) sb.append("(Este grupo no contiene aristas)\n");
        else for (Arista a : grupo1)
            sb.append(obtenerIdPorIndice(a.obtenerOrigen())).append(" -> ")
              .append(obtenerIdPorIndice(a.obtenerDestino()))
              .append(" | Peso: ").append(a.obtenerPeso()).append("\n");

        sb.append("\nGrupo 2 (VERDE):\n");
        if (grupo2.isEmpty()) sb.append("(Este grupo no contiene aristas)\n");
        else for (Arista a : grupo2)
            sb.append(obtenerIdPorIndice(a.obtenerOrigen())).append(" -> ")
              .append(obtenerIdPorIndice(a.obtenerDestino()))
              .append(" | Peso: ").append(a.obtenerPeso()).append("\n");

        return sb.toString();
    }

    private String obtenerIdPorIndice(int indice) {
        List<String> ids = new ArrayList<>(usuarios.keySet());
        return ids.get(indice);
    }

    private void dfs(Grafo<Integer> g, Integer v, Set<Integer> visitados, List<Integer> componente) {
        visitados.add(v);
        componente.add(v);
        for (Integer vecino : g.obtenerVecinos(v)) {
            if (!visitados.contains(vecino)) dfs(g, vecino, visitados, componente);
        }
    }
}

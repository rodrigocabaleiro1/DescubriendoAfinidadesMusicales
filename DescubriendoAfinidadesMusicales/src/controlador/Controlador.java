package controlador;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import negocio.Usuario;
import pantalla.AltaUsuario;
import pantalla.MenuPrincipal;
import pantalla.VisualizarUsuarios;

public class Controlador {
    private MenuPrincipal menuPrincipal;
    private AltaUsuario pantallaAltaUsuario;
    private VisualizarUsuarios pantallaVisualizarUsuarios;

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
        pantallaVisualizarUsuarios.actualizarListaUsuarios(cargarUsuarios());
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
        Usuario[] usuarios = cargarUsuarios();
        if (usuarios == null) usuarios = new Usuario[0];

        Usuario[] nuevosUsuarios = new Usuario[usuarios.length + 1];
        System.arraycopy(usuarios, 0, nuevosUsuarios, 0, usuarios.length);
        nuevosUsuarios[usuarios.length] = usuario;

        try (FileWriter writer = new FileWriter("usuarios.json")) {
            Gson gson = new Gson();
            gson.toJson(nuevosUsuarios, writer);
        }
    }

    public Usuario[] cargarUsuarios() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("usuarios.json")) {
            return gson.fromJson(reader, Usuario[].class);
        } catch (Exception e) {
            return null;
        }
    }

    public void eliminarUsuario(String nombre) throws IOException {
        Usuario[] usuarios = cargarUsuarios();
        if (usuarios == null) return;

        Usuario[] nuevosUsuarios = java.util.Arrays.stream(usuarios)
                .filter(u -> !u.nombre().equalsIgnoreCase(nombre))
                .toArray(Usuario[]::new);

        try (FileWriter writer = new FileWriter("usuarios.json")) {
            Gson gson = new Gson();
            gson.toJson(nuevosUsuarios, writer);
        }
    }
}

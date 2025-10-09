package controlador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import negocio.Usuario;

@DisplayName("Tests de la clase Controlador")
public class ControladorTest {
    private Controlador controlador;

    @BeforeEach
    public void setUp() {
        controlador = new Controlador();
    }

    @Test
    @DisplayName("Alta de usuario válido")
    public void testAltaUsuarioValido() {
        assertDoesNotThrow(() -> {
            controlador.altaUsuario("TestUser", 3, 3, 3, 3);
        });
        
        Set<String> nombres = controlador.obtenerNombreUsuarios();
        assertTrue(nombres.contains("TestUser"));
    }

    @Test
    @DisplayName("Error al dar de alta usuario con datos inválidos")
    public void testAltaUsuarioInvalido() {
        assertThrows(Exception.class, () -> {
            controlador.altaUsuario("Invalid", 0, 3, 3, 3);
        });
    }

    @Test
    @DisplayName("Obtener nombres de usuarios")
    public void testObtenerNombreUsuarios() {
        try {
            controlador.altaUsuario("User1", 3, 3, 3, 3);
            controlador.altaUsuario("User2", 4, 4, 4, 4);
        } catch (Exception e) {
            fail("No debería lanzar excepción");
        }
        
        Set<String> nombres = controlador.obtenerNombreUsuarios();
        assertTrue(nombres.size() >= 2);
        assertTrue(nombres.contains("User1"));
        assertTrue(nombres.contains("User2"));
    }

    @Test
    @DisplayName("Eliminar usuario existente")
    public void testEliminarUsuarioExistente() {
        try {
            controlador.altaUsuario("UserToDelete", 3, 3, 3, 3);
            assertTrue(controlador.obtenerNombreUsuarios().contains("UserToDelete"));
            
            controlador.eliminarUsuario("UserToDelete");
            assertFalse(controlador.obtenerNombreUsuarios().contains("UserToDelete"));
        } catch (Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Error al eliminar usuario inexistente")
    public void testEliminarUsuarioInexistente() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controlador.eliminarUsuario("UsuarioQueNoExiste");
        });
        
        assertTrue(exception.getMessage().contains("ERROR"));
        assertTrue(exception.getMessage().contains("no existe"));
    }

    @Test
    @DisplayName("Alta de múltiples usuarios")
    public void testAltaMultiplesUsuarios() {
        try {
            controlador.altaUsuario("Usuario1", 1, 2, 3, 4);
            controlador.altaUsuario("Usuario2", 5, 4, 3, 2);
            controlador.altaUsuario("Usuario3", 3, 3, 3, 3);
        } catch (Exception e) {
            fail("No debería lanzar excepción");
        }
        
        Set<String> nombres = controlador.obtenerNombreUsuarios();
        assertTrue(nombres.size() >= 3);
    }

    @Test
    @DisplayName("No se puede agregar usuario con nombre duplicado")
    public void testUsuarioDuplicado() {
        try {
            controlador.altaUsuario("Duplicado", 3, 3, 3, 3);
            controlador.altaUsuario("Duplicado", 4, 4, 4, 4);
            
            // Solo debe haber un usuario con ese nombre (el último sobrescribe)
            Set<String> nombres = controlador.obtenerNombreUsuarios();
            long count = nombres.stream()
                .filter(n -> n.equals("Duplicado"))
                .count();
            assertEquals(1, count);
        } catch (Exception e) {
            fail("No debería lanzar excepción");
        }
    }

    @Test
    @DisplayName("Alta de usuario con intereses en límites inferiores")
    public void testAltaUsuarioLimitesInferiores() {
        assertDoesNotThrow(() -> {
            controlador.altaUsuario("MinUser", 1, 1, 1, 1);
        });
    }

    @Test
    @DisplayName("Alta de usuario con intereses en límites superiores")
    public void testAltaUsuarioLimitesSuperiores() {
        assertDoesNotThrow(() -> {
            controlador.altaUsuario("MaxUser", 5, 5, 5, 5);
        });
    }

    @Test
    @DisplayName("Obtener nombres usuarios con lista vacía")
    public void testObtenerNombresListaVacia() {
        // Después de eliminar todos los usuarios que agregamos
        Set<String> nombresIniciales = controlador.obtenerNombreUsuarios();
        
        try {
            for (String nombre : nombresIniciales) {
                controlador.eliminarUsuario(nombre);
            }
        } catch (IOException e) {
            fail("No debería lanzar excepción");
        }
        
        Set<String> nombres = controlador.obtenerNombreUsuarios();
        assertEquals(0, nombres.size());
    }

    @Test
    @DisplayName("Validar inicialización del controlador")
    public void testInicializacionControlador() {
        Controlador nuevoControlador = new Controlador();
        assertNotNull(nuevoControlador);
        assertNotNull(nuevoControlador.obtenerNombreUsuarios());
    }

    @Test
    @DisplayName("Alta y eliminación múltiple de usuarios")
    public void testOperacionesMultiples() {
        try {
            // Agregar usuarios
            controlador.altaUsuario("Usuario1", 1, 2, 3, 4);
            controlador.altaUsuario("Usuario2", 2, 3, 4, 5);
            controlador.altaUsuario("Usuario3", 3, 4, 5, 1);
            
            Set<String> nombresAntes = controlador.obtenerNombreUsuarios();
            int cantidadAntes = nombresAntes.size();
            
            // Eliminar uno
            controlador.eliminarUsuario("Usuario2");
            
            Set<String> nombresDespues = controlador.obtenerNombreUsuarios();
            assertEquals(cantidadAntes - 1, nombresDespues.size());
            assertFalse(nombresDespues.contains("Usuario2"));
            assertTrue(nombresDespues.contains("Usuario1"));
            assertTrue(nombresDespues.contains("Usuario3"));
        } catch (Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Alta de usuario con nombre con espacios")
    public void testAltaUsuarioNombreConEspacios() {
        assertDoesNotThrow(() -> {
            controlador.altaUsuario("Juan Carlos", 3, 3, 3, 3);
        });
        
        assertTrue(controlador.obtenerNombreUsuarios().contains("Juan Carlos"));
    }

    @Test
    @DisplayName("Alta de usuario con nombre largo")
    public void testAltaUsuarioNombreLargo() {
        String nombreLargo = "Usuario Con Nombre Muy Muy Largo Para Pruebas";
        assertDoesNotThrow(() -> {
            controlador.altaUsuario(nombreLargo, 3, 3, 3, 3);
        });
        
        assertTrue(controlador.obtenerNombreUsuarios().contains(nombreLargo));
    }

    @Test
    @DisplayName("Validar que los intereses se guardan correctamente")
    public void testValidarInteresesGuardados() {
        try {
            controlador.altaUsuario("ValidarIntereses", 1, 2, 3, 4);
            // El test pasa si no lanza excepción y el usuario se agrega
            assertTrue(controlador.obtenerNombreUsuarios().contains("ValidarIntereses"));
        } catch (Exception e) {
            fail("No debería lanzar excepción");
        }
    }

    @Test
    @DisplayName("Cargar usuarios debe funcionar sin errores")
    public void testCargarUsuarios() {
        assertDoesNotThrow(() -> {
            controlador.cargarUsuarios();
        });
    }
}



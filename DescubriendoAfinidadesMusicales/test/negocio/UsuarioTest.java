package negocio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la clase Usuario")
public class UsuarioTest {

    @Test
    @DisplayName("Crear usuario válido con todos los intereses en rango")
    public void testCrearUsuarioValido() {
        Usuario usuario = new Usuario("Juan", 3, 4, 2, 5);
        
        assertEquals("Juan", usuario.nombre());
        assertEquals(3, usuario.interesFolclore());
        assertEquals(4, usuario.interesTango());
        assertEquals(2, usuario.interesRockNacional());
        assertEquals(5, usuario.interesUrbano());
    }

    @Test
    @DisplayName("Crear usuario con intereses mínimos válidos (todos en 1)")
    public void testInteresesMinimosValidos() {
        assertDoesNotThrow(() -> {
            new Usuario("Pedro", 1, 1, 1, 1);
        });
    }

    @Test
    @DisplayName("Crear usuario con intereses máximos válidos (todos en 5)")
    public void testInteresesMaximosValidos() {
        assertDoesNotThrow(() -> {
            new Usuario("María", 5, 5, 5, 5);
        });
    }

    @Test
    @DisplayName("Error al crear usuario con interés de folclore menor a 1")
    public void testInteresFolcloreMenorA1() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Usuario("Carlos", 0, 3, 3, 3)
        );
        assertTrue(exception.getMessage().contains("Interes Folclore"));
        assertTrue(exception.getMessage().contains("no puede ser menor a 1"));
    }

    @Test
    @DisplayName("Error al crear usuario con interés de tango mayor a 5")
    public void testInteresTangoMayorA5() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Usuario("Ana", 3, 6, 3, 3)
        );
        assertTrue(exception.getMessage().contains("Interes Tango"));
        assertTrue(exception.getMessage().contains("no puede ser mayor a 5"));
    }

    @Test
    @DisplayName("Error al crear usuario con interés de rock nacional inválido")
    public void testInteresRockNacionalInvalido() {
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Luis", 3, 3, -1, 3)
        );
    }

    @Test
    @DisplayName("Error al crear usuario con interés urbano inválido")
    public void testInteresUrbanoInvalido() {
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Sofia", 3, 3, 3, 10)
        );
    }

    @Test
    @DisplayName("Validar múltiples intereses inválidos simultáneos")
    public void testMultiplesInteresesInvalidos() {
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Error", 0, 0, 0, 0)
        );
    }

    @Test
    @DisplayName("Validar método toString contiene información correcta")
    public void testToString() {
        Usuario usuario = new Usuario("Test", 2, 3, 4, 5);
        String resultado = usuario.toString();
        
        assertTrue(resultado.contains("Test"));
        assertTrue(resultado.contains("Tango: 3"));
        assertTrue(resultado.contains("Folclore: 2"));
        assertTrue(resultado.contains("Rock Nacional: 4"));
        assertTrue(resultado.contains("Genero Urbano: 5"));
    }

    @Test
    @DisplayName("Crear usuario con nombre vacío")
    public void testUsuarioNombreVacio() {
        Usuario usuario = new Usuario("", 3, 3, 3, 3);
        assertEquals("", usuario.nombre());
    }

    @Test
    @DisplayName("Crear usuario con nombre con espacios")
    public void testUsuarioNombreConEspacios() {
        Usuario usuario = new Usuario("Juan Carlos", 3, 3, 3, 3);
        assertEquals("Juan Carlos", usuario.nombre());
    }

    @Test
    @DisplayName("Validar bordes superiores de cada interés")
    public void testBordesSuperioresTodosIntereses() {
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Test", 6, 3, 3, 3)
        );
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Test", 3, 6, 3, 3)
        );
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Test", 3, 3, 6, 3)
        );
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Test", 3, 3, 3, 6)
        );
    }

    @Test
    @DisplayName("Validar bordes inferiores de cada interés")
    public void testBordesInferioresTodosIntereses() {
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Test", 0, 3, 3, 3)
        );
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Test", 3, 0, 3, 3)
        );
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Test", 3, 3, 0, 3)
        );
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Test", 3, 3, 3, 0)
        );
    }
}



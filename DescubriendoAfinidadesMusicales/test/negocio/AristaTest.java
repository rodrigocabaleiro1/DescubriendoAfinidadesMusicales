package negocio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la clase Arista")
public class AristaTest {
    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuario3;

    @BeforeEach
    public void setUp() {
        usuario1 = new Usuario("Juan", 3, 4, 2, 5);
        usuario2 = new Usuario("María", 2, 3, 4, 1);
        usuario3 = new Usuario("Pedro", 5, 5, 5, 5);
    }

    @Test
    @DisplayName("Crear arista y verificar sus atributos")
    public void testCrearArista() {
        Arista arista = new Arista(usuario1, usuario2, 10);
        
        assertEquals(usuario1, arista.getU1());
        assertEquals(usuario2, arista.getU2());
        assertEquals(10, arista.getPeso());
    }

    @Test
    @DisplayName("Comparar aristas: peso menor")
    public void testCompararAristasMenor() {
        Arista arista1 = new Arista(usuario1, usuario2, 5);
        Arista arista2 = new Arista(usuario2, usuario3, 10);
        
        assertTrue(arista1.compareTo(arista2) < 0);
    }

    @Test
    @DisplayName("Comparar aristas: peso mayor")
    public void testCompararAristasMayor() {
        Arista arista1 = new Arista(usuario1, usuario2, 15);
        Arista arista2 = new Arista(usuario2, usuario3, 8);
        
        assertTrue(arista1.compareTo(arista2) > 0);
    }

    @Test
    @DisplayName("Comparar aristas: pesos iguales")
    public void testCompararAristasIguales() {
        Arista arista1 = new Arista(usuario1, usuario2, 10);
        Arista arista2 = new Arista(usuario2, usuario3, 10);
        
        assertEquals(0, arista1.compareTo(arista2));
    }

    @Test
    @DisplayName("Validar método toString contiene información de ambos usuarios y peso")
    public void testToString() {
        Arista arista = new Arista(usuario1, usuario2, 10);
        String resultado = arista.toString();
        
        assertTrue(resultado.contains("Juan"));
        assertTrue(resultado.contains("María"));
        assertTrue(resultado.contains("10"));
    }

    @Test
    @DisplayName("Crear arista con peso cero")
    public void testAristaPesoCero() {
        Arista arista = new Arista(usuario1, usuario1, 0);
        assertEquals(0, arista.getPeso());
    }

    @Test
    @DisplayName("Crear arista con peso negativo")
    public void testAristaPesoNegativo() {
        Arista arista = new Arista(usuario1, usuario2, -5);
        assertEquals(-5, arista.getPeso());
    }

    @Test
    @DisplayName("Comparar arista con peso negativo")
    public void testCompararAristasPesoNegativo() {
        Arista arista1 = new Arista(usuario1, usuario2, -5);
        Arista arista2 = new Arista(usuario2, usuario3, 3);
        
        assertTrue(arista1.compareTo(arista2) < 0);
    }

    @Test
    @DisplayName("Crear arista que conecta un usuario consigo mismo")
    public void testAristaConMismoUsuario() {
        Arista arista = new Arista(usuario1, usuario1, 0);
        
        assertEquals(usuario1, arista.getU1());
        assertEquals(usuario1, arista.getU2());
        assertSame(arista.getU1(), arista.getU2());
    }

    @Test
    @DisplayName("Comparar múltiples aristas y verificar ordenamiento")
    public void testOrdenamientoMultiplesAristas() {
        Arista arista1 = new Arista(usuario1, usuario2, 1);
        Arista arista2 = new Arista(usuario1, usuario3, 5);
        Arista arista3 = new Arista(usuario2, usuario3, 3);
        
        assertTrue(arista1.compareTo(arista2) < 0);
        assertTrue(arista1.compareTo(arista3) < 0);
        assertTrue(arista3.compareTo(arista2) < 0);
    }

    @Test
    @DisplayName("Crear arista con peso muy grande")
    public void testAristaPesoGrande() {
        Arista arista = new Arista(usuario1, usuario2, 1000000);
        assertEquals(1000000, arista.getPeso());
    }
}



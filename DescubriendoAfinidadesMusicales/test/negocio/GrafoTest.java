package negocio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@DisplayName("Tests de la clase Grafo")
public class GrafoTest {
    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuario3;
    private Usuario usuario4;

    @BeforeEach
    public void setUp() {
        usuario1 = new Usuario("Juan", 1, 1, 1, 1);
        usuario2 = new Usuario("María", 1, 1, 1, 2);
        usuario3 = new Usuario("Pedro", 5, 5, 5, 5);
        usuario4 = new Usuario("Ana", 5, 5, 5, 4);
    }

    @Test
    @DisplayName("Crear grafo básico")
    public void testCrearGrafo() {
        Usuario[] usuarios = {usuario1, usuario2};
        int[][] matriz = {{0, 1}, {1, 0}};
        
        Grafo grafo = new Grafo(usuarios, matriz);
        assertNotNull(grafo);
    }

    @Test
    @DisplayName("con tres usuarios debe tener exactamente 2 aristas")
    public void testArbolGeneradorMinimoTresUsuarios() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        
        assertEquals(2, mst.size());
    }

    @Test
    @DisplayName("con cuatro usuarios debe tener exactamente 3 aristas")
    public void testArbolGeneradorMinimoCuatroUsuarios() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        
        assertEquals(3, mst.size());
    }

    @Test
    @DisplayName(" debe contener aristas con pesos ordenados de menor a mayor")
    public void testMSTContieneAristasMinimas() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        
        for (int i = 0; i < mst.size() - 1; i++) {
            assertTrue(mst.get(i).getPeso() <= mst.get(i + 1).getPeso());
        }
    }

    @Test
    @DisplayName("Dividir grafo en grupos debe crear exactamente 2 grupos")
    public void testDividirEnGrupos() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        List<List<Usuario>> grupos = grafo.dividirEnGrupos(mst);
        
        assertNotNull(grupos);
        assertEquals(2, grupos.size());
    }

    @Test
    @DisplayName("Los grupos deben contener todos los usuarios originales")
    public void testDividirEnGruposContenidoEsperado() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        List<List<Usuario>> grupos = grafo.dividirEnGrupos(mst);
        
        for (List<Usuario> grupo : grupos) {
            assertFalse(grupo.isEmpty());
        }
        
        int totalUsuarios = grupos.stream()
            .mapToInt(List::size)
            .sum();
        assertEquals(4, totalUsuarios);
    }

    @Test
    @DisplayName("MST con un solo usuario debe tener cero aristas")
    public void testMSTUnSoloUsuario() {
        Usuario[] usuarios = {usuario1};
        int[][] matriz = {{0}};
        Grafo grafo = new Grafo(usuarios, matriz);
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        
        assertEquals(0, mst.size());
    }

    @Test
    @DisplayName("MST con dos usuarios debe tener exactamente una arista")
    public void testMSTDosUsuarios() {
        Usuario[] usuarios = {usuario1, usuario2};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        
        assertEquals(1, mst.size());
    }

    @Test
    @DisplayName("Dividir grupos con MST vacío debe retornar null")
    public void testDividirGruposVacio() {
        Usuario[] usuarios = {usuario1};
        int[][] matriz = {{0}};
        Grafo grafo = new Grafo(usuarios, matriz);
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        List<List<Usuario>> grupos = grafo.dividirEnGrupos(mst);
        
        assertNull(grupos);
    }

    @Test
    @DisplayName("MST debe incluir todos los usuarios en un componente conectado")
    public void testMSTComponenteConectado() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        
        // Verificar que el MST conecta exactamente n-1 aristas para n nodos
        assertEquals(usuarios.length - 1, mst.size());
    }

    @Test
    @DisplayName("MST con usuarios de similitud idéntica")
    public void testMSTSimilitudIdentica() {
        Usuario u1 = new Usuario("U1", 3, 3, 3, 3);
        Usuario u2 = new Usuario("U2", 3, 3, 3, 3);
        Usuario u3 = new Usuario("U3", 3, 3, 3, 3);
        
        Usuario[] usuarios = {u1, u2, u3};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        
        assertEquals(2, mst.size());
        // Todas las aristas deben tener peso 0
        for (Arista arista : mst) {
            assertEquals(0, arista.getPeso());
        }
    }

    @Test
    @DisplayName("Dividir en grupos debe preservar similitudes altas dentro de cada grupo")
    public void testGruposSimilaridadAlta() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        List<List<Usuario>> grupos = grafo.dividirEnGrupos(mst);
        
        // Verificar que usuario1 y usuario2 están en el mismo grupo (similares)
        // y usuario3 y usuario4 están en el mismo grupo (similares)
        boolean grupo1Correcto = 
            (grupos.get(0).contains(usuario1) && grupos.get(0).contains(usuario2)) ||
            (grupos.get(1).contains(usuario1) && grupos.get(1).contains(usuario2));
        
        boolean grupo2Correcto = 
            (grupos.get(0).contains(usuario3) && grupos.get(0).contains(usuario4)) ||
            (grupos.get(1).contains(usuario3) && grupos.get(1).contains(usuario4));
        
        assertTrue(grupo1Correcto);
        assertTrue(grupo2Correcto);
    }

    @Test
    @DisplayName("MST con 5 usuarios debe tener 4 aristas")
    public void testMSTCincoUsuarios() {
        Usuario u1 = new Usuario("U1", 1, 1, 1, 1);
        Usuario u2 = new Usuario("U2", 2, 2, 2, 2);
        Usuario u3 = new Usuario("U3", 3, 3, 3, 3);
        Usuario u4 = new Usuario("U4", 4, 4, 4, 4);
        Usuario u5 = new Usuario("U5", 5, 5, 5, 5);
        
        Usuario[] usuarios = {u1, u2, u3, u4, u5};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        
        assertEquals(4, mst.size());
    }

    @Test
    @DisplayName("Verificar que MST no contiene ciclos")
    public void testMSTSinCiclos() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        Grafo grafo = new Grafo(usuarios, indice.getMatriz());
        
        List<Arista> mst = grafo.arbolGeneradorMinimo();
        
        // Un árbol con n nodos tiene exactamente n-1 aristas
        assertEquals(usuarios.length - 1, mst.size());
    }
}




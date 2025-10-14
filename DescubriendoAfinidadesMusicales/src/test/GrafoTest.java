package test;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;

import negocio.Arista;
import negocio.Grafo;

public class GrafoTest {

    private Grafo<String> grafo;

    @Before
    public void setUp() {
        grafo = new Grafo<>();
    }

    @Test
    public void testAgregarYObtenerVertice() {
        int id = grafo.agregarVertice("A");
        assertTrue(grafo.existeVertice(id));
        assertEquals("A", grafo.obtenerDatoVertice(id));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEliminarVerticeInexistente() {
        grafo.eliminarVertice(999);
    }

    @Test
    public void testEliminarVerticeExistente() {
        int id = grafo.agregarVertice("B");
        grafo.eliminarVertice(id);
        assertFalse(grafo.existeVertice(id));
    }

    @Test
    public void testModificarVertice() {
        int id = grafo.agregarVertice("C");
        grafo.modificarVertice(id, "D");
        assertEquals("D", grafo.obtenerDatoVertice(id));
    }

    @Test
    public void testAgregarYEliminarArista() {
        int v1 = grafo.agregarVertice("A");
        int v2 = grafo.agregarVertice("B");

        grafo.agregarArista(v1, v2, 5);
        assertTrue(grafo.existeArista(v1, v2));
        assertEquals(Integer.valueOf(5), grafo.obtenerPeso(v1, v2));

        grafo.eliminarArista(v1, v2);
        assertFalse(grafo.existeArista(v1, v2));
    }

    @Test
    public void testObtenerVecinos() {
        int v1 = grafo.agregarVertice("A");
        int v2 = grafo.agregarVertice("B");
        int v3 = grafo.agregarVertice("C");

        grafo.agregarArista(v1, v2, 3);
        grafo.agregarArista(v1, v3, 7);

        List<Integer> vecinos = grafo.obtenerVecinos(v1);
        assertTrue(vecinos.contains(v2));
        assertTrue(vecinos.contains(v3));
    }

    @Test
    public void testArbolGeneradorMinimo() {
        int v1 = grafo.agregarVertice("A");
        int v2 = grafo.agregarVertice("B");
        int v3 = grafo.agregarVertice("C");

        grafo.agregarArista(v1, v2, 1);
        grafo.agregarArista(v2, v3, 2);
        grafo.agregarArista(v1, v3, 3);

        List<Arista> agm = grafo.arbolGeneradorMinimo();
        int sumaPeso = agm.stream().mapToInt(Arista::obtenerPeso).sum();

        // El AGM debería tener peso total 3 (1+2)
        assertEquals(3, sumaPeso);
    }

    @Test
    public void testParticionarEnKComponentes() {
        int v1 = grafo.agregarVertice("A");
        int v2 = grafo.agregarVertice("B");
        int v3 = grafo.agregarVertice("C");
        int v4 = grafo.agregarVertice("D");

        grafo.agregarArista(v1, v2, 1);
        grafo.agregarArista(v2, v3, 2);
        grafo.agregarArista(v3, v4, 3);

        List<Set<Integer>> componentes = grafo.particionarEnKComponentes(2);
        assertEquals(2, componentes.size());
        // Cada componente debe tener al menos un vértice
        for (Set<Integer> c : componentes) {
            assertTrue(c.size() >= 1);
        }
    }

    @Test
    public void testGenerarAPartirDeMatrizDeSimilaridad() {
        Map<Integer, String> vertices = new HashMap<>();
        vertices.put(0, "A");
        vertices.put(1, "B");
        vertices.put(2, "C");

        int[][] matriz = {
            {0, 1, 2},
            {1, 0, 3},
            {2, 3, 0}
        };

        grafo.generarAPartirDeMatrizDeSimilaridad(matriz, vertices);

        assertEquals("A", grafo.obtenerDatoVertice(0));
        assertEquals(Integer.valueOf(1), grafo.obtenerPeso(0, 1));
        assertEquals(Integer.valueOf(3), grafo.obtenerPeso(1, 2));
    }
}

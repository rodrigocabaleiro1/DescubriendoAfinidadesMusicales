package negocio;

import static org.junit.Assert.*;
import org.junit.Test;

public class KruskalTest {

    @Test
    public void testInicializacion() {
        Kruskal k = new Kruskal(5);
        // Cada nodo debería ser su propio padre inicialmente
        for (int i = 0; i < 5; i++) {
            assertEquals(i, k.find(i));
        }
    }

    @Test
    public void testUnionYFind() {
        Kruskal k = new Kruskal(5);

        // Unir 0 y 1
        assertTrue(k.union(0, 1));
        assertEquals(k.find(0), k.find(1));

        // Unir 1 y 2
        assertTrue(k.union(1, 2));
        assertEquals(k.find(0), k.find(2));

        // Unir 3 y 4
        assertTrue(k.union(3, 4));
        assertEquals(k.find(3), k.find(4));

        // Intentar unir nodos ya conectados
        assertFalse(k.union(0, 2)); // 0 y 2 ya están unidos
    }

    @Test
    public void testUnionMultiples() {
        Kruskal k = new Kruskal(6);

        assertTrue(k.union(0, 1));
        assertTrue(k.union(2, 3));
        assertTrue(k.union(4, 5));
        assertTrue(k.union(1, 2));
        assertTrue(k.union(0, 3)); // ya están unidos, debería ser false
        assertFalse(k.union(0, 3));

        // Revisar que todos los nodos de 0 a 3 tengan la misma raíz
        int raiz = k.find(0);
        for (int i = 0; i <= 3; i++) {
            assertEquals(raiz, k.find(i));
        }
    }
}

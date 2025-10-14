package test;

import static org.junit.Assert.*;
import org.junit.Test;

import negocio.Kruskal;

public class KruskalTest {

    @Test
    public void testFindDevuelveRaizCorrecta() {
        Kruskal kr = new Kruskal(5);
        // Al inicio, cada nodo es su propio padre
        for (int i = 0; i < 5; i++) {
            assertEquals(i, kr.find(i));
        }
    }

    @Test
    public void testUnionConectaNodos() {
        Kruskal kr = new Kruskal(5);
        boolean unionResult = kr.union(0, 1);
        assertTrue("La union debería devolver true si une nodos distintos", unionResult);
        // Ahora 0 y 1 deberían tener la misma raíz
        assertEquals(kr.find(0), kr.find(1));
    }

    @Test
    public void testUnionNodosYaConectadosDevuelveFalse() {
        Kruskal kr = new Kruskal(5);
        kr.union(0, 1);
        // Intentar unirlos nuevamente
        boolean unionResult = kr.union(0, 1);
        assertFalse("La union debería devolver false si los nodos ya están conectados", unionResult);
    }

    @Test
    public void testCadenaDeUniones() {
        Kruskal kr = new Kruskal(5);
        kr.union(0, 1);
        kr.union(1, 2);
        kr.union(3, 4);

        // Los nodos 0,1,2 deben tener la misma raíz
        int raiz = kr.find(0);
        assertEquals(raiz, kr.find(1));
        assertEquals(raiz, kr.find(2));

        // Nodos 3 y 4 deben tener la misma raíz pero diferente de 0
        assertEquals(kr.find(3), kr.find(4));
        assertNotEquals(raiz, kr.find(3));
    }
}
package negocio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la clase UnionFind")
public class UnionFindTest {
    private UnionFind uf;

    @BeforeEach
    public void setUp() {
        uf = new UnionFind(5);
    }

    @Test
    @DisplayName("Inicialización: cada elemento es su propio padre")
    public void testInicializacion() {
        for (int i = 0; i < 5; i++) {
            assertEquals(i, uf.find(i));
        }
    }

    @Test
    @DisplayName("Unión simple de dos elementos")
    public void testUnionSimple() {
        assertTrue(uf.union(0, 1));
        assertEquals(uf.find(0), uf.find(1));
    }

    @Test
    @DisplayName("Unión de elementos que ya están en el mismo conjunto")
    public void testUnionMismoConjunto() {
        uf.union(0, 1);
        assertFalse(uf.union(0, 1));
    }

    @Test
    @DisplayName("Múltiples uniones creando diferentes conjuntos")
    public void testUnionMultiple() {
        assertTrue(uf.union(0, 1));
        assertTrue(uf.union(1, 2));
        assertTrue(uf.union(3, 4));
        
        assertEquals(uf.find(0), uf.find(1));
        assertEquals(uf.find(1), uf.find(2));
        assertEquals(uf.find(3), uf.find(4));
        assertNotEquals(uf.find(0), uf.find(3));
    }

    @Test
    @DisplayName("Unir todos los elementos en un único conjunto")
    public void testUnionTodosElementos() {
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(2, 3);
        uf.union(3, 4);
        
        int raiz = uf.find(0);
        for (int i = 1; i < 5; i++) {
            assertEquals(raiz, uf.find(i));
        }
    }

    @Test
    @DisplayName("Validar compresión de camino en find")
    public void testCompresionDeCamino() {
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(2, 3);
        
        int raiz1 = uf.find(3);
        int raiz2 = uf.find(3);
        
        assertEquals(raiz1, raiz2);
    }

    @Test
    @DisplayName("UnionFind con un solo elemento")
    public void testUnionFindUnElemento() {
        UnionFind ufSingle = new UnionFind(1);
        assertEquals(0, ufSingle.find(0));
        assertFalse(ufSingle.union(0, 0));
    }

    @Test
    @DisplayName("Unión en cadena lineal")
    public void testUnionCadenaLineal() {
        for (int i = 0; i < 4; i++) {
            assertTrue(uf.union(i, i + 1));
        }
        
        int raiz = uf.find(0);
        for (int i = 1; i < 5; i++) {
            assertEquals(raiz, uf.find(i));
        }
    }

    @Test
    @DisplayName("Unión de elementos no consecutivos")
    public void testUnionNoConsecutivos() {
        assertTrue(uf.union(0, 4));
        assertTrue(uf.union(1, 3));
        
        assertEquals(uf.find(0), uf.find(4));
        assertEquals(uf.find(1), uf.find(3));
        assertNotEquals(uf.find(0), uf.find(1));
        assertNotEquals(uf.find(0), uf.find(2));
    }

    @Test
    @DisplayName("Verificar que union retorna false cuando elementos ya conectados")
    public void testUnionRetornaFalsoCuandoConectados() {
        uf.union(0, 1);
        uf.union(1, 2);
        
        assertFalse(uf.union(0, 2));
    }

    @Test
    @DisplayName("UnionFind con tamaño cero")
    public void testUnionFindTamanoCero() {
        UnionFind ufEmpty = new UnionFind(0);
        assertNotNull(ufEmpty);
    }

    @Test
    @DisplayName("Merge de dos conjuntos grandes")
    public void testMergeConjuntosGrandes() {
        uf.union(0, 1);
        uf.union(3, 4);
        
        assertTrue(uf.union(1, 3));
        
        int raiz = uf.find(0);
        assertEquals(raiz, uf.find(1));
        assertEquals(raiz, uf.find(3));
        assertEquals(raiz, uf.find(4));
    }

    @Test
    @DisplayName("Verificar consistencia después de múltiples operaciones")
    public void testConsistenciaDespuesOperaciones() {
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(1, 2);
        
        int raiz = uf.find(0);
        assertEquals(raiz, uf.find(1));
        assertEquals(raiz, uf.find(2));
        assertEquals(raiz, uf.find(3));
        
        assertNotEquals(raiz, uf.find(4));
    }
}



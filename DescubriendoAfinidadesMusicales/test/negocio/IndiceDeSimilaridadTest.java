package negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IndiceDeSimilaridadTest {
	private final Usuario usuario1 = new Usuario("1", "Juan", 3, 4, 2, 5);
    private final Usuario usuario2 = new Usuario("2", "María", 3, 4, 2, 5);
    private final Usuario usuario3 = new Usuario("3","Pedro", 5, 5, 5, 5);
    private final Usuario usuario4 = new Usuario("4","Ana", 1, 1, 1, 1);
      
    @Test
    public void testSimilaridadConsigMismo() {
        Usuario[] usuarios = {usuario1};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        assertEquals(0, matriz[0][0]);
    }
    
    @Test
    public void testSimilaridadUsuariosIdenticos() {
    	Usuario[] usuarios = {usuario1, usuario2};
    	IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
    	int[][] matriz = indice.obtenerIndice();
    	
    	assertEquals(0, matriz[0][1]);
    	assertEquals(0, matriz[1][0]);
    }
    
    @Test
    public void testSimilaridadMaximaVsMinima() {
        Usuario[] usuarios = {usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        // Pedro (5,5,5,5) vs Ana (1,1,1,1)
        // |5-1| + |5-1| + |5-1| + |5-1| = 16
        assertEquals(16, matriz[0][1]);
        assertEquals(16, matriz[1][0]);
    }
    
    @Test
    public void testSimetriaDeLaMatriz() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(matriz[i][j], matriz[j][i]);
            }
        }
    }

    @Test
    public void testDiagonalCeros() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        for (int i = 0; i < 4; i++) {
            assertEquals(0, matriz[i][i]);
        }
    }

    @Test
    public void testCalculoSimilaridadEspecifico() {
        // Juan (3,4,2,5) vs Pedro (5,5,5,5)
        // |3-5| + |4-5| + |2-5| + |5-5| = 2 + 1 + 3 + 0 = 6
        Usuario[] usuarios = {usuario1, usuario3};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        assertEquals(6, matriz[0][1]);
    }

    @Test
    public void testTamañoMatriz() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        assertEquals(4, matriz.length);
        for (int i = 0; i < matriz.length; i++) {
            assertEquals(4, matriz[i].length);
        }
    }

    @Test
    public void testValoresNoNegativos() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertTrue(matriz[i][j] >= 0);
            }
        }
    }

    @Test
    public void testMatrizUnSoloUsuario() {
        Usuario[] usuarios = {usuario1};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        assertEquals(1, matriz.length);
        assertEquals(1, matriz[0].length);
        assertEquals(0, matriz[0][0]);
    }

    @Test
    public void testSimilaridadDiferenciasParciales() {
        Usuario usuarioA = new Usuario("1","A", 1, 2, 3, 4);
        Usuario usuarioB = new Usuario("2","B", 2, 2, 3, 3);
        // |1-2| + |2-2| + |3-3| + |4-3| = 1 + 0 + 0 + 1 = 2
        
        Usuario[] usuarios = {usuarioA, usuarioB};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        assertEquals(2, matriz[0][1]);
    }

    @Test
    public void testSimilaridadValoresMaximos() {
        Usuario usuarioMax1 = new Usuario("1","Max1", 5, 5, 5, 5);
        Usuario usuarioMax2 = new Usuario("2","Max2", 5, 5, 5, 5);
        
        Usuario[] usuarios = {usuarioMax1, usuarioMax2};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        assertEquals(0, matriz[0][1]);
    }

    @Test
    public void testSimilaridadValoresMinimos() {
        Usuario usuarioMin1 = new Usuario("1", "Min1", 1, 1, 1, 1);
        Usuario usuarioMin2 = new Usuario("2", "Min2", 1, 1, 1, 1);
        
        Usuario[] usuarios = {usuarioMin1, usuarioMin2};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        assertEquals(0, matriz[0][1]);
    }

    @Test
    public void testMatrizTresUsuarios() {
        Usuario[] usuarios = {usuario1, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.obtenerIndice();
        
        assertEquals(3, matriz.length);
        
        // Verificar desigualdad triangular (opcional pero interesante)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    assertTrue(matriz[i][k] <= matriz[i][j] + matriz[j][k]);
                }
            }
        }
    }
}

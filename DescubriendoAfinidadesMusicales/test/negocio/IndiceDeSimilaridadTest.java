package negocio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la clase IndiceDeSimilaridad")
public class IndiceDeSimilaridadTest {
    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuario3;
    private Usuario usuario4;

    @BeforeEach
    public void setUp() {
        usuario1 = new Usuario("Juan", 3, 4, 2, 5);
        usuario2 = new Usuario("María", 3, 4, 2, 5);
        usuario3 = new Usuario("Pedro", 5, 5, 5, 5);
        usuario4 = new Usuario("Ana", 1, 1, 1, 1);
    }

    @Test
    @DisplayName("Similitud entre usuarios idénticos debe ser cero")
    public void testSimilaridadUsuariosIdenticos() {
        Usuario[] usuarios = {usuario1, usuario2};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        assertEquals(0, matriz[0][1]);
        assertEquals(0, matriz[1][0]);
    }

    @Test
    @DisplayName("Similitud de un usuario consigo mismo debe ser cero")
    public void testSimilaridadConsigMismo() {
        Usuario[] usuarios = {usuario1};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        assertEquals(0, matriz[0][0]);
    }

    @Test
    @DisplayName("Similitud máxima entre usuarios con todos los intereses opuestos")
    public void testSimilaridadMaximaVsMinima() {
        Usuario[] usuarios = {usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        // Pedro (5,5,5,5) vs Ana (1,1,1,1)
        // |5-1| + |5-1| + |5-1| + |5-1| = 16
        assertEquals(16, matriz[0][1]);
        assertEquals(16, matriz[1][0]);
    }

    @Test
    @DisplayName("La matriz de similitud debe ser simétrica")
    public void testSimetriaDeLaMatriz() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(matriz[i][j], matriz[j][i]);
            }
        }
    }

    @Test
    @DisplayName("La diagonal de la matriz debe ser cero")
    public void testDiagonalCeros() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        for (int i = 0; i < 4; i++) {
            assertEquals(0, matriz[i][i]);
        }
    }

    @Test
    @DisplayName("Calcular similitud específica entre dos usuarios")
    public void testCalculoSimilaridadEspecifico() {
        // Juan (3,4,2,5) vs Pedro (5,5,5,5)
        // |3-5| + |4-5| + |2-5| + |5-5| = 2 + 1 + 3 + 0 = 6
        Usuario[] usuarios = {usuario1, usuario3};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        assertEquals(6, matriz[0][1]);
    }

    @Test
    @DisplayName("Verificar dimensiones de la matriz de similitud")
    public void testTamañoMatriz() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        assertEquals(4, matriz.length);
        for (int i = 0; i < matriz.length; i++) {
            assertEquals(4, matriz[i].length);
        }
    }

    @Test
    @DisplayName("Todos los valores de similitud deben ser no negativos")
    public void testValoresNoNegativos() {
        Usuario[] usuarios = {usuario1, usuario2, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertTrue(matriz[i][j] >= 0);
            }
        }
    }

    @Test
    @DisplayName("Matriz con un solo usuario")
    public void testMatrizUnSoloUsuario() {
        Usuario[] usuarios = {usuario1};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        assertEquals(1, matriz.length);
        assertEquals(1, matriz[0].length);
        assertEquals(0, matriz[0][0]);
    }

    @Test
    @DisplayName("Similitud con diferencias parciales")
    public void testSimilaridadDiferenciasParciales() {
        Usuario usuarioA = new Usuario("A", 1, 2, 3, 4);
        Usuario usuarioB = new Usuario("B", 2, 2, 3, 3);
        // |1-2| + |2-2| + |3-3| + |4-3| = 1 + 0 + 0 + 1 = 2
        
        Usuario[] usuarios = {usuarioA, usuarioB};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        assertEquals(2, matriz[0][1]);
    }

    @Test
    @DisplayName("Similitud con valores máximos")
    public void testSimilaridadValoresMaximos() {
        Usuario usuarioMax1 = new Usuario("Max1", 5, 5, 5, 5);
        Usuario usuarioMax2 = new Usuario("Max2", 5, 5, 5, 5);
        
        Usuario[] usuarios = {usuarioMax1, usuarioMax2};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        assertEquals(0, matriz[0][1]);
    }

    @Test
    @DisplayName("Similitud con valores mínimos")
    public void testSimilaridadValoresMinimos() {
        Usuario usuarioMin1 = new Usuario("Min1", 1, 1, 1, 1);
        Usuario usuarioMin2 = new Usuario("Min2", 1, 1, 1, 1);
        
        Usuario[] usuarios = {usuarioMin1, usuarioMin2};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
        assertEquals(0, matriz[0][1]);
    }

    @Test
    @DisplayName("Matriz con tres usuarios - triángulo de similitudes")
    public void testMatrizTresUsuarios() {
        Usuario[] usuarios = {usuario1, usuario3, usuario4};
        IndiceDeSimilaridad indice = new IndiceDeSimilaridad(usuarios);
        int[][] matriz = indice.getMatriz();
        
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



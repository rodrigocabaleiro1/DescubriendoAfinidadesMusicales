package negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AristaTest {
	
	@Test
    public void testCrearAristaCorrectamente() {
        Arista arista = new Arista(1, 1, 10);
    }
	
	@Test
	public void testCompararAristasMenor() {
        Arista arista1 = new Arista(1, 2, 1);
        Arista arista2 = new Arista(1, 2, 2);
        assertTrue(arista1.compareTo(arista2) < 0);
    }
	
	@Test
	public void testCompararAristasMayor() {
        Arista arista1 = new Arista(1, 2, 2);
        Arista arista2 = new Arista(2, 3, 1);
        assertTrue(arista1.compareTo(arista2) > 0);
    }
	
	@Test
    public void testCompararAristasIguales() {
		Arista arista1 = new Arista(1, 2, 1);
        Arista arista2 = new Arista(2, 3, 1);
        assertEquals(0, arista1.compareTo(arista2));
    }
}

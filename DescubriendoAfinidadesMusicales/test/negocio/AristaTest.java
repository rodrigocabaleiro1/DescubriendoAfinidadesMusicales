package negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AristaTest {
	
	@Test
    public void testCrearAristaCorrectamente() {
        Arista arista = new Arista("usuario1", "usuario2", 10);
    }
	
	@Test (expected = IllegalArgumentException.class)
	public void testCrearAristaConDosUsuariosIguales() {
		Arista arista  = new Arista("Usuario1", "Usuario1", 10);
	}
	
	@Test
	public void testCompararAristasMenor() {
        Arista arista1 = new Arista("usuario1", "usuario2", 1);
        Arista arista2 = new Arista("usuario3", "usuario2", 2);
        assertTrue(arista1.compareTo(arista2) < 0);
    }
	
	@Test
	public void testCompararAristasMayor() {
        Arista arista1 = new Arista("usuario1", "usuario2", 2);
        Arista arista2 = new Arista("usuario2", "usuario3", 1);
        assertTrue(arista1.compareTo(arista2) > 0);
    }
	
	@Test
    public void testCompararAristasIguales() {
		Arista arista1 = new Arista("usuario1", "usuario2", 1);
        Arista arista2 = new Arista("usuario2", "usuario3", 1);
        assertEquals(0, arista1.compareTo(arista2));
    }
}

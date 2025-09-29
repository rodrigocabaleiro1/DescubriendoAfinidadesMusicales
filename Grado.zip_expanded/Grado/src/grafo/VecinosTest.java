package grafo;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class VecinosTest {

	@Test(expected = IllegalArgumentException.class)
	public void VeticeNegativoTest() {
		Grafo grafo = new Grafo(5);
		grafo.vecinos(-1);
	}
	@Test(expected = IllegalArgumentException.class)
	public void VeticeExcedidoTest() {
		Grafo grafo = new Grafo(5);
		grafo.vecinos(5);
	}
	
	@Test
	public void GrafoVacioTest() {
		Grafo grafo = new Grafo(0);
		assertEquals(0, grafo.vecinos(0).size());
	}
	@Test
	public void VerticesAisladosTest() {
		Grafo grafo = new Grafo(5);
		assertEquals(0, grafo.vecinos(0).size());
	}
	@Test
	public void VeticeUniversalTest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(0, 1);
		grafo.agregarArista(0, 2);
		grafo.agregarArista(0, 3);
		grafo.agregarArista(0, 4);
		int []esperado = {1,2,3,4};
		Assert.Iguales(esperado, grafo.vecinos(0));
	}
	@Test
	public void VecinosTest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(0, 1);
		grafo.agregarArista(0, 2);
		int []esperado = {1,2};
		Assert.Iguales(esperado, grafo.vecinos(0));
	}
	
}

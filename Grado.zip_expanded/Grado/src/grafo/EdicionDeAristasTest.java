package grafo;

import static org.junit.Assert.*;

import org.junit.Test;

public class EdicionDeAristasTest {

	@Test
	public void AgregarAristatest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(2, 3);
		assertTrue(grafo.existeArista(2, 3));

	}
	@Test
	public void AgregarAristaOpuestatest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(2, 3);
		assertTrue(grafo.existeArista(3, 2));
	}
	@Test
	public void EliminarAristatest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(2, 3);
		grafo.eliminarArista(2, 3);
		assertFalse(grafo.existeArista(2, 3));
	}
	@Test
	public void EliminarAristaOpuestatest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(2, 3);
		grafo.eliminarArista(2, 3);
		assertFalse(grafo.existeArista(3, 2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void PrimerVeticeNegativoTest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(-1, 3);
	}
	@Test(expected = IllegalArgumentException.class)
	public void SegundoVeticeNegativoTest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(1, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void PrimerVeticeExcedidoTest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(7, 4);
	}
	@Test(expected = IllegalArgumentException.class)
	public void SegundoVeticeExcedidoTest() {
		Grafo grafo = new Grafo(5);
		grafo.agregarArista(2, 5);
	}
	
	
	

}

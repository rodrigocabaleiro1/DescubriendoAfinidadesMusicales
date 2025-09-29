package grafo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

public class Assert {
	public static void Iguales(int [] esperado, Set <Integer> obtenido) {
		assertEquals(esperado.length, obtenido.size());
		for(int i : esperado) {
			assertTrue(obtenido.contains(i));
		}
	}
}

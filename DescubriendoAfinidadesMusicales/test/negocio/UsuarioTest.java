package negocio;

import org.junit.Test;

public class UsuarioTest{
	@Test
    public void testInteresesMinimosValidos() {
		Usuario usuario = new Usuario("1","test", 1, 1, 1, 1);
    }
	
	@Test
	public void testInteresesMaximosValidos() {
		Usuario usuario = new Usuario("1", "test", 5, 5, 5, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInteresFolcloreMenorAUno() {
		int datoInvalido = 0;
		Usuario usuario = new Usuario("1", "test", datoInvalido, 1, 1, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInteresTangoMenorAUno() {
		int datoInvalido = 0;
		Usuario usuario = new Usuario("1", "test", 1, datoInvalido, 1, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInteresRockNacionalMenorAUno() {
		int datoInvalido = 0;
		Usuario usuario = new Usuario("1", "test", 1, 1, datoInvalido, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInteresUrbanoMenorAUno() {
		int datoInvalido = 0;
		Usuario usuario = new Usuario("1", "test", 1, 1, 1, datoInvalido);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInteresFolcloreMayorACinco() {
		int datoInvalido = 6;
		Usuario usuario = new Usuario("1", "test", datoInvalido, 5, 5, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInteresTangoMayorACinco() {
		int datoInvalido = 6;
		Usuario usuario = new Usuario("1", "test", 5, datoInvalido, 5, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInteresRockNacionalMayorACinco() {
		int datoInvalido = 6;
		Usuario usuario = new Usuario("1", "test", 5, 5, datoInvalido, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInteresUrbanoMayorACinco() {
		int datoInvalido = 6;
		Usuario usuario = new Usuario("1", "test", 5, 5, 5, datoInvalido);
	}
	}
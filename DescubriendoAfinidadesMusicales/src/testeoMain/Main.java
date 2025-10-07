package testeoMain;
//import java.util.LinkedList;

import controlador.Controlador;
//import pantalla.*;
//
//import negocio.IndiceDeSimilaridad;
//import negocio.Usuario;
//import pruebas.Prueba;
public class Main {

	public static void main(String[] args) {
		//Prueba prueba = new Prueba();
		//IndiceDeSimilaridad indice = new IndiceDeSimilaridad (prueba.cargarUsuarios());
		//indice.MostrarSimilaridades();
//		LinkedList<String> lista = new LinkedList<String>();
//		LinkedList<String> lista2 = new LinkedList<String>();
//		String a = "a";
//		String b = "b";
//		lista.add("a");
//		lista.add(b);
//		lista2.add("a");
//		lista2.add(b);
//		
//		if(lista.equals(lista2)) {
//			System.out.println("Bien");
//		}
//		
//		Controlador c = new Controlador();
//		c.mostrarPantallaAltaUsuario();
		
		Controlador controlador = new Controlador();
		controlador.iniciarAplicacion();
	}

}

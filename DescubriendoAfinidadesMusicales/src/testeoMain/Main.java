package testeoMain;
//import java.util.LinkedList;

import java.util.HashMap;

import controlador.Controlador;
import negocio.Usuario;
//import pantalla.*;
//
//import negocio.IndiceDeSimilaridad;
//import negocio.Usuario;
//import pruebas.Prueba;
public class Main {

	public static void main(String[] args) {		
		Controlador controlador = new Controlador();
		controlador.iniciarAplicacion();
		System.out.println(controlador.obtenerNombreUsuarios());
	}

}

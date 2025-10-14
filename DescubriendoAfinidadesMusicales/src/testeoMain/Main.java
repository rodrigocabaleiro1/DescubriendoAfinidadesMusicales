package testeoMain;
//import java.util.LinkedList;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import controlador.Controlador;
import negocio.Usuario;
import pantalla.GrafoCircular;
//import pantalla.*;
//
//import negocio.IndiceDeSimilaridad;
//import negocio.Usuario;
//import pruebas.Prueba;
public class Main {

	public static void main(String[] args) {		
		Controlador controlador = new Controlador();
		controlador.iniciarAplicacion();
		
		
		
	}
	public void pruebaGC() {
		JFrame f = new JFrame("Grafo Circular");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 600);
        
        List<String> nombres = new ArrayList<>();
        nombres.add("Goku");
        nombres.add("Vegeta");
        nombres.add("krillin");
        nombres.add("Gohan");
        nombres.add("trunks");
        nombres.add("picolo");
        
        int cantNodos = nombres.size();
        List<Point> aristas = new ArrayList<>();
        for(int x = 0; x<cantNodos-1; x++) {
        	Random random = new Random();
        	int numero = random.nextInt(cantNodos-x-1) + x;
        	aristas.add(new Point(x , numero));
        }
        //f.add(new GrafoCircular(nombres, 250, 300, 300, aristas));
        f.setVisible(true);
        System.out.println(aristas);
	}
}

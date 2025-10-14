package pantalla;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class GrafoCircular extends JPanel{
		private final List<Point> posiciones = new ArrayList<>();
		private List<String> nombres = new ArrayList<>();
		private List<Point> aristas;
		private int cantidadNodos;

	    public GrafoCircular(List<String> nombres, int radio, int centroX, int centroY, List<Point> aristas) {
	    	validarAristasYNodos(aristas, nombres);
	    	this.aristas = aristas;
	    	this.nombres = nombres;
	    	cantidadNodos = nombres.size();
	        determinarPosicionNodos(radio, centroX, centroY);
	    }

	    private void validarAristasYNodos(List<Point> aristas, List<String> nombres) {
			if(nombres.size() == 0) {
				throw new RuntimeException("No hay nodos en este Grafo");
			}
	    	if(aristas == null) {
				throw new RuntimeException("no existen aristas");
			}
			
		}

		@Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        dibujarAristas(g);
	        dibujarVertices(g);
	    }
	    
	    private void determinarPosicionNodos(int radio, int centroX, int centroY) {
			for (int i = 0; i < this.cantidadNodos; i++) {
	            double angulo = obtenerAnguloNodo(this.cantidadNodos, i);
	            int x = obtenerXNodo(radio, centroX, angulo);
	            int y = obtenerYNodo(radio, centroY, angulo);
	            posiciones.add(new Point(x, y));
	        }
		}
	    
		private void dibujarVertices(Graphics g) {
			g.setColor(Color.BLUE);
	        	char letra;
	        for (int i = 0; i < this.cantidadNodos; i++) {
	        	letra = this.nombres.get(i).charAt(0);
	            Point poscicion = posiciones.get(i);
	            g.fillOval(poscicion.x - 10, poscicion.y - 10, 20, 20);
	            g.setColor(Color.WHITE);
	            g.drawString(""+letra, poscicion.x - 4, poscicion.y + 4);
	            g.setColor(Color.BLUE);
	        }
		}

		private void dibujarAristas(Graphics g) {
			g.setColor(Color.BLACK);
	        for(Point nodosIndice: aristas) {
	        	 Point a = posiciones.get(nodosIndice.x);
	             Point b = posiciones.get(nodosIndice.y);
	             g.drawLine(a.x, a.y, b.x, b.y);
	        }
		}
		
		private int obtenerYNodo(int radio, int centroY, double angulo) {
			return (int) (centroY + radio * Math.sin(angulo));
		}

		private int obtenerXNodo(int radio, int centroX, double angulo) {
			return (int) (centroX + radio * Math.cos(angulo));
		}

		private double obtenerAnguloNodo(int cantidadNodos, int indiceNodo) {
			double circuloCompleto = 2 * Math.PI;
			return  circuloCompleto * indiceNodo / cantidadNodos;
		}

	
	}


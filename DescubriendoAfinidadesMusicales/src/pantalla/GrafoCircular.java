package pantalla;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;


public class GrafoCircular extends JPanel{
		private final List<Point> posiciones = new ArrayList<>();
		private int diametroNodo;
		private List<String> nombres = new ArrayList<>();
		private List<Point> aristas;
		private Map<String, Color> coloresNodos;
		private int cantidadNodos;

	    public GrafoCircular(List<String> nombres, int radio, int centroX, int centroY, List<Point> aristas) {
	    	validarAristasYNodos(aristas, nombres);
	    	this.aristas = aristas;
	    	this.nombres = nombres;
	    	cantidadNodos = nombres.size();
	    	diametroNodo = 20;
	    	coloresNodos = new HashMap<String, Color>();
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
	        	Color color;
	        	String nombre = this.nombres.get(i);
	        	letra = nombre.charAt(0);
	            Point poscicion = posiciones.get(i);
	            if(!coloresNodos.containsKey(nombre)) {
	            	color = Color.BLUE;
	            	coloresNodos.put(nombre, color);
	            }else {
	            	color = coloresNodos.get(nombre);
	            }
	            
	            dibujarVertice(g, letra, poscicion, color);
	        }
		}

		private void dibujarVertice(Graphics g, char letra, Point poscicion, Color color) {
			g.setColor(color);
			g.fillOval(poscicion.x - 10, poscicion.y - 10, diametroNodo, diametroNodo);
			g.setColor(Color.WHITE);
			g.drawString(""+letra, poscicion.x - 4, poscicion.y + 4);
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

		public String obtenerNodo(Point posicion) {
			String ret = null;
			int radioNodo = diametroNodo/2;
			for(int i = 0; i < this.posiciones.size(); i++) {
				String nombre = this.nombres.get(i);
				if(estaDentroDelCirculo(posiciones.get(i), radioNodo, posicion)) {
					ret = nombre;
					this.coloresNodos.put(nombre, Color.red);
					
				}
				else {
					this.coloresNodos.put(nombre, Color.blue);
				}
			}
			return ret;
		}
		

		private boolean estaDentroDelCirculo(Point circulo, int radio, Point otro) {
		    double diferenciax = otro.x - circulo.x;
		    double diferenciay = otro.y - circulo.y;
		    return (diferenciax * diferenciax + diferenciay * diferenciay) <= (radio * radio);
		}
	
	}


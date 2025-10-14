package pantalla;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import javax.swing.JPanel;

public class GrafoCircular extends JPanel{
    private List<Point> posiciones = new ArrayList<>();
    private int diametroNodo = 20;
    private List<String> idsUsuarios;
    private List<Point> aristas;
    private Map<Point, Color> coloresAristas = new HashMap<>();
    private Map<String, Color> coloresNodos = new HashMap<>();
    private int cantidadNodos;

    public GrafoCircular(List<String> idsUsuarios, int radio, int centroX, int centroY, List<Point> aristas){
        if(idsUsuarios == null || idsUsuarios.isEmpty()) throw new RuntimeException("No hay nodos");
        if(aristas == null) aristas = new ArrayList<>();

        this.idsUsuarios = idsUsuarios;
        this.aristas = aristas;
        this.cantidadNodos = idsUsuarios.size();
        determinarPosicionNodos(radio, centroX, centroY);
    }

    private void determinarPosicionNodos(int radio, int centroX, int centroY){
        posiciones.clear();
        for(int i=0;i<cantidadNodos;i++){
            double angulo = 2*Math.PI*i/cantidadNodos;
            int x = (int)(centroX + radio*Math.cos(angulo));
            int y = (int)(centroY + radio*Math.sin(angulo));
            posiciones.add(new Point(x,y));
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        dibujarAristas(g);
        dibujarVertices(g);
    }

    private void dibujarVertices(Graphics g){
        for(int i=0;i<cantidadNodos;i++){
            String id = idsUsuarios.get(i);
            Point pos = posiciones.get(i);
            Color color = coloresNodos.getOrDefault(id, Color.BLUE);
            g.setColor(color);
            g.fillOval(pos.x - diametroNodo/2, pos.y - diametroNodo/2, diametroNodo, diametroNodo);
            g.setColor(Color.WHITE);
            g.drawString(id, pos.x - diametroNodo/4, pos.y + diametroNodo/4);
        }
    }

    private void dibujarAristas(Graphics g){
        for(Point a : aristas){
            Point p1 = posiciones.get(a.x);
            Point p2 = posiciones.get(a.y);
            Color color = coloresAristas.getOrDefault(a, Color.BLACK);
            g.setColor(color);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public String obtenerNodo(Point clic){
        String seleccionado = null;
        int radio = diametroNodo/2;
        for(int i=0;i<posiciones.size();i++){
            Point pos = posiciones.get(i);
            String id = idsUsuarios.get(i);
            if(estaDentroDelCirculo(pos, radio, clic)){
                seleccionado = id;
                coloresNodos.put(id, Color.RED);
            } else {
                coloresNodos.put(id, Color.BLUE);
            }
        }
        repaint();
        return seleccionado;
    }

    private boolean estaDentroDelCirculo(Point centro,int radio, Point clic){
        int dx = clic.x - centro.x;
        int dy = clic.y - centro.y;
        return dx*dx + dy*dy <= radio*radio;
    }

    public void setColoresAristas(Map<Point, Color> map){
        this.coloresAristas = map;
        repaint();
    }
}

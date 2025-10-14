package pantalla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;

public class VistaGrafo extends Pantalla{
	private final Controlador controlador;
	private JPanel header, body, footer;
    private JLabel tituloLabel;
    private JPanel grafo, datosNodo;
    private String nodoSeleccionado;
    private JTextArea usuarioDatos;
    
	public VistaGrafo(Controlador controlador) {
		super("Grafo Representado Graficamente", 20, 20, 800, 800);
        this.controlador = controlador;
        inicializarComponentes();
	}

	private void inicializarComponentes() {
		header = new JPanel();
        body = new JPanel(new BorderLayout());
        footer = new JPanel(new GridLayout(1, 2, 10, 10));
        datosNodo = new JPanel();
        usuarioDatos = new JTextArea();
        generarGrafo();

        tituloLabel = new JLabel("Grupos de Usuarios", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 22));
        agregarElementosPanel(header, tituloLabel);

        agregarElementosPanel(getContentPane(), header, BorderLayout.NORTH);
        agregarElementosPanel(getContentPane(), body, BorderLayout.CENTER);
        agregarElementosPanel(getContentPane(), footer, BorderLayout.SOUTH);
        agregarElementosPanel(getContentPane(), datosNodo, BorderLayout.EAST);
        datosNodo.add(usuarioDatos);
        body.add(grafo);

        definirColorDeFondo(header, Color.LIGHT_GRAY);
        definirColorDeFondo(body, Color.LIGHT_GRAY);
        definirColorDeFondo(footer, Color.LIGHT_GRAY);

        establecerBorde(header, 20, 20, 20, 20);
        establecerBorde(body, 10, 20, 10, 20);
        establecerBorde(footer, 10, 20, 20, 20);
        
        grafo.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                nodoSeleccionado = ((GrafoCircular)grafo).obtenerNodo(new Point(x,y));
                mostrarDatosNodo();
                grafo.repaint();
            }

		private void mostrarDatosNodo() {
			if(nodoSeleccionado != null) {
				usuarioDatos.setText(informacionUsuario(nodoSeleccionado));
				usuarioDatos.setPreferredSize(new Dimension(200, usuarioDatos.getPreferredSize().height));
				usuarioDatos.setEditable(false);
				usuarioDatos.setOpaque(false);
				usuarioDatos.setFocusable(false);
				usuarioDatos.setLineWrap(true);
            }else {
            	usuarioDatos.setText("");
				usuarioDatos.setPreferredSize(new Dimension(0, usuarioDatos.getPreferredSize().height));
            	usuarioDatos.setBorder(null);
            }
			
		}
        });

	}
	
	private String informacionUsuario(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append(controlador.obtenerInformacionUsuario(id));
		return sb.toString();
	}

	private void generarGrafo() {
		List<String> identificadores = obtenerListaID();
		List<Point> aristas = obtenerAristas();
		grafo = new GrafoCircular(identificadores, 250, 270, 270, aristas);
		
	}

	private List<Point> obtenerAristas() {
		return controlador.obtenerAristas();
	}

	private List<String> obtenerListaID() {
		Set<String> claves = controlador.obtenerIdsUsuarios();
		List<String> identificadores = new ArrayList<>(claves);
		return identificadores;
	}
}

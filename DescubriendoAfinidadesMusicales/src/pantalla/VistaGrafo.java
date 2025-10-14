package pantalla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controlador.Controlador;

public class VistaGrafo extends Pantalla{
	private final Controlador controlador;
	private JPanel header, body, footer;
    private JLabel tituloLabel;
    private JPanel grafo;
    
	public VistaGrafo(Controlador controlador) {
		super("Usuarios Registrados", 20, 20, 800, 800);
        this.controlador = controlador;
        inicializarComponentes();
	}

	private void inicializarComponentes() {
		header = new JPanel();
        body = new JPanel(new BorderLayout());
        footer = new JPanel(new GridLayout(1, 2, 10, 10));
        generarGrafo();

        tituloLabel = new JLabel("Usuarios Registrados", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 22));
        agregarElementosPanel(header, tituloLabel);

        agregarElementosPanel(getContentPane(), header, BorderLayout.NORTH);
        agregarElementosPanel(getContentPane(), body, BorderLayout.CENTER);
        agregarElementosPanel(getContentPane(), footer, BorderLayout.SOUTH);
        body.add(grafo);

        definirColorDeFondo(header, Color.LIGHT_GRAY);
        definirColorDeFondo(body, Color.LIGHT_GRAY);
        definirColorDeFondo(footer, Color.LIGHT_GRAY);

        establecerBorde(header, 20, 20, 20, 20);
        establecerBorde(body, 10, 20, 10, 20);
        establecerBorde(footer, 10, 20, 20, 20);
        
        

	}

	private void generarGrafo() {
		List<String> identificadores = obtenerListaID();
		List<Point> aristas = obtenerAristas();
		grafo = new GrafoCircular(identificadores, 250, 300, 300, aristas);
		
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

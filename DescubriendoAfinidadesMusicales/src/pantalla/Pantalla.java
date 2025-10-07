package pantalla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public abstract class Pantalla extends JFrame{
	
	public Pantalla(String titulo, int xPosicion, int yPosicion, int anchoVentana, int altoVentana) {
		setTitle(titulo);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(xPosicion, yPosicion, anchoVentana, altoVentana);
	}
	
	protected void modificarAnchoPanel(JPanel panel, int ancho) {
		FlowLayout fl_panel = (FlowLayout) panel.getLayout();
		fl_panel.setHgap(ancho);
	}


	protected void modificarAlturaPanel(JPanel panel, int altura) {
		FlowLayout fl_Panel = (FlowLayout) panel.getLayout();
		fl_Panel.setVgap(altura);
	}


	protected void definirColorDeFondo(JPanel panel, Color color) {
		panel.setBackground(color);
	}
	
	protected void definirColorDeFondo(JButton boton, Color color) {
		boton.setBackground(color);
	}
	
	protected void agregarElementosPanel(Container container, java.awt.Component elemento, String layout) {
	    container.add(elemento, layout);
	}
	
	protected void agregarElementosPanel(Container container, JLabel elemento, String layout) {
		container.add(elemento, layout);
	}
	protected void agregarElementosPanel(Container container, JButton elemento, String layout) {
		container.add(elemento, layout);
	}
	protected void agregarElementosPanel(Container container, JLabel elemento) {
		container.add(elemento);
	}
	protected void agregarElementosPanel(Container container, JButton elemento) {
		container.add(elemento);
	}
	protected void agregarElementosPanel(Container container, JTextField elemento) {
		container.add(elemento);
	}
	protected void pintarTexto(JLabel texto, Color color) {
		texto.setForeground(color);
	}
	protected void establecerGrilla(JPanel contenedor, int filas, int columnas) {
		contenedor.setLayout(new GridLayout(filas, columnas));
	}

	protected void establecerBorde(JPanel contenedor, int arriba, int izquierda, int abajo, int derecha) {
		contenedor.setBorder(new EmptyBorder(arriba, izquierda, abajo, derecha));
	}
	
	protected void cambiarDistanciaEntreCeldas(GridLayout layout, int espacioVertical, int espacioVorizontal) {
		layout.setHgap(espacioVorizontal);
		layout.setVgap(espacioVertical);
	}
}

package pantalla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controlador.Controlador;

public class AltaUsuario extends Pantalla{
	JPanel header, body, footer, datos;
	JLabel tituloLabel, nombreLabel, interesFolcloreLabel, interesTangoLabel, interesRockNacionalLabel, 
	interesUrbanoLabel, resultado;
	JButton enviarDatos;
	JTextField nombre, interesFolclore, interesTango, interesRockNacional, interesUrbano;
	Controlador controlador;
	public AltaUsuario(Controlador controlador) {
		super("Alta Usuarios", 20, 20, 480, 640);
		this.controlador = controlador;
		
		header = new JPanel();
		body = new JPanel();		
		footer = new JPanel();
		datos = new JPanel();
		
		tituloLabel = new JLabel("Alta de Usuarios");
		nombreLabel = new JLabel("Nombre: ");
		interesFolcloreLabel = new JLabel("Interes Folclore: ");
		interesTangoLabel = new JLabel("Interes Tango: ");
		interesRockNacionalLabel = new JLabel("Interes Rock Nacional: ");
		interesUrbanoLabel = new JLabel("Interes Urbano: ");
		resultado = new JLabel();
		
		nombre = new JTextField();
		interesFolclore = new JTextField();
		interesTango = new JTextField();
		interesRockNacional = new JTextField();
		interesUrbano = new JTextField();
		
		enviarDatos = new JButton ("Cargar Datos");
		definirFuncionalidadEnviarDatos();
		
		agregarElementosPanel(getContentPane(), header, BorderLayout.NORTH);
		agregarElementosPanel(getContentPane(), body, BorderLayout.CENTER);
		agregarElementosPanel(getContentPane(), footer, BorderLayout.SOUTH);
		
		agregarElementosPanel(body, datos, BorderLayout.SOUTH);
		
		agregarElementosPanel(header, tituloLabel);

		agregarElementosPanel(datos, nombreLabel);
		agregarElementosPanel(datos, nombre);
		
		agregarElementosPanel(datos, interesFolcloreLabel);
		agregarElementosPanel(datos, interesFolclore);
		
		agregarElementosPanel(datos, interesTangoLabel);
		agregarElementosPanel(datos, interesTango);
		
		agregarElementosPanel(datos, interesRockNacionalLabel);
		agregarElementosPanel(datos, interesRockNacional);
		
		agregarElementosPanel(datos, interesUrbanoLabel);
		agregarElementosPanel(datos, interesUrbano);
		
		agregarElementosPanel(footer, resultado);
		agregarElementosPanel(footer, enviarDatos);
		
		
		establecerBorde(header, 20, 20, 20, 20);
		establecerBorde(body, 20, 20, 0, 20);
		establecerBorde(footer, 0, 20, 50, 20);
		
		establecerGrilla(datos, 5 ,2);
		establecerGrilla(footer, 0, 1);
		
		datos.setPreferredSize(new Dimension(400, 150 + 20*5));
		
		
		definirColorDeFondo(header, Color.LIGHT_GRAY);
		definirColorDeFondo(body, Color.LIGHT_GRAY);
		definirColorDeFondo(footer, Color.LIGHT_GRAY);
		definirColorDeFondo(datos, null);
		cambiarDistanciaEntreCeldas((GridLayout)datos.getLayout(), 20, 5);
		cambiarDistanciaEntreCeldas((GridLayout)footer.getLayout(), 20, 0);
		
		resultado.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private void definirFuncionalidadEnviarDatos() {
		enviarDatos.addActionListener(e -> cargarDatos());
	}

	private void cargarDatos() {
		String nombre = this.nombre.getText();
		String interesFolclore = this.interesFolclore.getText();
		String interesTango = this.interesTango.getText();
		String interesRockNacional = this.interesRockNacional.getText();
		String interesUrbano = this.interesUrbano.getText();
		
		try {
			validarDatos(nombre,interesFolclore,interesTango,interesRockNacional,interesUrbano);
			controlador.altaUsuario(nombre, convertirNumero(interesFolclore), convertirNumero(interesTango),
					convertirNumero(interesRockNacional), convertirNumero(interesUrbano));
			resultado.setText("¡Los datos se han cargado con Exito!");
			vaciarCampos();
		} catch(Exception e) {
			resultado.setText("¡Error! " + e.getMessage());
		}
	}
	
		private void vaciarCampos() {
			this.nombre.setText("");
			this.interesFolclore.setText("");
			this.interesTango.setText("");
			this.interesRockNacional.setText("");
			this.interesUrbano.setText("");
		
	}

		//---------------------------------------------
		//	Validaciones
		//---------------------------------------------
		private void validarDatos(String nombre, String interesFolclore, String interesTango,
				String interesRockNacional, String interesUrbano) {
			validarDato(nombre, "Nombre");
			validarInteres(interesFolclore, "Interes Folclore");
			validarInteres(interesTango, "Interes Tango");
			validarInteres(interesUrbano, "Interes Urbano");
			validarInteres(interesRockNacional, "Interes RockNacional");
		}

		private void validarDato(String dato, String nombreDato) {
			if(datoVacio(dato)) {
				throw new IllegalArgumentException ("El dato '" + nombreDato + "' no puede estar vacio");
			}
			
		}

		private boolean datoVacio(String dato) {
			return dato.length() == 0 || dato == "" || dato == " " || dato == null;
		}

		private void validarInteres(String interes, String nombreInteres) {
			validarDato(interes, nombreInteres);
			validarValorInteres(interes, nombreInteres);
		}

		private void validarValorInteres(String interes, String nombreInteres) {
			esNumero(interes, nombreInteres);
			int numero = convertirNumero(interes);
			
		}

		private void esNumero(String interes, String nombreInteres) {
			if (noEsNumero(interes)) {
				throw new NumberFormatException ("El dato '" + nombreInteres + "' debe ser un Número");
			}
			
		}

		private boolean noEsNumero(String interes) {
			try {
		        int n = convertirNumero(interes);
		        return false;
		    } catch (NumberFormatException e) {
		        return true;
		    }
		}
		private int convertirNumero(String numero) {
			return Integer.parseInt(numero);
		}
}

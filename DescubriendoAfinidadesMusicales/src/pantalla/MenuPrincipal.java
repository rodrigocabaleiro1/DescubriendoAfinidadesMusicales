package pantalla;

import controlador.Controlador;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MenuPrincipal extends Pantalla {

    private final Controlador controlador;
    private JPanel header, body, footer;
    private JLabel tituloLabel;

    public MenuPrincipal(Controlador controlador) {
        super("Menú Principal", 20, 20, 400, 400);
        this.controlador = controlador;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        header = new JPanel();
        body = new JPanel(new GridLayout(3, 1, 10, 10));
        footer = new JPanel();

        tituloLabel = new JLabel("Menú Principal", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 22));
        agregarElementosPanel(header, tituloLabel);

        JButton btnAlta = new JButton("Dar de alta usuario");
        JButton btnVisualizar = new JButton("Visualizar / Eliminar usuarios");
        JButton btnVerGrafo = new JButton("Visualizar grafo (no implementado)");

        body.add(btnAlta);
        body.add(btnVisualizar);
        body.add(btnVerGrafo);

        agregarElementosPanel(getContentPane(), header, BorderLayout.NORTH);
        agregarElementosPanel(getContentPane(), body, BorderLayout.CENTER);
        agregarElementosPanel(getContentPane(), footer, BorderLayout.SOUTH);

        definirColorDeFondo(header, Color.LIGHT_GRAY);
        definirColorDeFondo(body, Color.LIGHT_GRAY);
        definirColorDeFondo(footer, Color.LIGHT_GRAY);

        establecerBorde(header, 20, 20, 20, 20);
        establecerBorde(body, 10, 20, 10, 20);
        establecerBorde(footer, 10, 20, 20, 20);

        btnAlta.addActionListener(e -> controlador.mostrarPantallaAltaUsuario());
        btnVisualizar.addActionListener(e -> controlador.mostrarPantallaVisualizarUsuarios());
        btnVerGrafo.addActionListener(e -> controlador.mostrarPantallaGrafo());
    }
}

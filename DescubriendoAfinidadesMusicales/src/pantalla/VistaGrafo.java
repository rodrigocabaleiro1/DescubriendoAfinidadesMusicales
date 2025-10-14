package pantalla;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;

import controlador.Controlador;
import negocio.Arista;

public class VistaGrafo extends Pantalla {
    private final Controlador controlador;
    private JPanel header, body, footer;
    private JLabel tituloLabel;
    private JPanel grafo;
    private String nodoSeleccionado;
    private JTextPane usuarioDatos;
    private JScrollPane scrollDatos;

    public VistaGrafo(Controlador controlador) {
        super("Grafo Representado Graficamente", 20, 20, 900, 700);
        this.controlador = controlador;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        header = new JPanel();
        body = new JPanel(new BorderLayout());
        footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        usuarioDatos = new JTextPane();
        usuarioDatos.setContentType("text/html");
        usuarioDatos.setEditable(false);
        usuarioDatos.setBackground(new Color(245,245,245));
        usuarioDatos.setBorder(BorderFactory.createTitledBorder("Información del Usuario"));

        scrollDatos = new JScrollPane(usuarioDatos);
        scrollDatos.setPreferredSize(new Dimension(250, 0));

        // Header
        tituloLabel = new JLabel("Grupos de Usuarios", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 22));
        header.add(tituloLabel);

        // Layout principal
        getContentPane().setLayout(new BorderLayout(10,10));
        getContentPane().add(header, BorderLayout.NORTH);
        getContentPane().add(body, BorderLayout.CENTER);
        getContentPane().add(footer, BorderLayout.SOUTH);
        getContentPane().add(scrollDatos, BorderLayout.EAST);

        generarGrafo();

        // Botón para destacar grupos
        JButton btnGrupos = new JButton("Destacar Grupos");
        btnGrupos.addActionListener(e -> {
            List<Arista> g1 = new ArrayList<>();
            List<Arista> g2 = new ArrayList<>();
            String info = controlador.dividirAGMPorMayorPeso(g1, g2);

            Map<Point, Color> colores = new HashMap<>();
            for(Arista a : g1) colores.put(new Point(a.obtenerOrigen(), a.obtenerDestino()), Color.BLUE);
            for(Arista a : g2) colores.put(new Point(a.obtenerOrigen(), a.obtenerDestino()), Color.GREEN);

            if(!g1.isEmpty() && !g2.isEmpty()){
                Arista mayor = g1.get(0);
                colores.put(new Point(mayor.obtenerOrigen(), mayor.obtenerDestino()), Color.RED);
            }

            if(grafo instanceof GrafoCircular){
                ((GrafoCircular)grafo).setColoresAristas(colores);
                grafo.repaint();
            }

            usuarioDatos.setText("<html><pre>" + info + "</pre></html>");
        });
        footer.add(btnGrupos);

        // Listener click en nodos
        if(grafo != null){
            grafo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    int x = e.getX();
                    int y = e.getY();
                    nodoSeleccionado = ((GrafoCircular)grafo).obtenerNodo(new Point(x,y));
                    mostrarDatosNodo();
                }

                private void mostrarDatosNodo() {
                    if(nodoSeleccionado != null){
                        usuarioDatos.setText(crearHtmlInfoUsuario(nodoSeleccionado));
                    }
                }
            });
        }
    }

    private String crearHtmlInfoUsuario(String id){
        try {
            negocio.Usuario u = controlador.obtenerUsuarios().get(id);
            if(u != null){
                return "<html>" +
                        "<b>Usuario:</b> " + u.nombre() + "<br>" +
                        "<b>ID:</b> " + u.obtenerID() + "<br>" +
                        "<b>Tango:</b> " + u.interesTango() + "<br>" +
                        "<b>Folclore:</b> " + u.interesFolclore() + "<br>" +
                        "<b>Rock Nacional:</b> " + u.interesRockNacional() + "<br>" +
                        "<b>Urbano:</b> " + u.interesUrbano() +
                        "</html>";
            }
        } catch(Exception e){
            return "<html>Información no disponible</html>";
        }
        return "<html>Usuario no encontrado</html>";
    }

    private void generarGrafo(){
        List<String> ids = new ArrayList<>(controlador.obtenerIdsUsuarios());
        List<Point> aristas = controlador.obtenerAristas();
        if(ids.isEmpty()){
            grafo = new JPanel();
            grafo.add(new JLabel("No hay datos para mostrar el grafo."));
        } else {
            grafo = new GrafoCircular(ids, 250, 270, 270, aristas);
        }
        body.add(grafo, BorderLayout.CENTER);
    }
}

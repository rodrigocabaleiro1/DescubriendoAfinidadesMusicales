package pantalla;

import controlador.Controlador;
import negocio.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class VisualizarUsuarios extends Pantalla {

    private final Controlador controlador;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaUsuarios;
    private Map<Integer, String> usuariosId;

    private JPanel header, body, footer;
    private JLabel tituloLabel;

    public VisualizarUsuarios(Controlador controlador) {
        super("Usuarios Registrados", 20, 20, 400, 500);
        this.controlador = controlador;
        usuariosId = new HashMap<Integer, String>();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        header = new JPanel();
        body = new JPanel(new BorderLayout());
        footer = new JPanel(new GridLayout(1, 2, 10, 10));

        tituloLabel = new JLabel("Usuarios Registrados", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 22));
        agregarElementosPanel(header, tituloLabel);

        modeloLista = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloLista);
        JScrollPane scroll = new JScrollPane(listaUsuarios);
        body.add(scroll, BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar seleccionado");
        JButton btnActualizar = new JButton("Actualizar lista");
        footer.add(btnEliminar);
        footer.add(btnActualizar);

        agregarElementosPanel(getContentPane(), header, BorderLayout.NORTH);
        agregarElementosPanel(getContentPane(), body, BorderLayout.CENTER);
        agregarElementosPanel(getContentPane(), footer, BorderLayout.SOUTH);

        definirColorDeFondo(header, Color.LIGHT_GRAY);
        definirColorDeFondo(body, Color.LIGHT_GRAY);
        definirColorDeFondo(footer, Color.LIGHT_GRAY);

        establecerBorde(header, 20, 20, 20, 20);
        establecerBorde(body, 10, 20, 10, 20);
        establecerBorde(footer, 10, 20, 20, 20);

        btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());
        btnActualizar.addActionListener(e -> actualizarLista());

        actualizarLista();
    }

    public void actualizarListaUsuarios(Set<String> usuarios) {
        modeloLista.clear();
        if (usuarios != null && usuarios.size() > 0) {
            for (String clave : usuarios) {
            	usuariosId.put(modeloLista.getSize(), clave);
                modeloLista.addElement(controlador.nombreUsuario(clave));
            }
        } else {
            modeloLista.addElement("No hay usuarios cargados.");
        }
    }

    private void actualizarLista() {
        actualizarListaUsuarios(controlador.obtenerIdsUsuarios());
    }

    private void eliminarUsuarioSeleccionado() {
        int index = listaUsuarios.getSelectedIndex();
        if (index == -1 || modeloLista.getElementAt(index).equals("No hay usuarios cargados.")) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario válido para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombreSeleccionado = modeloLista.getElementAt(index);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar a " + nombreSeleccionado + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controlador.eliminarUsuario(usuariosId.get(index));
                actualizarLista();
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

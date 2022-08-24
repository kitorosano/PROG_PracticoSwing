package gestorempleados.Presentacion;

import gestorempleados.Logica.Clases.Empleado;
import gestorempleados.Logica.Fabrica;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

//TODO: Si la BD no esta conectada, mostrar la app con un mensaje de error.
public class Dashboard extends JFrame {

    private JPanel panelMain;
    private JTable tableEmpleados;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnRecargar;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JPanel panelBuscar;
    private JPanel panelAcciones;
    private JScrollPane panelTable;
    private JLabel lblTitulo;

    public Dashboard(String title) {
        super(title);
        setContentPane(panelMain);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        cargarEventos();
    }

    private void buscarEmpleadoTabla() {
        String busqueda = txtBuscar.getText();
        if (busqueda.isEmpty())
            recargarEmpleadosTabla(); // Tambien se podria remover esta primera parte y que no haga nada si no hay nada escrito en el campo de busqueda.
        else {
            Fabrica fabrica = Fabrica.getInstance();
            Empleado empleado = fabrica.getIEmpleado().get(busqueda);
            EmpleadosTableModel empleadosTableModel = new EmpleadosTableModel(empleado);
            tableEmpleados.setModel(empleadosTableModel);
        }
    }

    private void recargarEmpleadosTabla() {
        Fabrica fabrica = Fabrica.getInstance();
        try {
            List<Empleado> empleados = fabrica.getIEmpleado().getAll();
            EmpleadosTableModel empleadosTableModel = new EmpleadosTableModel(empleados);
            tableEmpleados.setModel(empleadosTableModel);
            if(empleadosTableModel.getRowCount() == 0)
                JOptionPane.showMessageDialog(this, "No hay empleados registrados");
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Recargar Empleados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void nuevoEmpleadoTabla() {
        JFrame frame = new Formulario("Nuevo empleado");
        mostrarFormulario(frame);
    }

    private void editarEmpleadoTabla() {
        int filaSeleccionada = tableEmpleados.getSelectedRow();
        if (filaSeleccionada == -1)
            JOptionPane.showMessageDialog(this, "Por favor, asegurate de seleccionar un empleado para poder editar", "Editar empleado", JOptionPane.WARNING_MESSAGE);
        else {
            Fabrica fabrica = Fabrica.getInstance();
            String correoSeleccionado = tableEmpleados.getValueAt(filaSeleccionada, 0).toString();
            Empleado empleadoSeleccionado = fabrica.getIEmpleado().get(correoSeleccionado);

            JFrame frame = new Formulario("Editar empleado", empleadoSeleccionado);
            mostrarFormulario(frame);
        }
    }

    private void eliminarEmpleadoTabla() {
        int filaSeleccionada = tableEmpleados.getSelectedRow();
        if (filaSeleccionada == -1)
            JOptionPane.showMessageDialog(this, "Por favor, asegurate de seleccionar un empleado para poder editar", "Editar empleado", JOptionPane.WARNING_MESSAGE);
        else {
            String correoSeleccionado = tableEmpleados.getValueAt(filaSeleccionada, 0).toString();
            Fabrica fabrica = Fabrica.getInstance();
            try {
                fabrica.getIEmpleado().delete(correoSeleccionado);
                recargarEmpleadosTabla();
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Eliminar Empleado", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarFormulario(JFrame frame) {
        frame.setBounds(this.getBounds().x + 200, this.getBounds().y + 100, 400, 250);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                recargarEmpleadosTabla();
            }
        });
    }

    private static class EmpleadosTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Correo", "Nombre", "Apellido", "Teléfono", "Fecha de nacimiento"};
        private List<Empleado> empleados;

        private EmpleadosTableModel(Empleado empleado) {
            empleados = new ArrayList<>();
            this.empleados.add(empleado);
        }

        private EmpleadosTableModel(List<Empleado> empleados) {
            this.empleados = empleados;
        }

        @Override
        public int getRowCount() {
            return empleados.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Empleado empleado = empleados.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return empleado.getCorreo();
                case 1:
                    return empleado.getNombre();
                case 2:
                    return empleado.getApellido();
                case 3:
                    return empleado.getTelefono();
                case 4:
                    return empleado.getFechaNacimiento();
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }

                private void cargarEventos() {
                    txtBuscar.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                                buscarEmpleadoTabla();
                            }
                        }
                    });
                    btnBuscar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            buscarEmpleadoTabla();
                        }
                    });
                    btnRecargar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            recargarEmpleadosTabla();
            }
        });
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nuevoEmpleadoTabla();
            }
        });
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarEmpleadoTabla();
            }
        });
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleadoTabla();
            }
        });
    }
}

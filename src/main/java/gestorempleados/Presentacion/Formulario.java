package gestorempleados.Presentacion;

import gestorempleados.Logica.Clases.Empleado;
import gestorempleados.Logica.Fabrica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class Formulario extends JFrame {
    private JPanel panelMain;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
    private JTextField txtNombre;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JLabel lblTelefono;
    private JLabel lblFechaNac;
    private JTextField txtFechaNac;
    private JButton btnGuardar;
    private JLabel lblTitulo;

    public Formulario(String title) {
        super(title);
        setContentPane(panelMain);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        lblTitulo.setText(title);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Empleado empleado = new Empleado();
                guardarEmpleadosTabla(empleado);
            }
        });

    }

    public Formulario(String title, Empleado empleado) {
        super(title);
        setContentPane(panelMain);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        lblTitulo.setText(title);

        completarFormulario(empleado);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarEmpleadosTabla(empleado);
            }
        });
    }

    private void completarFormulario(Empleado empleado) {
        txtCorreo.setText(empleado.getCorreo());
        txtCorreo.setEditable(false); // Evitar que se pueda editar el correo
        txtNombre.setText(empleado.getNombre());
        txtApellido.setText(empleado.getApellido());
        txtTelefono.setText(empleado.getTelefono());
        txtFechaNac.setText(empleado.getFechaNacimiento().toString());
    }

    private void guardarEmpleadosTabla(Empleado empleado) {
        if(txtCorreo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtFechaNac.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos");
        } else {
            empleado.setCorreo(txtCorreo.getText());
            empleado.setNombre(txtNombre.getText());
            empleado.setApellido(txtApellido.getText());
            empleado.setTelefono(txtTelefono.getText());
            try {
                empleado.setFechaNacimiento(LocalDate.parse(txtFechaNac.getText()));
                Fabrica fabrica = Fabrica.getInstance();
                fabrica.getIEmpleado().save(empleado);
                dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto; el formato es: yyyy-MM-dd");
            }

        }
    }
}

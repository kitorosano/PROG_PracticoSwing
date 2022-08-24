package gestorempleados.Presentacion;

import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;

public class GestorEmpleadosApplication {
    public static void main(String[] args) {
        Dotenv.configure()
            .filename(".env")
            .systemProperties()
            .load();

        JFrame frame = new Dashboard("Gestor de empleados");
        frame.setVisible(true);
    }
}
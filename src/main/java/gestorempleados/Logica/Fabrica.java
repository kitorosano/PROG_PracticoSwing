package gestorempleados.Logica;

import gestorempleados.Logica.Controladores.EmpleadoController;
import gestorempleados.Logica.Interfaces.IEmpleado;

public class Fabrica {
    private static Fabrica instance;
    private IEmpleado empleado;

    private Fabrica() {}

    public static Fabrica getInstance() {
        if (instance == null) {
            instance = new Fabrica();
        }
        return instance;
    }

    public IEmpleado getIEmpleado() {
        EmpleadoController empleadoController = EmpleadoController.getInstance();
        return empleadoController;
    }
}

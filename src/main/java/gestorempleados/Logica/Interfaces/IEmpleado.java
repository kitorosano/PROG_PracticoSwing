package gestorempleados.Logica.Interfaces;

import gestorempleados.Logica.Clases.Empleado;

import java.util.List;

public interface IEmpleado {
    List<Empleado> getAll();
    Empleado get(String correo);
    void save(Empleado empleado);
    void delete(String correo);
}

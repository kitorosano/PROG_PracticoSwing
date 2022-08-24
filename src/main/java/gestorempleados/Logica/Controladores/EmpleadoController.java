package gestorempleados.Logica.Controladores;

import gestorempleados.Logica.Clases.Empleado;
import gestorempleados.Logica.Interfaces.IEmpleado;
import gestorempleados.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoController implements IEmpleado {
    private static EmpleadoController instance;
    private List<Empleado> empleados = new ArrayList<>();

    private EmpleadoController() {
    }

    public static EmpleadoController getInstance() {
        if (instance == null) {
            instance = new EmpleadoController();
        }
        return instance;
    }

    @Override
    public List<Empleado> getAll() {
        empleados.clear(); // Limpiamos la lista para que no se repitan los datos
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM empleado";
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String correo = resultSet.getString("correo");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String tel = resultSet.getString("tel");
                LocalDate nacimiento = resultSet.getDate("nacimiento").toLocalDate();

                Empleado empleado = new Empleado(correo, nombre, apellido, tel, nacimiento);
                empleados.add(empleado);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los empleados", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
            }
        }
        return empleados;
    }

    @Override
    public Empleado get(String correo) {
        if (empleados.isEmpty())
            getAll();
        for (Empleado empleado : empleados) {
            if (empleado.getCorreo().contains(correo)) {
                return empleado;
            }
        }
        return null;
    }

    @Override
    public void save(Empleado empleado) {
        Connection connection = null;
        Statement statement = null;
        // Si no existe el empleado en la base de datos, lo insertamos
        if (this.get(empleado.getCorreo()) == null) {
            String query = String.format("INSERT INTO empleado VALUES ('%s', '%s', '%s', '%s', '%s')",
                    empleado.getCorreo(), empleado.getNombre(), empleado.getApellido(), empleado.getTelefono(), empleado.getFechaNacimiento().toString());
            try {
                connection = ConexionDB.getConnection();
                statement = connection.createStatement();
                statement.executeUpdate(query);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Error al conectar con la base de datos", e);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Error al insertar el empleado", e);
            } finally {
                try {
                    if (statement != null) statement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
                }
            }
        }
        // Si ya existe el empleado, lo actualiza
        else {
            String query = String.format("UPDATE empleado SET nombre = '%s', apellido = '%s', tel = '%s', nacimiento = '%s' WHERE correo = '%s'",
                    empleado.getNombre(), empleado.getApellido(), empleado.getTelefono(), empleado.getFechaNacimiento().toString(), empleado.getCorreo());
            try {
                connection = ConexionDB.getConnection();
                statement = connection.createStatement();
                statement.executeUpdate(query);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Error al conectar con la base de datos", e);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Error al actualizar el empleado", e);
            } finally {
                try {
                    if (statement != null) statement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
                }
            }
        }
    }

    @Override
    public void delete(String correo) {
        Connection connection = null;
        Statement statement = null;
        String query = String.format("DELETE FROM empleado WHERE correo = '%s'", correo);
        try {
            connection = ConexionDB.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al eliminar el empleado", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
            }
        }
    }
}

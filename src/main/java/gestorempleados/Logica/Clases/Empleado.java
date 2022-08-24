package gestorempleados.Logica.Clases;

import java.time.LocalDate;

public class Empleado {
    private String correo;
    private String nombre;
    private String apellido;
    private String tel;
    private LocalDate nacimiento;

    public Empleado(){}

    public Empleado(String correo, String nombre, String apellido, String tel, LocalDate nacimiento) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tel = tel;
        this.nacimiento = nacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return tel;
    }

    public void setTelefono(String tel) {
        this.tel = tel;
    }

    public LocalDate getFechaNacimiento() {
        return nacimiento;
    }

    public void setFechaNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "correo='" + correo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", tel='" + tel + '\'' +
                ", nacimiento=" + nacimiento +
                '}';
    }
}

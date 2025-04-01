package Models;

public class Paciente {
    private String nombre;
    private String apellido;
    private String genero;
    private String ciudad;
    private int edad;
    private String dni;
    private double peso;
    private double altura;
    private byte[] foto;

    // Constructor
    public Paciente(String nombre, String apellido, String genero, String ciudad,
                    int edad, String dni, double peso, double altura, byte[] foto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.ciudad = ciudad;
        this.edad = edad;
        this.dni = dni;
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;
    }

    // Getters y Setters
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

    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }

    public byte[] getFoto() {
        return foto;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    // Métodos adicionales
    public String nombreCompleto() {
        return nombre + " " + apellido;
    }

    public String edadPaciente() {
        return "Edad: " + (esMayorEdad() ? "Mayor de Edad" : "Menor de Edad");
    }

    public boolean esMayorEdad() {
        return edad >= 18;
    }

    public String pesoPaciente() {
        String[] tipoPeso = {"Bajo", "Ideal", "Alto"};
        return tipoPeso[calcularBMI() + 1];
    }

    public int calcularBMI() {
        if (altura <= 0) return 0;
        double bmi = peso / (altura * altura);
        if (bmi < 20) {
            return -1;
        } else if (bmi <= 25) {
            return 0;
        } else {
            return 1;
        }
    }

    public static boolean verificarDNI(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }

    @Override
    public String toString() {
        return "Paciente: " + nombreCompleto() + ", tiene un peso " + pesoPaciente() +
                ", y es " + edadPaciente();
    }
}

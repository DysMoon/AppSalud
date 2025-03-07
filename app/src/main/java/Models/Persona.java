package Models;

import java.io.Serializable;

public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombres;
    private String apellidos;
    private int edad;
    private String dni;
    private double peso;
    private double altura;

    public Persona(String nombres, String apellidos, int edad, String dni, double peso, double altura) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.dni = dni;
        this.peso = peso;
        this.altura = altura;
    }


    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public String getDni() {
        return dni;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }


    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public int calcularIMC() {
        double imc = peso / (altura * altura);
        if (imc < 20) {
            return -1;
        } else if (imc <= 25) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean esMayorDeEdad() {
        return edad >= 18;
    }

    public static boolean verificarDNI(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }

    @Override
    public String toString() {
        String[] tipoPeso = {"Peso Bajo", "Peso Ideal", "Sobre Peso"};
        return nombres + " " + apellidos + " tiene peso " + tipoPeso[calcularIMC() + 1] +
                " y es " + (esMayorDeEdad() ? "mayor de edad" : "menor de edad");
    }
}

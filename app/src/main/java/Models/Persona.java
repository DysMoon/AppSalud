package Models;

import android.net.Uri;

public class Persona {
    private String nombres;
    private String apellidos;
    private int edad;
    private String dni;
    private double peso;
    private double altura;
    private String sexo;
    private String ciudad;
    private Uri foto;


    public Persona(String nombres, String apellidos, String sexo, String ciudad, int edad, String dni, double peso, double altura, Uri foto) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.ciudad = ciudad;
        this.edad = edad;
        this.dni = dni;
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;

    }
    public Uri getFoto() {
        return foto;
    }

    public String getNombreCompleto(){
        return apellidos+", "+ nombres;
    }

    public String getTipoPeso(){
        String[] tipoPeso = {"Peso Bajo", "Peso Ideal", "Sobre Peso"};
        return tipoPeso[calcularIMC()+1];

    }

    public String getTipoPersona(){
        return esMayorDeEdad()?"mayor edad":"menor de edad";
    }


    public String getSexo() {
        return sexo;
    }
    public String getCiudad() {
        return ciudad;
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

    @Override
    public String toString() {
        String[] tipoPeso = {"Peso Bajo", "Peso Ideal", "Sobre Peso"};
        return nombres + " " + apellidos + " tiene peso " + tipoPeso[calcularIMC() + 1] +
                " y es " + (esMayorDeEdad() ? "mayor de edad" : "menor de edad");
    }
}

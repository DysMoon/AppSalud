package Models;

public class Persona  {
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
        if(edad >= 18)
            return true;
        else
            return false;
    }

    public static boolean verificarDNI(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }

    @Override
    public String toString() {

        String[] tipoPeso = {"Peso Bajo", "Peso Ideal", "Sobre Peso"};
        return nombres + "," + apellidos + " tiene peso " + tipoPeso[calcularIMC()+1] + " y es " + (esMayorDeEdad() ? "mayor de edad" : "menor de edad");
    }
}

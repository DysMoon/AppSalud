package Models;

public class PacienteAPI extends Paciente {
    private String foto;
    private int idPaciente;

    public PacienteAPI(String nombre, String apellido, String genero, String ciudad,
                       int edad, String dni, double peso, double altura, String foto) {
        super(nombre, apellido, genero, ciudad, edad, dni, peso, altura);
        this.foto = foto;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

package Network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import Models.PacienteAPI;

public interface ApiServicioSalud {

    @GET("Paciente")
    Call<List<PacienteAPI>> GetPacientes();

    @POST("Paciente")
    Call<PacienteAPI> PostPacientes(@Body PacienteAPI paciente);
}

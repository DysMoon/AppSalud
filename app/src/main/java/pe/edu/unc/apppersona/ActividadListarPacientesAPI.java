package pe.edu.unc.apppersona;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import Models.PacienteAPI;
import Network.ApiServicioSalud;
import Network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActividadListarPacientesAPI extends AppCompatActivity {

    ListView lvListaPersonasAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ly_listar_personas_api);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvListaPersonasAPI), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvListaPersonasAPI = findViewById(R.id.lvListaPersonasAPI);
        cargarPacientesAPI();
    }

    private void cargarPacientesAPI() {
        ApiServicioSalud apiServicioSalud = RetrofitClient.getClient().create(ApiServicioSalud.class);
        Call<List<PacienteAPI>> call = apiServicioSalud.GetPacientes();

        call.enqueue(new Callback<List<PacienteAPI>>() {
            @Override
            public void onResponse(Call<List<PacienteAPI>> call, Response<List<PacienteAPI>> response) {
                if (response.isSuccessful()) {
                    List<PacienteAPI> lista = response.body();
                    List<String> nombres = new ArrayList<>();
                    for (PacienteAPI p : lista) {
                        nombres.add(p.getNombre() + " " + p.getApellido());
                    }
                    lvListaPersonasAPI.setAdapter(new ArrayAdapter<>(
                            ActividadListarPacientesAPI.this,
                            android.R.layout.simple_list_item_1,
                            nombres
                    ));
                } else {
                    Toast.makeText(ActividadListarPacientesAPI.this, "Respuesta no exitosa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PacienteAPI>> call, Throwable t) {
                Toast.makeText(ActividadListarPacientesAPI.this, "Error al cargar los pacientes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

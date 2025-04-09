package pe.edu.unc.apppersona;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import DataAccess.DAOPaciente;
import pe.edu.unc.apppersona.Adapters.AdapterPacientes;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_list);

        Toolbar toolbar = findViewById(R.id.toolbarListar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Lista de Pacientes");
        }

        ListView lvListado = findViewById(R.id.lvListar);
        if (lvListado == null) {
            Toast.makeText(this, "Error al cargar la lista", Toast.LENGTH_SHORT).show();
            return;
        }

        cargarListado(lvListado);
    }

    private void cargarListado(ListView lvListado) {
        DAOPaciente daoPaciente = new DAOPaciente();
        daoPaciente.cargar(this);

        if (daoPaciente.getSize() <= 0) {
            mostrarAvisoSinPacientes();
            return;
        }

        lvListado.setAdapter(new AdapterPacientes(daoPaciente, this));
    }

    private void mostrarAvisoSinPacientes() {
        Toast.makeText(this, "No hay registro de pacientes", Toast.LENGTH_SHORT).show();
    }
}

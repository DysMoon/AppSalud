package pe.edu.unc.apppersona;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Models.Persona;

public class ActividadListadoPersonas extends AppCompatActivity {

    ListView lvPersonas;
    ArrayList<Persona> listaPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_list);

        lvPersonas = findViewById(R.id.lvPersonas);

        if (lvPersonas == null) {
            Log.e("ERROR", "El ListView lvPersonas es NULL. Verifica el ID en el XML.");
            Toast.makeText(this, "Error: ListView no encontrado", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (getIntent().hasExtra("listaPersonas")) {
            listaPersonas = (ArrayList<Persona>) getIntent().getSerializableExtra("listaPersonas");
        } else {
            listaPersonas = new ArrayList<>();
        }

        Log.d("DEBUG", "Personas recibidas: " + listaPersonas.toString());

        if (listaPersonas == null || listaPersonas.isEmpty()) {
            Toast.makeText(this, "No hay personas registradas", Toast.LENGTH_SHORT).show();
            return;
        }

        PersonaAdapter adapter = new PersonaAdapter(this, listaPersonas);
        lvPersonas.setAdapter(adapter);
    }
}

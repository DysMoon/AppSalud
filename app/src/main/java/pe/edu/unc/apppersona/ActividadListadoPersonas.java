package pe.edu.unc.apppersona;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

import Models.Persona;

public class ActividadListadoPersonas extends AppCompatActivity {


    ListView lvPersonas;
    ArrayList<Persona> listaPersonas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_lista);

        lvPersonas = findViewById(R.id.lvPersonas);

        listaPersonas = (ArrayList<Persona>) getIntent().getSerializableExtra("listaPersonas");
        if (listaPersonas == null) {
            listaPersonas = new ArrayList<>();
        }

        if (listaPersonas.isEmpty()) {
            Toast.makeText(this, "No hay personas registradas", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si no hay datos
            return;
        }

        ArrayList<String> datosPersonas = new ArrayList<>();
        for (Persona p : listaPersonas) {
            datosPersonas.add(p.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosPersonas);
        lvPersonas.setAdapter(adapter);
    }
}
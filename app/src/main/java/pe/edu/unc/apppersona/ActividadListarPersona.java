package pe.edu.unc.apppersona;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import Models.Persona;

public class ActividadListarPersona extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_listar_persona);


        ListView lvListaPersonas = findViewById(R.id.lvListaPersonas);

        Toolbar oBarra = findViewById(R.id.toolbarListarPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lvListaPersonas.setAdapter(new AdaptadorPersonas(AdaptadorPersonas.listaPersona,this));

    }



}

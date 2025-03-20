package pe.edu.unc.apppersona;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import Models.Persona;

public class ActividadListadoPersonas extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_listadopersonas);


        ListView lvListaPersonas = findViewById(R.id.lvListaPersonas);
        lvListaPersonas.setAdapter(new ArrayAdapter<Persona>(this, android.R.layout.simple_list_item_1, ActividadPrincipal.listaPersonas));

        Toolbar oBarra = findViewById(R.id.toolbarListarPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lvListaPersonas.setAdapter(new AdaptadorPersonas(ActividadPrincipal.listaPersonas,this));

    }



}

package pe.edu.unc.apppersona;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class ActividadPrincipal extends AppCompatActivity {

    public static List<Persona> listaPersonas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ly_principal);
        //Busca la Toolbar en el layout
        Toolbar oBarra = findViewById(R.id.toolbarPrincipal);
        //La establece como Action Bar para manejar menús y navegación
        setSupportActionBar(oBarra);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent oIntent=null;

        if(item.getItemId()==R.id.itemSalir)
        {
            finish();
        }

        if(item.getItemId()==R.id.itemRegistrarPersonas){
            oIntent = new Intent(this,ActividadPersona.class);
            startActivity(oIntent);
        }

        if(item.getItemId()==R.id.itemListarPersonas){
            oIntent = new Intent(this, ActividadListadoPersonas.class);
            startActivity(oIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
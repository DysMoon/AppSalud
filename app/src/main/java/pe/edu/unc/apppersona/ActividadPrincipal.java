package pe.edu.unc.apppersona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Models.Persona;

public class ActividadPrincipal extends AppCompatActivity {



    public static List<Persona> listaPersonas = new ArrayList<>();

    TextView lbContar,lbUltima;
    int contador;
    Date visita;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_principal);

        lbContar = findViewById(R.id.lbContar);
        lbUltima = findViewById(R.id.lbUltima);

        // Recuperamos los datos de SharedPreferences
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        contador = oFlujo.getInt("contador", 0);


        // Mostramos la información en la interfaz
        lbContar.setText("N° de visitas: " + contador);
        visita = new Date();
        lbUltima.setText("Ultima visita: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(visita));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        contador++;  // Incrementamos el contador




        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        SharedPreferences.Editor oEditor = oFlujo.edit();

        oEditor.putInt("contador", contador);

        oEditor.putLong("visita", visita.getTime());


        oEditor.commit();

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
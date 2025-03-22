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
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class ActividadPrincipal extends AppCompatActivity {

    private TextView lbContar, lbUltima;
    private int contador;
    private long ultimaVisita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_principal);

        Toolbar oBarra = findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(oBarra);

        lbContar = findViewById(R.id.lbContar);
        lbUltima = findViewById(R.id.lbUltima);

        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);

        contador = oFlujo.getInt("contador", 1);
        lbContar.setText("N° de visitas: " + contador);


        ultimaVisita = oFlujo.getLong("ultimaVisita", 0);

        // Mostrar solo la hora de la última visita
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        if (ultimaVisita > 0) {
            lbUltima.setText("Última: " + formatoHora.format(new Date(ultimaVisita)));
        } else {
            lbUltima.setText("Última: --:--:--");
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Verificar si la actividad está realmente siendo destruida
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        SharedPreferences.Editor oEditor = oFlujo.edit();

        long nuevaVisita = System.currentTimeMillis();
        oEditor.putLong("ultimaVisita", nuevaVisita);

        // Incrementar el contador
        contador++;
        oEditor.putInt("contador", contador);

        oEditor.apply();  // Usamos apply() para una operación asíncrona
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent oIntent = null;

        if (item.getItemId() == R.id.itemSalir) {
            finish();
        }

        if (item.getItemId() == R.id.itemRegistrarPersonas) {
            oIntent = new Intent(this, ActividadRegistrarPersona.class);
            startActivity(oIntent);
        }

        if (item.getItemId() == R.id.itemListarPersonas) {
            oIntent = new Intent(this, ActividadListarPersona.class);
            startActivity(oIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}

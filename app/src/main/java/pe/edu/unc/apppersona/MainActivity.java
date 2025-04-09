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

public class MainActivity extends AppCompatActivity {

    private TextView txtContador, txtVisita;
    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AppPersona");

        txtContador = findViewById(R.id.txtContador);
        txtVisita = findViewById(R.id.txtVisita);

        SharedPreferences prefs = getSharedPreferences("control", Context.MODE_PRIVATE);

        String ultimaFecha = prefs.getString("ultimaFecha", "Primera vez");

        contador = prefs.getInt("contador", 1);
        contador++;
        prefs.edit().putInt("contador", contador).apply();

        String nuevaFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        prefs.edit().putString("ultimaFecha", nuevaFecha).apply();

        txtContador.setText("N° Ingresos: " + contador);
        txtVisita.setText("Último acceso: " + ultimaFecha);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        if (item.getItemId() == R.id.exitItem) {
            finish();

        }
        if (item.getItemId() == R.id.registerItem) {
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.listItem){
            intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.callsItem){
            intent = new Intent(this, CallsActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.listaPersonaAPIItem) {
            intent = new Intent(this, ActividadListarPacientesAPI.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.itemRegistarPacienteAPI) {
            intent = new Intent(this, ActividadRegistarPacientesAPI.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}

package pe.edu.unc.apppersona;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ActividadLista extends ListActivity {

        String[] ejer = {"Persona"};


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ejer));
        }

        @Override
        protected void onListItemClick(ListView l, View v, int index, long id) {
            super.onListItemClick(l, v, index, id);
              // Toast.makeText(this, "Has pulsado " + index, Toast.LENGTH_LONG).show();

            Intent ointent=null;
            try {
                Class<?> ActividadDestino = Class.forName("pe.edu.unc.apppersona.Actividad" + ejer[index]);
                ointent = new Intent(this, ActividadDestino);
                startActivity(ointent);
            }catch (Exception e){
                Log.d("Error", e.getMessage());

            }


        }
    }

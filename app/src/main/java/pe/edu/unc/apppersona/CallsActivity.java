package pe.edu.unc.apppersona;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import DataAccess.DAOCalls;

public class CallsActivity extends AppCompatActivity {

    private ListView lvCallList;
    private DAOCalls daoCalls;
    private String selectedType = "All";

    private static final String[] CALL_TYPES = {"All", "Incoming", "Outgoing", "Missed", "Rejected"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_calls);

        Toolbar toolbar = findViewById(R.id.toolbarCalls);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Historial de Llamadas");
        }

        Spinner spCallList = findViewById(R.id.spListaLlamadas);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CALL_TYPES);
        spCallList.setAdapter(adapter);

        spCallList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = CALL_TYPES[position];
                filterCalls();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lvCallList = findViewById(R.id.lvListaLlamadas);
        daoCalls = new DAOCalls();
        filterCalls();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void filterCalls() {
        List<String> llamadas = daoCalls.listCalls(this, selectedType);

        if (llamadas.isEmpty()) {
            llamadas.add("No se encontraron llamadas.");
        }

        lvCallList.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                llamadas
        ));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
